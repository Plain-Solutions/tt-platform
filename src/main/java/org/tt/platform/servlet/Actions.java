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

import org.tt.core.dm.TTDeliveryManager;
import org.tt.core.dm.TTFactory;
import org.tt.core.entity.datafetcher.Group;
import org.tt.core.entity.db.TTEntity;
import org.tt.core.sql.ex.NoSuchDepartmentException;
import org.tt.core.sql.ex.NoSuchGroupException;
import org.tt.platform.convert.json.JSONConverter;
import org.tt.core.entity.datafetcher.Department;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import static spark.Spark.get;

public class Actions implements SparkApplication {
    @Override
    public void init() {
        final TTFactory ttf = TTFactory.getInstance();
        final JSONConverter dconv = new JSONConverter();
        get(new Route("/department/:tag/group/:name") {
            @Override
            public Object handle(Request request, Response response) {
                TTDeliveryManager ttdm = ttf.produceDeliveryManager();

                String groupName = "";
                try {
                    groupName = URLDecoder.decode(request.params(":name"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

                try {
                    TTEntity result = ttdm.getTT(request.params(":tag"), groupName);
                    response.status(200);
                    return dconv.convertTT(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.status(404);
                    return String.format("{msg:%s}", e.getSQLState());
                } catch (NoSuchDepartmentException e) {
                    e.printStackTrace();
                    response.status(404);
                    return "{msg: No such department found}";
                } catch (NoSuchGroupException e) {
                    e.printStackTrace();
                    response.status(404);
                    return "{msg: No such group found}";
                }


            }});

        get(new Route("/department/:tag/groups") {
            @Override
            public Object handle(Request request, Response response) {
                TTDeliveryManager ttdm = ttf.produceDeliveryManager();

                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

                try {
                    List<Group> result = ttdm.getGroups(request.params(":tag"));
                    response.status(200);
                    return dconv.convertGroupList(result);
                } catch (NoSuchDepartmentException e) {
                    e.printStackTrace();
                    response.status(404);
                    return "{msg: No such department found}";
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.status(404);
                    return String.format("{msg:%s}", e.getSQLState());
                }
            }
        });


        get(new Route("/departments") {
            @Override
            public Object handle(Request request, Response response) {
                TTDeliveryManager ttdm = ttf.produceDeliveryManager();

                response.type("application/json");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET");

                try {
                    List<Department> result = ttdm.getDepartments();
                    response.status(200);
                    return dconv.convertDepartmentList(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.status(404);
                    return String.format("{msg:%s}", e.getSQLState());
                }
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
