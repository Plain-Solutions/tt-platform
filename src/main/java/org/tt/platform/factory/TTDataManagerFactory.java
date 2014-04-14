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

package org.tt.platform.factory;

import org.tt.core.dm.AbstractDataConverter;
import org.tt.core.dm.AbstractDataManager;
import org.tt.core.fetch.AbstractDataFetcher;
import org.tt.core.sql.AbstractQueries;
import org.tt.core.sql.AbstractSQLManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TTDataManagerFactory {
    private static TTDataManagerFactory ttdmf;
    private static Configuration cfg;

    private static AbstractDataFetcher adf;
    private static AbstractSQLManager asqlm;
    private static AbstractQueries aqrs;
    private static AbstractDataConverter adc;
    private static String dmClass;
    private static String url;

    private static class Configuration {
        private static String adfClass;
        private static String asqlmClass;
        private static String aqrsClass;
        private static String adcClass;
        private static String dmClass;
        private static String url;

        public Configuration() {}

        public static void setAdfClass(String adfClass) {
            Configuration.adfClass = adfClass;
        }

        public static void setAsqlmClass(String asqlmClass) {
            Configuration.asqlmClass = asqlmClass;
        }

        public static void setAqrsClass(String aqrsClass) {
            Configuration.aqrsClass = aqrsClass;
        }

        public static void setAdcClass(String adcClass) {
            Configuration.adcClass = adcClass;
        }

        public static void setDmClass(String dmClass) {
            Configuration.dmClass = dmClass;
        }

        public static void setUrl(String url) {
            Configuration.url = url;
        }

        public Map<String, String> getConfig() {
            Map<String, String> cfg = new LinkedHashMap<>();

            cfg.put("df", adfClass);
            cfg.put("sqlm", asqlmClass);
            cfg.put("qrs", aqrsClass);
            cfg.put("dm", dmClass);
            cfg.put("url",url);

            return cfg;
        }
    }

    private TTDataManagerFactory(){}

    public static TTDataManagerFactory getInstance(){
        if (ttdmf == null) {
            ttdmf = new TTDataManagerFactory();
            cfg = new Configuration();
        }
        return ttdmf;
    }

    public static void supplyDataFetcher(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.adf = (AbstractDataFetcher) Class.forName(classname).getConstructor().newInstance();
        Configuration.setAdfClass(classname);
    }

    public static void supplySQLManagerConnection(Connection con, String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.asqlm = (AbstractSQLManager) Class.forName(classname).getConstructor(Connection.class).newInstance(con);
        Configuration.setAsqlmClass(classname);
    }

    public static void supplyQueries(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.aqrs = (AbstractQueries) Class.forName(classname).getConstructor().newInstance();
        Configuration.setAqrsClass(classname);
    }

    public static void supplyDataConverter(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.adc = (AbstractDataConverter) Class.forName(classname).getConstructor().newInstance();
        Configuration.setAdcClass(classname);
    }

    public static void supplyDataFetchingURL(String url) {
        TTDataManagerFactory.url = url;
        Configuration.setUrl(url);
    }

    public static void supplyDataManager (String classname) {
        TTDataManagerFactory.dmClass = classname;
        Configuration.setDmClass(classname);
    }

    public AbstractDataManager produce() {
        try {
            return (AbstractDataManager)Class.forName(dmClass).getConstructor(AbstractSQLManager.class, AbstractQueries.class, AbstractDataFetcher.class, AbstractDataConverter.class, String.class).newInstance(asqlm, aqrs, adf, adc, url);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getTTConfiguration() {
        return cfg.getConfig();
    }


}
