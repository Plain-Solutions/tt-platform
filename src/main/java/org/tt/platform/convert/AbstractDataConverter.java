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

package org.tt.platform.convert;

import org.tt.core.entity.datafetcher.Department;
import org.tt.core.entity.datafetcher.Group;
import org.tt.core.entity.db.TTEntity;

import java.util.List;

/**
 * Created by fau on 02/05/14.
 */
public interface AbstractDataConverter {
    String convertDepartmentList(List<Department> departments);

    String convertDepartmentMessage(String msg);

    String convertGroupList(List<Group> names);

    String convertAbstractList(List<String> list);

    String convertTT(TTEntity table);

    String convertTTPlainer (TTEntity table);

    String returnSQLErrMsg(String msg);

    String returnNoSuchDepEx();

    String returnNoSuchGrEx();

}
