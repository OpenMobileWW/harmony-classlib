/* Copyright 2005 The Apache Software Foundation or its licensors, as applicable
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

package tests.api.java.beans.mock;

import java.applet.Applet;
import java.beans.AppletInitializer;
import java.beans.beancontext.BeanContext;

/**
 * test java.beans.Beans
 */
public class MockAppletInitializer implements AppletInitializer {

	private boolean activateBeenCalled;

	private boolean initializeBeenCalled;

	public boolean activateHasBeenCalled() {
		return this.activateBeenCalled;
	}

	public boolean initializeHasBeenCalled() {
		return this.initializeBeenCalled;
	}

	public void activate(Applet newApplet) {
		this.activateBeenCalled = true;
	}

	public void initialize(Applet appletBean, BeanContext context) {
		this.initializeBeenCalled = true;
	}

}
