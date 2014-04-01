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

import org.ssutt.core.dm.AbstractDataConverter;
import org.ssutt.core.dm.SSUDataManager;
import org.ssutt.core.fetch.AbstractDataFetcher;
import org.ssutt.core.sql.AbstractQueries;
import org.ssutt.core.sql.AbstractSQLManager;

public class TTDataManagerFactory {
    private static TTDataManagerFactory ttdmf;

    private static AbstractDataFetcher adf;
    private static AbstractSQLManager asqlm;
    private static AbstractQueries aqrs;
    private static AbstractDataConverter adc;


    private TTDataManagerFactory(){}

    public static TTDataManagerFactory getInstance(){
        if (ttdmf == null) {
            ttdmf = new TTDataManagerFactory();
        }
        return ttdmf;
    }

    public static void supplyDataFetcher(AbstractDataFetcher adf) {
        TTDataManagerFactory.adf = adf;
    }

    public static void supplySQLManager(AbstractSQLManager asqlm) {
        TTDataManagerFactory.asqlm = asqlm;
    }

    public static void supplyQueries(AbstractQueries aqrs) {
        TTDataManagerFactory.aqrs = aqrs;
    }

    public static void supplyDataConverter(AbstractDataConverter adc) {
        TTDataManagerFactory.adc = adc;
    }

    public SSUDataManager produce() {
        return new SSUDataManager(asqlm, aqrs, adf, adc);
    }



}
