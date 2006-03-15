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

package tests.api.javax.naming.directory;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for tests.api.javax.naming.directory");
		// $JUnit-BEGIN$
		suite.addTestSuite(TestModificationItem.class);
		suite.addTestSuite(TestInitialDirContext.class);
		suite.addTestSuite(TestSearchControls.class);
		suite.addTestSuite(TestBasicAttribute.class);
		suite.addTestSuite(TestAttributeModificationException.class);
		suite.addTestSuite(TestAttributeInUseException.class);
		suite.addTestSuite(TestSearchResult.class);
		suite.addTestSuite(TestBasicAttributes.class);
		// $JUnit-END$
		return suite;
	}
}
