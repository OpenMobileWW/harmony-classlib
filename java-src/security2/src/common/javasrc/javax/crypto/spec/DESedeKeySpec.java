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

import java.security.InvalidKeyException;
import java.security.spec.KeySpec;

/**
 * @com.intel.drl.spec_ref
 */
public class DESedeKeySpec implements KeySpec {

    /**
     * @com.intel.drl.spec_ref
     */
    public static final int DES_EDE_KEY_LEN = 24;

    private final byte[] key;

    private static final NullPointerException NULL_KEY_EXC =
        new NullPointerException("Specified key material is null.");
    private static final InvalidKeyException INVALID_KEY_EXC =
        new InvalidKeyException("The key material is shorter than 24 bytes.");

    /**
     * @com.intel.drl.spec_ref
     */
    public DESedeKeySpec(byte[] key)
                throws InvalidKeyException {
        if (key == null) {
            throw NULL_KEY_EXC;
        }
        if (key.length < DES_EDE_KEY_LEN) {
            throw INVALID_KEY_EXC;
            //throw new InvalidKeyException(
            //        "The key material is shorter than 24 bytes.");
        }
        this.key = new byte[DES_EDE_KEY_LEN];
        System.arraycopy(key, 0, this.key, 0, DES_EDE_KEY_LEN);
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public DESedeKeySpec(byte[] key, int offset)
                throws InvalidKeyException {
        if (key == null) {
            throw NULL_KEY_EXC;
        }
        if (key.length - offset < DES_EDE_KEY_LEN) {
            throw INVALID_KEY_EXC;
            //throw new InvalidKeyException(
            //        "The key material is shorter than 24 bytes.");
        }
        this.key = new byte[DES_EDE_KEY_LEN];
        System.arraycopy(key, offset, this.key, 0, DES_EDE_KEY_LEN);
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public byte[] getKey() {
        byte[] result = new byte [DES_EDE_KEY_LEN];
        System.arraycopy(this.key, 0, result, 0, DES_EDE_KEY_LEN);
        return result;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public static boolean isParityAdjusted(byte[] key, int offset)
                throws InvalidKeyException {
        if (key.length - offset < DES_EDE_KEY_LEN) {
            throw INVALID_KEY_EXC;
            //throw new InvalidKeyException(
            //        "The key material is shorter than 24 bytes.");
        }
        for (int i=offset; i<DES_EDE_KEY_LEN+offset; i++) {
            int b = key[i];
            if ((((b & 1) + ((b & 2) >> 1) + ((b & 4) >> 2)
                + ((b & 8) >> 3) + ((b & 16) >> 4) + ((b & 32) >> 5)
                + ((b & 64) >> 6)) & 1) == ((b & 128) >> 7)) {
                return false;
            }
        }
        return true;
    }
}

