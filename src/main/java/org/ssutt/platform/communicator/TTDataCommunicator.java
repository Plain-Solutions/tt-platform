/*
 * Copyright 2014 Plain Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ssutt.platform.communicator;

import org.ssutt.core.dm.SSUDataManager;
import org.ssutt.core.dm.TTDataManager;
import org.ssutt.core.fetch.SSUDataFetcher;
import org.ssutt.core.sql.H2Queries;
import org.ssutt.core.sql.SSUSQLManager;
import org.ssutt.core.sql.ex.EmptyTableException;
import org.ssutt.core.sql.ex.NoSuchDepartmentException;
import org.ssutt.core.sql.ex.NoSuchGroupException;
import org.ssutt.platform.json.JSONHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TTDataCommunicator {
    public enum Module {
        GENSQL("SQL"),
        TTSQL("SSUSQLManager/Database"),
        DF("SSUDataFetcher"),
        IO("Input/Output");

        private final String name;
        Module (String name) {
            this.name = name;
        }
    }

   private TTDataManager dm;
    private JSONHandler jsh;

    public TTDataCommunicator() {
        dm = new SSUDataManager();

        dm.deliverDataFetcherProvider(new SSUDataFetcher());

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            //some json thing
            e.printStackTrace();
        }
        try {
            //testing purposes
            if (Files.exists(Paths.get("localtest.h2.db"))) {
                dm.deliverDBProvider(new SSUSQLManager(DriverManager.
                        getConnection("jdbc:h2:file:localtest;", "sa", "")), new H2Queries());
            }
            else {
                dm.deliverDataFetcherProvider(new SSUDataFetcher());
                dm.deliverDBProvider(new SSUSQLManager(DriverManager.
                        getConnection("jdbc:h2:file:localtest;INIT=RUNSCRIPT FROM './initTT.sql'", "sa", "")),
                        new H2Queries());
                dm.putDepartments();
                dm.putAllGroups();
                for (String d: dm.getDepartmentTags())
                    for (String gr: dm.getGroups(d)) {
                        dm.putTT(d, dm.getGroupID(d, gr));
                    }
            }
        } catch (SQLException e) {
           jsh.getFailure(Module.GENSQL.name(),e.getMessage());
        } catch (NoSuchDepartmentException | NoSuchGroupException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        } catch (IOException e) {
            jsh.getFailure(Module.IO.name(), e.getMessage());
        }
        jsh = new JSONHandler();
    }

    public void putDepartments() {
        try {
            dm.putDepartments();
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(),e.getMessage());
        }
    }

    public void putDepartmentGroups(String departmentTag) {
        try {
            dm.putDepartmentGroups(departmentTag);
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(), e.getMessage());
        } catch (NoSuchDepartmentException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }
    }

    public void putAllGroups() {
        try {
            dm.putAllGroups();
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(),e.getMessage());
        } catch (NoSuchDepartmentException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }
    }

    public void putTT(String departmentTag, int groupID) {

        try {
            dm.putTT(departmentTag, groupID);
        } catch (IOException e) {
            jsh.getFailure(Module.IO.name(), e.getMessage());
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(),e.getMessage());
        } catch (NoSuchDepartmentException | NoSuchGroupException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }

    }

    public String getDepartments() {
        String result = "";
        try {
            Map<String, Map<String, String>> raw = dm.getDepartments();
            result = jsh.convertDepartmentList(raw);
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(), e.getMessage());
        }
        return result;
    }

    public String testFailure(){
        return jsh.getFailure(Module.TTSQL.name(), "test message of TTSQLManager/DB failure");
    }


    public String getGroupNames(String departmentTag) {
        String result = "";
        try {
            List<String> raw = dm.getGroups(departmentTag);
            result = jsh.getGroupNames(raw);
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(), e.getMessage());
        } catch (NoSuchDepartmentException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }

        return result;
    }

    public String getGroupID(String departmentTag, String groupName) {
        String result = "";
        try {
            int raw = dm.getGroupID(departmentTag, groupName);
            result = jsh.convertGroupNames(raw);
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(),e.getMessage());
        } catch (NoSuchGroupException | NoSuchDepartmentException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }
        return  result;
    }

    public String convertTT(int groupID) {
        String result = "";
        try {
            List<String[]> raw = dm.getTT(groupID);
            result = jsh.convertTT(raw);
        } catch (SQLException e) {
            jsh.getFailure(Module.GENSQL.name(), e.getMessage());
        } catch (NoSuchGroupException | EmptyTableException e ) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        }
        return result;
    }
}
