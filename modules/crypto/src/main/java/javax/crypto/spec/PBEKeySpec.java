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
* @author Alexander Y. Kleymenov
* @version $Revision$
*/

package javax.crypto.spec;

import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * @com.intel.drl.spec_ref
 */
public class PBEKeySpec implements KeySpec {

    private char[] password;
    private final byte[] salt;
    private final int iterationCount;
    private final int keyLength;

    /**
     * @com.intel.drl.spec_ref
     */
    public PBEKeySpec(char[] password) {
        if (password == null) {
            this.password = new char[0];
        } else {
            this.password = new char[password.length];
            System.arraycopy(password, 0, this.password, 0, password.length);
        }
        salt = null;
        iterationCount = 0;
        keyLength = 0;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public PBEKeySpec(char[] password, byte[] salt, int iterationCount,
                      int keyLength) {
        if (salt == null) {
            throw new NullPointerException("Salt is null.");
        }
        if (salt.length == 0) {
            throw new IllegalArgumentException("salt is empty");
        }
        if (iterationCount <= 0) {
            throw new IllegalArgumentException(
                    "iterationCount is not positive.");
        }
        if (keyLength <= 0) {
            throw new IllegalArgumentException("keyLength is not positive.");
        }

        if (password == null) {
            this.password = new char[0];
        } else {
            this.password = new char[password.length];
            System.arraycopy(password, 0, this.password, 0, password.length);
        }
        this.salt = new byte[salt.length];
        System.arraycopy(salt, 0, this.salt, 0, salt.length);
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public PBEKeySpec(char[] password, byte[] salt, int iterationCount) {
        if (salt == null) {
            throw new NullPointerException("Salt is null.");
        }
        if (salt.length == 0) {
            throw new IllegalArgumentException("salt is empty");
        }
        if (iterationCount <= 0) {
            throw new IllegalArgumentException(
                    "iterationCount is not positive.");
        }

        if (password == null) {
            this.password = new char[0];
        } else {
            this.password = new char[password.length];
            System.arraycopy(password, 0, this.password, 0, password.length);
        }
        this.salt = new byte[salt.length];
        System.arraycopy(salt, 0, this.salt, 0, salt.length);
        this.iterationCount = iterationCount;
        this.keyLength = 0;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public final void clearPassword() {
        Arrays.fill(password, '?');
        password = null;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public final char[] getPassword() {
        if (password == null) {
            throw new IllegalStateException("The password has been cleared.");
        }
        char[] result = new char[password.length];
        System.arraycopy(password, 0, result, 0, password.length);
        return result;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public final byte[] getSalt() {
        if (salt == null) {
            return null;
        }
        byte[] result = new byte[salt.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        return result;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public final int getIterationCount() {
        return iterationCount;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public final int getKeyLength() {
        return keyLength;
    }
}

