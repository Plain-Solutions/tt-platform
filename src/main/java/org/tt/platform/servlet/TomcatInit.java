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

import org.tt.platform.factory.TTDataManagerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
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

        TTDataManagerFactory ttdmf = TTDataManagerFactory.getInstance();
        Properties prop = new Properties();

        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/WEB-INF/tt.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(prop.getProperty("sqlm"));

    }







    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
