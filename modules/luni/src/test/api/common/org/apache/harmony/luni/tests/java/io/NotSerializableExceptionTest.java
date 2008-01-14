/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.luni.tests.java.io;

import java.io.NotSerializableException;

public class NotSerializableExceptionTest extends junit.framework.TestCase {

	/**
	 * @tests java.io.NotSerializableException#NotSerializableException()
	 */
	public void test_Constructor() {
        NotSerializableException nse = new NotSerializableException();
        assertNull(nse.getMessage());
	}

	/**
	 * @tests java.io.NotSerializableException#NotSerializableException(java.lang.String)
	 */
	public void test_ConstructorLjava_lang_String() {
		// Test for method java.io.NotSerializableException(java.lang.String)
        String message = "Test message";
        NotSerializableException nse = new NotSerializableException(message);
        assertSame(message, nse.getMessage());
	}

	/**
	 * Sets up the fixture, for example, open a network connection. This method
	 * is called before a test is executed.
	 */
	protected void setUp() {
	}

	/**
	 * Tears down the fixture, for example, close a network connection. This
	 * method is called after a test is executed.
	 */
	protected void tearDown() {
	}
}