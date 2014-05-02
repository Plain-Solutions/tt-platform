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

import org.quartz.SchedulerException;
import org.tt.core.dm.TTFactory;
import org.tt.core.dm.TTUpdateManager;
import org.tt.core.fetch.AbstractDataFetcher;
import org.tt.core.sql.AbstractQueries;
import org.tt.core.sql.AbstractSQLManager;
import org.tt.core.sql.ex.NoSuchDepartmentException;
import org.tt.core.sql.ex.NoSuchGroupException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

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

        Properties prop = new Properties();

        try {
            prop.load(servletContextEvent.getServletContext().getResourceAsStream("/WEB-INF/tt.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert ds != null;
        TTFactory ttf = TTFactory.getInstance();

        try {
            Connection ttDBConnection = ds.getConnection();

            String sqlManagerImplementation = prop.getProperty("sqlm");
            String queriesImplementation = prop.getProperty("qrs");
            String dataFetcherImplementation = prop.getProperty("df");
            String ldfCreds = prop.getProperty("ldf");

            AbstractSQLManager sqlm = (AbstractSQLManager) Class.forName(sqlManagerImplementation).
                    getConstructor(Connection.class).newInstance(ttDBConnection);
            AbstractQueries qrs = (AbstractQueries) Class.forName(queriesImplementation).
                    getConstructor().newInstance();
            AbstractDataFetcher df = (AbstractDataFetcher) Class.forName(dataFetcherImplementation).
                    getConstructor(String.class).newInstance(ldfCreds);

            ttf.setSQLManager(sqlm, qrs);
            ttf.setDataFetcher(df);

        } catch (SQLException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException |
                InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        TTUpdateManager updm = ttf.produceUpdateManager();
        try {
            updm.initUpdateJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        File lock = new File("tt.lock");

        if (!lock.exists()) {
            try {
                System.out.println("No lock file found. Creating one.");
                new FileOutputStream(lock).close();
                updm.initFulfillment();
            } catch (IOException | SQLException | NoSuchGroupException | NoSuchDepartmentException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
