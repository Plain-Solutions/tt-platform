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
import org.ssutt.core.sql.ex.NoSuchDepartmentException;
import org.ssutt.core.sql.ex.NoSuchGroupException;
import org.ssutt.platform.json.JSONHandler;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataCommunicator {
    TTDataManager dm;
    JSONHandler jsh;

    public DataCommunicator() {
        dm = new SSUDataManager();

        dm.deliverDataFetcherProvider();

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            //some json thing
            e.printStackTrace();
        }
        try {
            dm.deliverDBProvider(DriverManager.
                    getConnection("jdbc:h2:file:localtest", "sa", ""));
        } catch (SQLException e) {
            //some json thing
            e.printStackTrace();
        }
        jsh = new JSONHandler();
    }

    public void putDepartments() {
        try {
            dm.putDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putDepartmentGroups(String departmentTag) {
        try {
            dm.putDepartmentGroups(departmentTag);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
        }
    }

    public void putAllGroups() {
        try {
            dm.putAllGroups();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
        }
    }

    public void putTT(String departmentTag, int groupID) {

        try {
            dm.putTT(departmentTag, groupID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
        }

    }

    public String getDepartments() {
        String result = "";
        try {
            Map<String, String> raw = dm.getDepartments();
            result =  jsh.convertDepartmentsMap(raw);
        } catch (SQLException e) {
            e.printStackTrace();
            result = "FORMAT ERROR HERE PLZ";
        }
        return result;
    }

    public String getGroupNames(String departmentTag) {
        String result = "";
        try {
            List<String> raw = dm.getGroups(departmentTag);
            result = jsh.getGroupNames(raw);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getGroupID(String departmentTag, String groupName) {
        String result = "";
        try {
            int raw = dm.getGroupID(departmentTag, groupName);
            result = jsh.converGroupNames(raw);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
        }
        return  result;
    }


}
