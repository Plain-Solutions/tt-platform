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
package org.ssutt.platform.servlet;

import org.ssutt.core.dm.SSUDataManager;
import org.ssutt.core.dm.TTData;
import org.ssutt.platform.factory.TTDataManagerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class Actions implements SparkApplication {
    @Override
    public void init() {
        final TTDataManagerFactory dmf = TTDataManagerFactory.getInstance();
        get(new Route("/department/:tag/group/:name") {
            @Override
            public Object handle(Request request, Response response) {
                SSUDataManager  dm = dmf.produce();
                TTData result = dm.getTT(Integer.parseInt(dm.getGroupID(request.params(":tag"),request.params(":name")).getMessage()));
                response.status(result.getHttpCode());
                return result.getMessage();
            }});

        get(new Route("/department/:tag/groups") {
            @Override
            public Object handle(Request request, Response response) {
                SSUDataManager dm = dmf.produce();
                TTData result = dm.getGroups(request.params(":tag"));
                response.status(result.getHttpCode());
                return result.getMessage();
            }
        });


        get(new Route("/departments") {
            @Override
            public Object handle(Request request, Response response) {
                SSUDataManager dm = dmf.produce();
                TTData result = dm.getDepartments();
                response.status(result.getHttpCode());

                return result.getMessage();
            }
        });


        get(new Route("/"){
            @Override
            public Object handle(Request request, Response response) {
                response.redirect("http://ssutt.org/developers");
                return 0;
            }
        });


    }
}
