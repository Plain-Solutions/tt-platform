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

package org.tt.platform.convert.json.serializer;

import com.google.gson.*;
import org.tt.core.entity.datafetcher.Department;

import java.lang.reflect.Type;

/**
 * DepartmentSerializer is override of standard GSON  <code>JsonSerializer</code> to properly create the list of departments.
 * It is now formatted as defined here:
 * <code>    <br>
 * [
 * <br>
 * { "tag: "tag", "name":"Some department"}, <br>
 * ] <br>
 * </code>
 *
 * @author Vlad Slepukhin
 * @since 1.2
 */
public class DepartmentSerializer implements JsonSerializer<Department> {

    /**
     * Converts Department element to JsonElement, saving order in information in right representation.
     *
     * @param department               the instance of {@link org.tt.core.entity.datafetcher.Department}.
     * @param type                     default GSON parameter.
     * @param jsonSerializationContext default GSON parameter.
     * @return Formatted JSON Element - sub-array with info about the department and its tag.
     * @since 2.0
     */
    @Override
    public JsonElement serialize(Department department, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("tag", department.getTag());
        result.addProperty("name", department.getName());

        return result;
    }
}
