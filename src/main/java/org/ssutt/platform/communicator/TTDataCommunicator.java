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

import org.ssutt.core.dm.TTDataManager;
import org.ssutt.core.sql.ex.EmptyTableException;
import org.ssutt.core.sql.ex.NoSuchDepartmentException;
import org.ssutt.core.sql.ex.NoSuchGroupException;
import org.ssutt.platform.json.JSONHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TTDataCommunicator {

   private TTDataManager dm;
    private JSONHandler jsh;

    public TTDataCommunicator(TTDataManager dm, JSONHandler jsh) {
        this.dm = dm;
        this.jsh = jsh;
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
