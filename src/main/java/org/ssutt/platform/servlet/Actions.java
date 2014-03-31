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

import org.ssutt.platform.communicator.CommunicationPool;
import org.ssutt.platform.communicator.TTDataCommunicator;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class Actions implements SparkApplication {
    @Override
    public void init() {
        final CommunicationPool cp = CommunicationPool.getInstance();
        get(new Route("/department/:tag/group/:name") {
            @Override
            public Object handle(Request request, Response response) {
                TTDataCommunicator dc = cp.getDCinstance();

                String result =  dc.getTT(Integer.parseInt(
                        dc.getGroupID(
                                request.params(":tag"), request.params(":name"))
                ));
                if (result.contains("error"))
                    response.status(404);
                else
                    response.status(200);
                return result;
            }
        });
        get(new Route("/department/:tag/groups") {
            @Override
            public Object handle(Request request, Response response) {
                TTDataCommunicator dc = cp.getDCinstance();
                System.out.println(dc.getGroupNames(request.params(":tag")));
                String result = dc.getGroupNames(request.params(":tag"));

                if (result.contains("error"))
                    response.status(404);
                else
                    response.status(200);
                return result;
            }
        });


        get(new Route("/departments") {
            @Override
            public Object handle(Request request, Response response) {
                TTDataCommunicator dc = cp.getDCinstance();
                String result = dc.getDepartments();
                if (result.contains("error"))
                    response.status(404);
                else
                    response.status(200);
                return result;
            }
        });


    }
}
