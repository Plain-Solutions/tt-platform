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
import org.tt.platform.convert.AbstractDataConverter;
import org.tt.platform.convert.json.JSONConverter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;


@Path("/")
public class Resources {
    final TTFactory ttf = TTFactory.getInstance();
    final AbstractDataConverter dconv = new JSONConverter();

    final int ResponseSQLErrorCode = 209;
    final int ResponseNoSuchDepartmentCode = 309;
    final int ResponseNoSuchGroupCode = 399;
    final int ResponseUnsupportedEncodingCode = 509;


    @GET
    @Path("/2/department/{tag}/group/{name}")
    public Response getPlainerTT(@PathParam("tag") String tag, @PathParam("name") String name) {
        Response.ResponseBuilder r= Response.ok();
        r.header("Access-Control-Allow-Origin", "*");
        r.header("Access-Control-Allow-Methods", "GET");
        r.type("application/json;charset=UTF-8");

        String groupName;
        try {
            groupName = URLDecoder.decode(name, "UTF-8");
            TTDeliveryManager ttdm = ttf.produceDeliveryManager();
            try {
                TTEntity result = ttdm.getTT(tag, groupName);
                r.status(Response.Status.OK);
                r.entity(dconv.convertTTPlainer(result));
            } catch (SQLException e) {
                e.printStackTrace();
                r.status(this.ResponseSQLErrorCode);
                r.entity(dconv.returnSQLErrMsg(e.getSQLState()));
            } catch (NoSuchDepartmentException e) {
                e.printStackTrace();
                r.status(this.ResponseNoSuchDepartmentCode);
                r.entity(dconv.returnNoSuchDepEx());
            } catch (NoSuchGroupException e) {
                e.printStackTrace();
                r.status(this.ResponseNoSuchGroupCode);
                r.entity(dconv.returnNoSuchGrEx());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            r.status(this.ResponseUnsupportedEncodingCode);
            r.entity("{errmsg: Invalid encoding}");
        }
        return r.build();
    }

    @GET
    @Path("/1/department/{tag}/group/{name}")
    public Response getTT(@PathParam("tag") String tag, @PathParam("name") String name) {
        Response.ResponseBuilder r= Response.ok();
        r.header("Access-Control-Allow-Origin", "*");
        r.header("Access-Control-Allow-Methods", "GET");
        r.type("application/json;charset=UTF-8");

        String groupName;
        try {
            groupName = URLDecoder.decode(name, "UTF-8");
            TTDeliveryManager ttdm = ttf.produceDeliveryManager();
            try {
                TTEntity result = ttdm.getTT(tag, groupName);
                r.status(Response.Status.OK);
                r.entity(dconv.convertTT(result));
            } catch (SQLException e) {
                e.printStackTrace();
                r.status(this.ResponseSQLErrorCode);
                r.entity(dconv.returnSQLErrMsg(e.getSQLState()));
            } catch (NoSuchDepartmentException e) {
                e.printStackTrace();
                r.status(this.ResponseNoSuchDepartmentCode);
                r.entity(dconv.returnNoSuchDepEx());
            } catch (NoSuchGroupException e) {
                e.printStackTrace();
                r.status(this.ResponseNoSuchGroupCode);
                r.entity(dconv.returnNoSuchGrEx());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            r.status(this.ResponseUnsupportedEncodingCode);
            r.entity("{errmsg: Invalid encoding}");
        }
        return r.build();
    }


    @GET
    @Path("/1/department/{tag}/groups")
    public Response getAllGroups(@PathParam("tag") String tag, @QueryParam("filled") int fullfill) {
        Response.ResponseBuilder r = Response.ok();
        r.header("Access-Control-Allow-Origin", "*");
        r.header("Access-Control-Allow-Methods", "GET");
        r.type("application/json;charset=UTF-8");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Group> result;
            if (fullfill == 1)
                result = ttdm.getNonEmptyGroups(tag);
            else
                result = ttdm.getGroups(tag);
            r.status(Response.Status.OK);
            r.entity(dconv.convertGroupList(result));
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            r.status(this.ResponseNoSuchDepartmentCode);
            r.entity(dconv.returnNoSuchDepEx());
        } catch (SQLException e) {
            e.printStackTrace();
            r.status(this.ResponseSQLErrorCode);
            r.entity(dconv.returnSQLErrMsg(e.getSQLState()));
        } catch (NoSuchGroupException e) {
            e.printStackTrace();
            r.status(this.ResponseNoSuchGroupCode);
            r.entity(dconv.returnNoSuchGrEx());
        }
        return r.build();
    }

    @GET
    @Path("/2/department/{tag}/groups")
    public Response getAllGroupsForward(@PathParam("tag") String tag, @QueryParam("filled") int fullfill) {
        return this.getAllGroups(tag, fullfill);
    }

    @GET
    @Path("/1/department/{tag}/msg")
    public Response getDepartmentMessage(@PathParam("tag") String tag) {
        Response.ResponseBuilder r = Response.ok();
        r.header("Access-Control-Allow-Origin", "*");
        r.header("Access-Control-Allow-Methods", "GET");
        r.type("application/json;charset=UTF-8");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            String result = ttdm.getDepartmentMessage(tag);
            r.status(Response.Status.OK);
            r.entity(dconv.convertDepartmentMessage(result));
        } catch (NoSuchDepartmentException e) {
            e.printStackTrace();
            r.status(this.ResponseNoSuchDepartmentCode);
            r.entity(dconv.returnNoSuchDepEx());
        } catch (SQLException e) {
            e.printStackTrace();
            r.status(this.ResponseSQLErrorCode);
            r.entity(dconv.returnSQLErrMsg(e.getSQLState()));
        }

        return r.build();
    }

    @GET
    @Path("/2/department/{tag}/msg")
    public Response getDepartmentMessageForward(@PathParam("tag") String tag) {
        return this.getDepartmentMessage(tag);
    }

    @GET
    @Path("/1/departments")
    public Response getDepartments() {
        Response.ResponseBuilder r = Response.ok();
        r.header("Access-Control-Allow-Origin", "*");
        r.header("Access-Control-Allow-Methods", "GET");
        r.type("application/json;charset=UTF-8");

        TTDeliveryManager ttdm = ttf.produceDeliveryManager();
        try {
            List<Department> result = ttdm.getDepartments();
            r.status(Response.Status.OK);
            r.entity(dconv.convertDepartmentList(result));

        } catch (SQLException e) {
            e.printStackTrace();
            r.status(this.ResponseSQLErrorCode);
            r.entity(dconv.returnSQLErrMsg(e.getSQLState()));
       }
       return r.build();
    }

    @GET
    @Path("/2/departments")
    public Response getDepartmentsForward() {
        return this.getDepartments();
    }

}

