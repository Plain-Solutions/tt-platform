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

public class TTDataManagerFactory {
    private static TTDataManagerFactory ttdmf;


    private static AbstractDataFetcher adf;
    private static AbstractSQLManager asqlm;
    private static AbstractQueries aqrs;
    private static AbstractDataConverter adc;
    private static String dmClass;
    private static String loginPassword;

    private TTDataManagerFactory(){}

    public static TTDataManagerFactory getInstance(){
        if (ttdmf == null) {
            ttdmf = new TTDataManagerFactory();
        }
        return ttdmf;
    }

    public static void supplyDataFetcher(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.adf = (AbstractDataFetcher) Class.forName(classname).getConstructor(String.class).newInstance(loginPassword);

    }

    public static void supplySQLManagerConnection(Connection con, String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.asqlm = (AbstractSQLManager) Class.forName(classname).getConstructor(Connection.class).newInstance(con);

    }

    public static void supplyQueries(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.aqrs = (AbstractQueries) Class.forName(classname).getConstructor().newInstance();

    }

    public static void supplyDataConverter(String classname) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TTDataManagerFactory.adc = (AbstractDataConverter) Class.forName(classname).getConstructor().newInstance();

    }

    public static void supplyLDFCredential(String loginPassword) {
        TTDataManagerFactory.loginPassword = loginPassword;

    }

    public static void supplyDataManager (String classname) {
        TTDataManagerFactory.dmClass = classname;

    }

    public AbstractDataManager produce() {
        try {
            return (AbstractDataManager)Class.forName(dmClass).getConstructor(AbstractSQLManager.class, AbstractQueries.class, AbstractDataFetcher.class, AbstractDataConverter.class).newInstance(asqlm, aqrs, adf, adc);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
