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
import org.ssutt.core.dm.TTDataManager;
import org.ssutt.core.fetch.SSUDataFetcher;
import org.ssutt.core.sql.H2Queries;
import org.ssutt.core.sql.SSUSQLManager;
import org.ssutt.core.sql.ex.NoSuchDepartmentException;
import org.ssutt.core.sql.ex.NoSuchGroupException;
import org.ssutt.platform.communicator.CommunicationPool;
import org.ssutt.platform.communicator.Module;
import org.ssutt.platform.json.JSONHandler;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class TomcatInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        JSONHandler jsh = new JSONHandler();
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/ttDS");

            TTDataManager dm = new SSUDataManager();
            dm.deliverDBProvider(new SSUSQLManager(ds.getConnection()), new H2Queries());
            dm.deliverDataFetcherProvider(new SSUDataFetcher());

            //for access to initialized Manager outside servlets
            CommunicationPool cp = CommunicationPool.getInstance();
            CommunicationPool.setDataManager(dm);
            CommunicationPool.setJSONHandler(jsh);
            dm.putDepartments();
            dm.putAllGroups();
            for (String d : dm.getDepartmentTags())
                for (String gr : dm.getGroups(d)) {
                    dm.putTT(d, dm.getGroupID(d, gr));
                }
        } catch (SQLException | NamingException e) {
            jsh.getFailure(Module.GENSQL.name(), e.getMessage());
        } catch (NoSuchDepartmentException | NoSuchGroupException e) {
            jsh.getFailure(Module.TTSQL.name(), e.getMessage());
        } catch (IOException e) {
            jsh.getFailure(Module.IO.name(), e.getMessage());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
