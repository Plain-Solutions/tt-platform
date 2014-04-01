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
import org.ssutt.core.dm.convert.json.JSONConverter;
import org.ssutt.core.fetch.SSUDataFetcher;
import org.ssutt.core.sql.H2Queries;
import org.ssutt.core.sql.SSUSQLManager;
import org.ssutt.platform.factory.TTDataManagerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.SQLException;

public class TomcatInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


        DataSource ds = null;
        try {
            Context initContext = new InitialContext();
            ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/ttDS");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        TTDataManagerFactory ttdmf = TTDataManagerFactory.getInstance();
        if (ds != null) {
            try {
                TTDataManagerFactory.supplySQLManager(new SSUSQLManager(ds.getConnection()));
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        TTDataManagerFactory.supplyQueries(new H2Queries());
        TTDataManagerFactory.supplyDataFetcher(new SSUDataFetcher());
        TTDataManagerFactory.supplyDataConverter(new JSONConverter());

        SSUDataManager dm = ttdmf.produce();
        TTData status = dm.putDepartments();
        if (status.getHttpCode()!=404) {
            status =dm.putAllGroups();
            if (status.getHttpCode()!=404) {
                status = dm.getDepartmentTags();
                for (String dep: dm.getJSONConverter().reverseConvertGroup(status.getMessage()))
                    for (String group: dm.getJSONConverter().reverseConvertGroup(dm.getGroups(dep).getMessage())) {
                        dm.putTT(dep, Integer.parseInt(dm.getGroupID(dep,group).getMessage()));
                    }

            }
        }




    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
