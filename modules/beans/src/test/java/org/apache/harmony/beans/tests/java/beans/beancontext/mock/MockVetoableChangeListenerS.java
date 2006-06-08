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

package org.apache.harmony.beans.tests.java.beans.beancontext.mock;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

/**
 * Mock of VetoableChangeListener
 */
public class MockVetoableChangeListenerS implements VetoableChangeListener,
		Serializable {

	public PropertyChangeEvent lastEvent;

	public String id;

	public MockVetoableChangeListenerS(String id) {
		this.id = id;
	}

	public void clearLastEvent() {
		lastEvent = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
	 */
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		lastEvent = evt;
	}

}
