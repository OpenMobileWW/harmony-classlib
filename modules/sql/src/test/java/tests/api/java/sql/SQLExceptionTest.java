/* Copyright 2004 The Apache Software Foundation or its licensors, as applicable
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

package tests.api.java.sql;

import java.lang.reflect.Field;
import java.sql.SQLException;

import junit.framework.TestCase;

public class SQLExceptionTest extends TestCase {

	static long theFixedSUID = 2135244094396331484L;

	/*
	 * SUID test
	 */
	public void testSUID() {

		try {
			Class theClass = Class.forName("java.sql.SQLException");
			Field theField = theClass.getDeclaredField("serialVersionUID");
			theField.setAccessible(true);
			long theSUID = theField.getLong(null);
			assertEquals("SUID mismatch: ", theFixedSUID, theSUID);
		} catch (Exception e) {
			System.out.println("SUID check got exception: " + e.getMessage());
			// assertTrue("Exception while testing SUID ", false );
		} // end catch

	} // end method testSUID

	/*
	 * ConstructorTest
	 */
	public void testSQLExceptionStringStringint() {

		String[] init1 = { "a", "1", "valid1", "----", "&valid*", "1", "a",
				null, "", " ", "a", "a", "a" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", "a",
				"&valid*", "a", "a", "a", null, "", " " };
		int[] init3 = { -2147483648, 2147483647, 0, 48429456, 1770127344,
				1047282235, -545472907, -2147483648, -2147483648, -2147483648,
				-2147483648, -2147483648, -2147483648 };

		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = init3;
		SQLException[] theFinalStates4 = { null, null, null, null, null, null,
				null, null, null, null, null, null, null };

		Exception[] theExceptions = { null, null, null, null, null, null, null,
				null, null, null, null, null, null };

		SQLException aSQLException;
		int loopCount = init1.length;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i], init3[i]);
				if (theExceptions[i] != null)
					assertTrue(false);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testSQLExceptionStringStringint

	/*
	 * ConstructorTest
	 */
	public void testSQLExceptionStringString() {

		String[] init1 = { "a", "1", "valid1", "----", "&valid*", null, "",
				" ", "a", "a", "a" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", "a", "a",
				"a", null, "", " " };

		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = { (int) 0, (int) 0, (int) 0, (int) 0, (int) 0,
				(int) 0, (int) 0, (int) 0, (int) 0, (int) 0, (int) 0 };
		SQLException[] theFinalStates4 = { null, null, null, null, null, null,
				null, null, null, null, null };

		Exception[] theExceptions = { null, null, null, null, null, null, null,
				null, null, null, null };

		SQLException aSQLException;
		int loopCount = init1.length;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i]);
				if (theExceptions[i] != null)
					assertTrue(false);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testSQLExceptionStringString

	/*
	 * ConstructorTest
	 */
	public void testSQLExceptionString() {

		String[] init1 = { "a", "1", "valid1", "----", "&valid*", null, "", " " };

		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = { null, null, null, null, null, null, null,
				null };
		int[] theFinalStates3 = { (int) 0, (int) 0, (int) 0, (int) 0, (int) 0,
				(int) 0, (int) 0, (int) 0 };
		SQLException[] theFinalStates4 = { null, null, null, null, null, null,
				null, null };

		Exception[] theExceptions = { null, null, null, null, null, null, null,
				null };

		SQLException aSQLException;
		int loopCount = init1.length;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i]);
				if (theExceptions[i] != null)
					assertTrue(false);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testSQLExceptionString

	/*
	 * ConstructorTest
	 */
	public void testSQLException() {

		String[] theFinalStates1 = { null };
		String[] theFinalStates2 = { null };
		int[] theFinalStates3 = { (int) 0 };
		SQLException[] theFinalStates4 = { null };

		Exception[] theExceptions = { null };

		SQLException aSQLException;
		int loopCount = 1;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException();
				if (theExceptions[i] != null)
					assertTrue(false);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testSQLException

	/*
	 * Method test for getErrorCode
	 */
	public void testGetErrorCode() {

		SQLException aSQLException;
		String[] init1 = { "a", "1", "valid1", "----", null, "&valid*", "1" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", null, "a" };
		int[] init3 = { -2147483648, 2147483647, 0, 48429456, 1770127344,
				1047282235, -545472907 };

		int theReturn;
		int[] theReturns = init3;
		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = init3;
		SQLException[] theFinalStates4 = { null, null, null, null, null, null,
				null };

		Exception[] theExceptions = { null, null, null, null, null, null, null };

		int loopCount = 1;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i], init3[i]);
				theReturn = aSQLException.getErrorCode();
				if (theExceptions[i] != null)
					fail(i + "Exception missed");
				assertEquals(i + "Return value mismatch", theReturn,
						theReturns[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testGetErrorCode

	/*
	 * Method test for getNextException
	 */
	public void testGetNextException() {

		SQLException aSQLException;
		String[] init1 = { "a", "1", "valid1", "----", null, "&valid*", "1" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", null, "a" };
		int[] init3 = { -2147483648, 2147483647, 0, 48429456, 1770127344,
				1047282235, -545472907 };
		SQLException[] init4 = { new SQLException(), null, new SQLException(),
				new SQLException(), new SQLException(), null,
				new SQLException() };

		SQLException theReturn;
		SQLException[] theReturns = init4;
		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = init3;
		SQLException[] theFinalStates4 = init4;

		Exception[] theExceptions = { null, null, null, null, null, null, null };

		int loopCount = init1.length;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i], init3[i]);
				aSQLException.setNextException(init4[i]);
				theReturn = aSQLException.getNextException();
				if (theExceptions[i] != null)
					fail(i + "Exception missed");
				assertEquals(i + "Return value mismatch", theReturn,
						theReturns[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testGetNextException

	/*
	 * Method test for getSQLState
	 */
	public void testGetSQLState() {

		SQLException aSQLException;
		String[] init1 = { "a", "1", "valid1", "----", null, "&valid*", "1" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", null, "a" };
		int[] init3 = { -2147483648, 2147483647, 0, 48429456, 1770127344,
				1047282235, -545472907 };

		String theReturn;
		String[] theReturns = init2;
		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = init3;
		SQLException[] theFinalStates4 = { null, null, null, null, null, null,
				null };

		Exception[] theExceptions = { null, null, null, null, null, null, null };

		int loopCount = 1;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i], init3[i]);
				theReturn = aSQLException.getSQLState();
				if (theExceptions[i] != null)
					fail(i + "Exception missed");
				assertEquals(i + "Return value mismatch", theReturn,
						theReturns[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testGetSQLState

	/*
	 * Method test for setNextException
	 */
	public void testSetNextExceptionSQLException() {

		SQLException[] parm1 = { new SQLException(), null, new SQLException(),
				new SQLException(), new SQLException(), null,
				new SQLException() };

		SQLException aSQLException;

		String[] init1 = { "a", "1", "valid1", "----", null, "&valid*", "1" };
		String[] init2 = { "a", "1", "valid1", "----", "&valid*", null, "a" };
		int[] init3 = { -2147483648, 2147483647, 0, 48429456, 1770127344,
				1047282235, -545472907 };

		String[] theFinalStates1 = init1;
		String[] theFinalStates2 = init2;
		int[] theFinalStates3 = init3;
		SQLException[] theFinalStates4 = parm1;

		Exception[] theExceptions = { null, null, null, null, null, null, null,
				null, null, null, null };

		int loopCount = parm1.length;
		for (int i = 0; i < loopCount; i++) {
			try {
				aSQLException = new SQLException(init1[i], init2[i], init3[i]);
				aSQLException.setNextException(parm1[i]);
				if (theExceptions[i] != null)
					fail(i + "Exception missed");
				assertEquals(i + "  Final state mismatch", aSQLException
						.getMessage(), theFinalStates1[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getSQLState(), theFinalStates2[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getErrorCode(), theFinalStates3[i]);
				assertEquals(i + "  Final state mismatch", aSQLException
						.getNextException(), theFinalStates4[i]);

			} catch (Exception e) {
				if (theExceptions[i] == null)
					fail(i + "Unexpected exception");
				assertEquals(i + "Exception mismatch", e.getClass(),
						theExceptions[i].getClass());
				assertEquals(i + "Exception mismatch", e.getMessage(),
						theExceptions[i].getMessage());
			} // end try
		} // end for

	} // end method testSetNextExceptionSQLException

} // end class SQLExceptionTest

