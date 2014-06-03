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
import javax.ws.rs.*;
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
        String groupName;
        try {
            groupName = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response.setStatus(404);
            return "{errmsg: Invalid encoding}";
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            TTEntity result = ttdm.getTT(tag, groupName);
            response.setStatus(200);
            return dconv.convertTT(result);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnSQLErrMsg(e.getSQLState());
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnNoSuchDepEx();
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnNoSuchGrEx();
        }
    }


    @GET
    @Path("/department/{tag}/groups")
    @Produces("application/json;charset=UTF-8")
    public String getAllGroups(@PathParam("tag") String tag, @QueryParam("filled") int fullfill, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Group> result;
            if (fullfill == 1)
                result = ttdm.getNonEmptyGroups(tag);
            else
                result = ttdm.getGroups(tag);
            response.setStatus(200);
            return dconv.convertGroupList(result);
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnNoSuchDepEx();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnSQLErrMsg(e.getSQLState());
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnNoSuchGrEx();
        }
    }


    @GET
    @Path("/department/{tag}/msg")
    @Produces("application/json;charset=UTF-8")
    public String getDepartmentMessage(@PathParam("tag") String tag, @Context HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            String result = ttdm.getDepartmentMessage(tag);
            response.setStatus(200);
            return dconv.convertDepartmentMessage(result);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnSQLErrMsg(e.getSQLState());
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            response.setStatus(404);
            return dconv.returnNoSuchDepEx();
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
            return dconv.returnSQLErrMsg(e.getSQLState());
        }
    }

    @GET
    @Path("/loaderio-ccc6fd7b785e7be1002b0cfbbfbba736")
    public String lodario() {
        return "loaderio-ccc6fd7b785e7be1002b0cfbbfbba736";
    }

}
