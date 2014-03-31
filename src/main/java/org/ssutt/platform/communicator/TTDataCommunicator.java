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

    private static TTDataManager dm;
    private static JSONHandler jsh;

    public TTDataCommunicator(TTDataManager dm, JSONHandler jsh) {
        TTDataCommunicator.dm = dm;
        this.jsh = jsh;
    }

    public void putDepartments() {
        try {
            dm.putDepartments();
        } catch (SQLException e) {
            System.out.println(jsh.getFailure(Module.GENSQL.name(), e.getMessage()));
        }
    }

    public void putDepartmentGroups(String departmentTag) {
        try {
            dm.putDepartmentGroups(departmentTag);
        } catch (SQLException e) {
            System.out.println(jsh.getFailure(Module.GENSQL.name(), e.getMessage()));
        } catch (NoSuchDepartmentException e) {
            System.out.println(jsh.getFailure(Module.TTSQL.name(), e.getMessage()));
        }
    }

    public void putAllGroups() {
        try {
            dm.putAllGroups();
        } catch (SQLException e) {
            System.out.println(jsh.getFailure(Module.GENSQL.name(), e.getMessage()));
        } catch (NoSuchDepartmentException e) {
            System.out.println(jsh.getFailure(Module.TTSQL.name(), e.getMessage()));
        }
    }

    public void putTT(String departmentTag, int groupID) {
        try {
            dm.putTT(departmentTag, groupID);
        } catch (IOException e) {
            System.out.println(jsh.getFailure(Module.IO.name(), e.getMessage()));
        } catch (SQLException e) {
            System.out.println(jsh.getFailure(Module.GENSQL.name(), e.getMessage()));
        } catch (NoSuchDepartmentException e) {
            System.out.println(jsh.getFailure(Module.TTSQL.name(), "No such Department"));
        } catch (NoSuchGroupException e) {
            System.out.println(jsh.getFailure(Module.TTSQL.name(), "No such Group"));
        }

    }

    public String getDepartments() {
        try {
            Map<String, Map<String, String>> raw = dm.getDepartments();
            return jsh.convertDepartmentList(raw);
        } catch (SQLException e) {
            return jsh.getFailure(Module.GENSQL.name(), e.getSQLState());
        }
    }


    public String getGroupNames(String departmentTag) {
        try {
            List<String> raw = dm.getGroups(departmentTag);
            return jsh.getGroupNames(raw);
        } catch (SQLException e) {
            return jsh.getFailure(Module.GENSQL.name(), e.getSQLState());
        } catch (NoSuchDepartmentException e) {
            return jsh.getFailure(Module.TTSQL.name(), "No department found");
        }
    }

    public String getGroupID(String departmentTag, String groupName) {
        try {
            int raw = dm.getGroupID(departmentTag, groupName);
            return jsh.convertGroupNames(raw);
        } catch (SQLException e) {
            return jsh.getFailure(Module.GENSQL.name(), e.getSQLState());
        } catch (NoSuchDepartmentException e) {
            return jsh.getFailure(Module.TTSQL.name(), "No such Department");
        } catch (NoSuchGroupException e) {
            return jsh.getFailure(Module.TTSQL.name(), "No such Group");
        }
    }

    public String getTT(int groupID) {
        try {
            List<String[]> raw = dm.getTT(groupID);
            return jsh.convertTT(raw);
        } catch (SQLException e) {
            return jsh.getFailure(Module.GENSQL.name(), e.getSQLState());
        } catch (NoSuchGroupException e) {
            return jsh.getFailure(Module.TTSQL.name(), "No such Group");
        } catch (EmptyTableException e) {
            return jsh.getFailure(Module.TTSQL.name(), "No info about this group. The Table is empty");
        }
    }


}
