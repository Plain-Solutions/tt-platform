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

package org.ssutt.platform.json;

import com.google.gson.Gson;
import org.ssutt.platform.json.entities.DepartmentEntity;

import java.util.*;

public class JSONHandler {
    private static Gson gson;
    public JSONHandler() {
        gson = new Gson();
    }

    public String convertDepartmentsMap(Map<String, String> departments) {
        Collection result = new ArrayList();
        for (String key : new TreeSet<String>(departments.keySet())) {
            result.add(new DepartmentEntity(key, departments.get(key)));
        }
        return gson.toJson(result);
    }

    public String getGroupNames(List<String> names) {
       return gson.toJson(names);
    }

    public String converGroupNames(int id){
        return gson.toJson(id);
    }

}
