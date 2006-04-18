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

package tests.api.java.beans;

import java.awt.Component;
import java.beans.BeanDescriptor;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import junit.framework.TestCase;

/**
 * Unit test for BeanDescriptor.
 */
public class BeanDescriptorTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Class under test for void BeanDescriptor(Class)
	 */
	public void testBeanDescriptorClass() {
		String beanName = "BeanDescriptorTest.bean";
		MockJavaBean bean = new MockJavaBean(beanName);
		Class beanClass = bean.getClass();
		BeanDescriptor bd = new BeanDescriptor(beanClass);

		assertSame(beanClass, bd.getBeanClass());
		String displayName = beanClass.getName().substring(
				beanClass.getName().lastIndexOf('.') + 1);
		assertEquals(displayName, bd.getDisplayName());
		assertEquals(displayName, bd.getName());
		assertEquals(displayName, bd.getShortDescription());

		assertNotNull(bd.attributeNames());
		assertFalse(bd.isExpert());
		assertFalse(bd.isHidden());
		assertFalse(bd.isPreferred());
	}

	public void testBeanDescriptorClass_Null() {
		try {
			BeanDescriptor bd = new BeanDescriptor(null);
			fail("Should throw NullPointerException.");
		} catch (NullPointerException e) {
		}
	}

	/*
	 * Class under test for void BeanDescriptor(Class, Class)
	 */
	public void testBeanDescriptorClassClass() {
		/*
		 * String beanName = "BeanDescriptorTest.bean"; MockJavaBean bean = new
		 * MockJavaBean(beanName); Class beanClass = bean.getClass(); Customizer
		 * customizer = new MyCustomizer(); Class cusClass =
		 * customizer.getClass(); BeanDescriptor bd = new
		 * BeanDescriptor(beanClass, cusClass);
		 * 
		 * assertSame(beanClass, bd.getBeanClass()); assertSame(cusClass,
		 * bd.getCustomizerClass());
		 * 
		 * String displayName = beanClass.getName().substring(
		 * beanClass.getName().lastIndexOf('.') + 1); assertEquals(displayName,
		 * bd.getDisplayName()); assertEquals(displayName, bd.getName());
		 * assertEquals(displayName, bd.getShortDescription());
		 * 
		 * assertNotNull(bd.attributeNames()); assertFalse(bd.isExpert());
		 * assertFalse(bd.isHidden()); assertFalse(bd.isPreferred());
		 */
	}

	public void testBeanDescriptorClassClass_BeanClassNull() {
		/*
		 * Class beanClass = null; Customizer customizer = new MyCustomizer();
		 * Class cusClass = customizer.getClass(); try { BeanDescriptor bd = new
		 * BeanDescriptor(beanClass, cusClass); fail("Should throw
		 * NullPointerException"); } catch (NullPointerException e) { }
		 */
	}

	public void testBeanDescriptorClassClass_CustomizerClassNull() {
		String beanName = "BeanDescriptorTest.bean";
		MockJavaBean bean = new MockJavaBean(beanName);
		Class beanClass = bean.getClass();
		Class cusClass = null;
		BeanDescriptor bd = new BeanDescriptor(beanClass, cusClass);

		assertSame(beanClass, bd.getBeanClass());
		assertNull(bd.getCustomizerClass());

		String displayName = beanClass.getName().substring(
				beanClass.getName().lastIndexOf('.') + 1);
		assertEquals(displayName, bd.getDisplayName());
		assertEquals(displayName, bd.getName());
		assertEquals(displayName, bd.getShortDescription());

		assertNotNull(bd.attributeNames());
		assertFalse(bd.isExpert());
		assertFalse(bd.isHidden());
		assertFalse(bd.isPreferred());
	}

	class MyCustomizer extends Component implements Customizer {

		HashSet listeners;

		Object bean;

		public MyCustomizer() {
			this.listeners = new HashSet();
			this.bean = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.Customizer#addPropertyChangeListener(java.beans.PropertyChangeListener)
		 */
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			this.listeners.add(listener);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.Customizer#removePropertyChangeListener(java.beans.PropertyChangeListener)
		 */
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			this.listeners.remove(listener);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.Customizer#setObject(java.lang.Object)
		 */
		public void setObject(Object bean) {
			this.bean = bean;
		}

	}
}
