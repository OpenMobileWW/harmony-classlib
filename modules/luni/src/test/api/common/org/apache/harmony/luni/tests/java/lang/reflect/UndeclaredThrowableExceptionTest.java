/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.harmony.luni.tests.java.lang.reflect;

import java.lang.reflect.UndeclaredThrowableException;

import junit.framework.TestCase;

import org.junit.Test;

public class UndeclaredThrowableExceptionTest extends TestCase {

    /**
     * @tests {@link java.lang.reflect.UndeclaredThrowableException#UndeclaredThrowableException(java.lang.Throwable)}
     *
     */
    @Test
    public void test_UndeclaredThrowableException_LThrowable() {
        UndeclaredThrowableException e = new UndeclaredThrowableException(
                (Exception) null);
        assertNotNull(e);
        assertNull(e.getCause());

    }

    /**
     * @tests {@link java.lang.reflect.UndeclaredThrowableException#UndeclaredThrowableException(java.lang.Throwable, java.lang.String)}
     *
     */
    @Test
    public void test_UndeclaredThrowableException_LThrowable_LString() {
        UndeclaredThrowableException e = new UndeclaredThrowableException(null,
                "SomeMsg");
        assertNotNull(e);
        assertNull(e.getCause());
        assertEquals("Wrong message", "SomeMsg", e.getMessage());
    }

    /**
     * @tests {@link java.lang.reflect.UndeclaredThrowableException#getUndeclaredThrowable()}
     */
    @Test
    public void test_getUndeclaredThrowable() {
        UndeclaredThrowableException e = new UndeclaredThrowableException(null);
        assertNotNull(e);
        assertNull(e.getUndeclaredThrowable());
    }
}
