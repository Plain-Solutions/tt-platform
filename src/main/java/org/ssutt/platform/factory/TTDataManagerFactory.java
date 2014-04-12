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

package org.ssutt.platform.factory;

import org.tt.core.dm.AbstractDataConverter;
import org.tt.core.dm.SSUDataManager;
import org.tt.core.fetch.AbstractDataFetcher;
import org.tt.core.sql.AbstractQueries;
import org.tt.core.sql.AbstractSQLManager;

import java.sql.Connection;

public class TTDataManagerFactory {
    private static TTDataManagerFactory ttdmf;

    private static AbstractDataFetcher adf;
    private static AbstractSQLManager asqlm;
    private static AbstractQueries aqrs;
    private static AbstractDataConverter adc;
    private static String url;

    private TTDataManagerFactory(){}

    public static TTDataManagerFactory getInstance(){
        if (ttdmf == null) {
            ttdmf = new TTDataManagerFactory();
        }
        return ttdmf;
    }

    public static void supplyDataFetcher(String classname) {
        TTDataManagerFactory.adf = adf;
    }

    public static void supplySQLManagerConnection(Connection con) {

        TTDataManagerFactory.asqlm = asqlm;
    }

    public static void supplyQueries(String classname) {
        TTDataManagerFactory.aqrs = aqrs;
    }

    public static void supplyDataConverter(String classname) {
        TTDataManagerFactory.adc = adc;
    }

    public static void supplyDataFetchingURL(String url) {TTDataManagerFactory.url = url; }

    public SSUDataManager produce() {
        return new SSUDataManager(asqlm, aqrs, adf, adc, url);
    }



}
