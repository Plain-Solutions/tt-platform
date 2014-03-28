/**
 * Copyright 2014 Plain Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ssutt.platform.testing;


import org.junit.Test;
import org.ssutt.platform.communicator.DataCommunicator;

import java.sql.SQLException;

public class MainTest {
    @Test
    public void TestDC() throws ClassNotFoundException, SQLException {

        DataCommunicator dc = new DataCommunicator();

        System.out.println(dc.getDepartments());
        System.out.println(dc.getGroupNames("knt"));
        System.out.println(dc.getGroupNames("sf"));
        System.out.println(dc.getGroupID("knt","151"));


    }
}
