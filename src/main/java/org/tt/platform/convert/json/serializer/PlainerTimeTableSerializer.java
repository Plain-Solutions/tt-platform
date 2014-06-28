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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlainerTimeTableSerializer implements JsonSerializer<TTEntity> {
    @Override
    public JsonElement serialize(TTEntity ttEntity, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray result = new JsonArray();
        List<TTDayEntity> days = ttEntity.getTimetable();

        for (TTDayEntity day: days) {
            for (TTLesson dayLessons: day.getLessons()) {
                JsonObject lesson = new JsonObject();
                lesson.addProperty("day", convertDayToNum(day.getName()));
                lesson.addProperty("sequence", dayLessons.getSequence());

                JsonArray res = new JsonArray();
                for (TTLesson.TTLessonRecord subject: dayLessons.getRecords()) {
                    JsonObject aSubj = new JsonObject();
                    aSubj.addProperty("name", subject.getSubject());
                    aSubj.addProperty("activity", subject.getActivity());
                    aSubj.addProperty("parity", convertParityToNum(dayLessons.getParity()));

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

    private int convertDayToNum(String day) {
        ArrayList<String> days =  new ArrayList<>(Arrays.asList(new String[]{"mon", "tue", "wed", "thu", "fri", "sat"}));

        return days.indexOf(day);
    }

    private int convertParityToNum(String parity) {
        ArrayList<String> parities = new ArrayList<>(Arrays.asList(new String[]{"nom", "denom", "full"}));

        return parities.indexOf(parity);
    }
}
