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

package org.tt.platform.convert.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tt.core.entity.datafetcher.Department;
import org.tt.core.entity.datafetcher.Group;
import org.tt.core.entity.db.TTEntity;
import org.tt.platform.convert.AbstractDataConverter;
import org.tt.platform.convert.json.serializer.DepartmentSerializer;
import org.tt.platform.convert.json.serializer.GroupListSerializer;
import org.tt.platform.convert.json.serializer.TimeTableSerializer;


import java.util.List;

/**
 * Created by fau on 02/05/14.
 */
public class JSONConverter implements AbstractDataConverter {
    private static Gson gson;

    public JSONConverter() {
        gson = new Gson();
    }

    public String convertDepartmentList(List<Department> departments) {
        GsonBuilder gsb = new GsonBuilder();
        gsb.registerTypeAdapter(Department.class, new DepartmentSerializer());

        return gsb.create().toJson(departments);
    }

    public String convertGroupList(List<Group> names) {
        GsonBuilder gsb = new GsonBuilder();
        gsb.registerTypeAdapter(Group.class, new GroupListSerializer());
        return gsb.create().toJson(names);

    }

    @Override
    public String convertAbstractList(List<String> list) {
        return gson.toJson(list);
    }


    @Override
    public String convertTT(TTEntity table) {
        GsonBuilder gsb = new GsonBuilder();
        gsb.registerTypeAdapter(TTEntity.class, new TimeTableSerializer());
        return gsb.create().toJson(table);
    }

}
