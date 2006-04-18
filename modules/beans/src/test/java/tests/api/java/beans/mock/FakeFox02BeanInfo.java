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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The series of FakeFox classes are used to test
 * Introspector.getBeanInfo(Class, int)
 */
public class FakeFox02BeanInfo extends SimpleBeanInfo {

	public FakeFox02BeanInfo() {
		super();
	}

	public FakeFox02BeanInfo(String param) {
		super();
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor pd = null;
		try {
			pd = new PropertyDescriptor("fox02", FakeFox02.class);
			pd.setDisplayName("fox02.beaninfo");
		} catch (IntrospectionException e) {

		}
		return new PropertyDescriptor[] { pd };
	}
}
