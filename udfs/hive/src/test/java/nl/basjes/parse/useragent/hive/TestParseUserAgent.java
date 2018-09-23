/*
 * Yet Another UserAgent Analyzer
 * Copyright (C) 2013-2018 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.parse.useragent.hive;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StandardStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestParseUserAgent {

    private ParseUserAgent parseUserAgent = new ParseUserAgent();

    @Test
    public void testBasic() throws HiveException {
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

        StandardStructObjectInspector resultInspector = (StandardStructObjectInspector) parseUserAgent
            .initialize(new ObjectInspector[]{
                PrimitiveObjectInspectorFactory.javaStringObjectInspector
            });

        for (int i = 0; i < 100000; i++) {
            Object row = parseUserAgent.evaluate(new DeferredObject[]{new DeferredJavaObject(userAgent)});

            checkField(resultInspector, row, "DeviceName", "Linux Desktop");
            checkField(resultInspector, row, "AgentNameVersion", "Chrome 58.0.3029.110");
            checkField(resultInspector, row, "AgentNameVersionMajor", "Chrome 58");
        }
    }

    @Test
    public void testDisplayString(){
        assertNotNull(parseUserAgent.getDisplayString(null));
    }

    private void checkField(StandardStructObjectInspector resultInspector, Object row, String fieldName, String expectedValue) {
        Object fieldData = resultInspector.getStructFieldData(row, resultInspector.getStructFieldRef(fieldName));
        if (expectedValue == null) {
            assertNull(fieldData);
        } else {
            assertEquals(expectedValue, fieldData.toString());
        }
    }

    @Test
    public void testExplain() {
        assertTrue("Wrong explanation", parseUserAgent.getDisplayString(null).contains("UserAgent"));
    }

}
