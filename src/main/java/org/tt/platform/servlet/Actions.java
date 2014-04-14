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
package org.tt.platform.servlet;

import org.tt.core.dm.AbstractDataManager;
import org.tt.core.dm.TTData;
import org.tt.platform.factory.TTDataManagerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static spark.Spark.get;

public class Actions implements SparkApplication {
    @Override
    public void init() {
        final TTDataManagerFactory dmf = TTDataManagerFactory.getInstance();
        get(new Route("/department/:tag/group/:name") {
            @Override
            public Object handle(Request request, Response response) {
                AbstractDataManager dm = dmf.produce();

                String groupName = "";
                try {
                    groupName = URLDecoder.decode(request.params(":name"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                TTData result = dm.getTT(request.params(":tag"), groupName);
                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

                response.status(result.getHttpCode());

                return result.getMessage();
            }});

        get(new Route("/department/:tag/groups") {
            @Override
            public Object handle(Request request, Response response) {
                AbstractDataManager dm = dmf.produce();
                TTData result = dm.getGroups(request.params(":tag"));
                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");
                response.status(result.getHttpCode());
                return result.getMessage();
            }
        });


        get(new Route("/departments") {
            @Override
            public Object handle(Request request, Response response) {
                AbstractDataManager dm = dmf.produce();
                TTData result = dm.getDepartments();
                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

                response.status(result.getHttpCode());

                return result.getMessage();
            }
        });


        get(new Route("/cfg") {
            @Override
            public Object handle(Request request, Response response){
                AbstractDataManager dm = dmf.produce();
                TTData result = dm.getFormattedString(dmf.getTTConfiguration());
                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

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
