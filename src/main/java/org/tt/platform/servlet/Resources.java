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
import org.tt.core.entity.datafetcher.Department;
import org.tt.core.entity.datafetcher.Group;
import org.tt.core.entity.db.TTEntity;
import org.tt.core.sql.ex.NoSuchDepartmentException;
import org.tt.core.sql.ex.NoSuchGroupException;
import org.tt.platform.convert.json.JSONConverter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;


@Path("/")
public class Resources {
    final TTFactory ttf = TTFactory.getInstance();
    final JSONConverter dconv = new JSONConverter();

    @GET
    @Produces("text/plain")
    public Response goTODevPage() throws URISyntaxException {
        return Response.seeOther(new URI("http://ssutt.org/developers")).build();
    }

    @GET
    @Path("/department/{tag}/group/{name}")
    @Produces("application/json;charset=UTF-8")
    public String getTT(@PathParam("tag") String tag, @PathParam("name") String name, @Context HttpServletResponse response) {
        TTDeliveryManager ttdm = ttf.produceDeliveryManager();

        String groupName;
        try {
            groupName = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: Invalid encoding}";
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        try {
            TTEntity result = ttdm.getTT(tag, groupName);
            response.setStatus(200);
            return dconv.convertTT(result);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return String.format("{msg:%s}", e.getSQLState());
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: No such department found}";
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: No such group found}";
        }
    }


    @GET
    @Path("/department/{tag}/groups/all")
    @Produces("application/json;charset=UTF-8")
    public String getAllGroups(@PathParam("tag") String tag, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Group> result = ttdm.getGroups(tag);
            response.setStatus(200);
            return dconv.convertGroupList(result);
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: No such department found}";
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return String.format("{msg:%s}", e.getSQLState());
        }
    }

    @GET
    @Path("/department/{tag}/groups/nemp")
    @Produces("application/json;charset=UTF-8")
    public String getNonEmptyGroups(@PathParam("tag") String tag, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Group> result = ttdm.getNonEmptyGroups(tag);
            response.setStatus(200);
            return dconv.convertGroupList(result);
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: No such department found}";
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return String.format("{msg:%s}", e.getSQLState());
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{msg: No such group found}";
        }
    }

    @GET
    @Path("/departments")
    @Produces("application/json;charset=UTF-8")
    public String getDepartments(@Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Department> result = ttdm.getDepartments();
            response.setStatus(200);
            return dconv.convertDepartmentList(result);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return String.format("{msg:%s}", e.getSQLState());
        }
    }

    @GET
    @Path("/loaderio-0f720aed82610a1c7e9893e9640aac51")
    public String lodario() {
        return "loaderio-0f720aed82610a1c7e9893e9640aac51";
    }

}
