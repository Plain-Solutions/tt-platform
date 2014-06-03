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
import org.tt.core.entity.db.TTDayEntity;
import org.tt.core.entity.db.TTEntity;
import org.tt.core.entity.db.TTLesson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * TimeTableSerializer is override of standard GSON <code>JsonSerializer</code> to properly create table of group.
 * It has this structure:
 * <ul>
 * <li>1st: array of days {"dayname": data}</li>
 * <li>2nd: array of lessons (data) {parity, sequence, info} </li>
 * <li>3rd: array of lessons records (info) {activity, subject, subinfo}</li>
 * <li>4th: array of subgroup-teacher-location (subinfo) {subgroup, teacher, building, room} </li>
 * </ul>
 * sequence and info. It is sorted by days, then by sequence of lesson, then by parity (even<odd<full),
 * then by subgroups (lexicographically): see at <a href=https://github.com/Plain-Solutions/tt-platform/blob/master/docs/API%20Reference.md">API reference</a>
 * </code>
 *
 * @author Vlad Slepukhin
 * @since 2.0
 */
public class TimeTableSerializer implements JsonSerializer<TTEntity> {

    /**
     * Converts TimeTableEntity to JsonElement, saving order in information in right representation.
     *
     * @param tt                       an datafetcher of {@link org.tt.core.entity.db.TTEntity}
     * @param type                     default GSON parameter.
     * @param jsonSerializationContext default GSON parameter.
     * @return JsonElement in a proper format.
     * @since 2.0
     */
    @Override
    public JsonElement serialize(TTEntity tt, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray result = new JsonArray();
        List<TTDayEntity> days = tt.getTimetable();

        for (TTDayEntity day: days) {
            for (TTLesson dayLessons: day.getLessons()) {
                JsonObject lesson = new JsonObject();
                lesson.addProperty("day",day.getName());
                lesson.addProperty("parity", dayLessons.getParity());
                lesson.addProperty("sequence", dayLessons.getSequence());


                JsonArray res = new JsonArray();
                for (TTLesson.TTLessonRecord subject: dayLessons.getRecords()) {
                    JsonObject aSubj = new JsonObject();
                    aSubj.addProperty("name", subject.getSubject());
                    aSubj.addProperty("activity", subject.getActivity());

                    JsonArray subgroups = new JsonArray();
                    for (TTLesson.TTClassRoomEntity cre: subject.getClassRoomEntities()) {
                        JsonObject data = new JsonObject();
                        data.addProperty("subgroup", cre.getSubgroup());
                        data.addProperty("teacher", cre.getTeacher());
                        data.addProperty("location", cre.getBuilding());
                        subgroups.add(data);
                    }
                    aSubj.add("subgroups", subgroups);
                    res.add(aSubj);
                }
                lesson.add("subject", res);
                result.add(lesson);
            }

        }


        return result;
    }
}
