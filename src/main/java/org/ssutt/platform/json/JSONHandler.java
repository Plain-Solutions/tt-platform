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
import com.google.gson.GsonBuilder;
import org.ssutt.platform.json.entities.DepartmentEntity;
import org.ssutt.platform.json.entities.FailureEntity;
import org.ssutt.platform.json.entities.TimeTableEntity;
import org.ssutt.platform.json.serializers.DepartmentSerializer;
import org.ssutt.platform.json.serializers.TimeTableSerializer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONHandler {
    private static Gson gson;
    public JSONHandler() {
        gson = new Gson();

    }

    public String convertDepartmentList(Map<String, Map<String, String>> departments) {
        GsonBuilder gsb = new GsonBuilder();
        gsb.registerTypeAdapter(DepartmentEntity.class, new DepartmentSerializer());

        return gsb.create().toJson(departments);
    }

    public String getGroupNames(List<String> names) {
       return gson.toJson(names);
    }

    public String convertGroupNames(int id){
        return gson.toJson(id);
    }

    public String convertTT(List<String[]> table) {
        GsonBuilder gsb = new GsonBuilder();

        Map<String, List<Map<String, String>>> temp = new LinkedHashMap<>();

        for (String[] record: table){
            String weekday  = record[0];
            Map<String, String> t = new LinkedHashMap<>();
            t.put("parity", record[1]);
            t.put("sequence", record[2]);
            t.put("info", record[3]);
            if (temp.containsKey(weekday)) {
                temp.get(weekday).add(t);
            }
            else {
                List<Map <String, String>> tT = new ArrayList<>();
                tT.add(t);
                temp.put(weekday,tT);
            }

        }


        gsb.registerTypeAdapter(TimeTableEntity.class, new TimeTableSerializer());
        return gsb.create().toJson(temp);
    }

    public String getFailure(String module, String msg) {
        return gson.toJson(new FailureEntity(module, msg));
    }

}
