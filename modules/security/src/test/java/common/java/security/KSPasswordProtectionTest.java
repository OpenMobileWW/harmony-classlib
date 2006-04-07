/*
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
* @author Vera Y. Petrashkova
* @version $Revision$
*/

package java.security;


import javax.security.auth.DestroyFailedException;

import junit.framework.TestCase;

/**
 * Tests for <code>KeyStore.PasswordProtection</code> class constructor and methods
 * 
 */

public class KSPasswordProtectionTest extends TestCase {

    /**
     * Constructor for KSPasswordProtectionTest.
     * @param arg0
     */
    public KSPasswordProtectionTest(String arg0) {
        super(arg0);
    }
    
    /**
     * Test for <code>KeyStore.PasswordProtection(char[] password)</code> constructor
     * and the following methods 
     * <code>getPassword()<code>
     * <code>destroy()<code>
     * <code>isDestroyed()<code>
     * Assertions: constructor created new PasswordProtection object
     * getPassword() returns password or throws IllegalArgumentException
     * if PasswordProtection is destroyed
     */
    public void testGetPassword() throws DestroyFailedException {
        char [] pass = {'a', 'b', 'c'};
        KeyStore.PasswordProtection ksPWP = new KeyStore.PasswordProtection(pass);
        char [] rPass = ksPWP.getPassword();
        assertFalse("PasswordProtection Should not be descroyed", ksPWP.isDestroyed());        
        assertEquals("Incorrect password length", pass.length, rPass.length);
        for (int i = 0; i < pass.length; i++) {
            assertEquals("Incorrect password (item: ".concat(Integer.toString(i))
                    .concat(")"), pass[i], rPass[i]);
        }
        ksPWP.destroy();
        assertTrue("PasswordProtection must be descroyed", ksPWP.isDestroyed());
        try {
            ksPWP.getPassword();
            fail("IllegalStateException must be thrown because PasswordProtection is destroyed");
        } catch (IllegalStateException e) {
        }
    }
}
