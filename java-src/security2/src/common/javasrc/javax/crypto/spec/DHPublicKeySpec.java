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

import java.math.BigInteger;
import java.security.spec.KeySpec;

/**
 * @com.intel.drl.spec_ref
 */
public class DHPublicKeySpec implements KeySpec {

    private final BigInteger y;
    private final BigInteger p;
    private final BigInteger g;

    /**
     * @com.intel.drl.spec_ref
     */
    public DHPublicKeySpec(BigInteger y, BigInteger p, BigInteger g) {
        this.y = y;
        this.p = p;
        this.g = g;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public BigInteger getY() {
        return y;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public BigInteger getP() {
        return p;
    }

    /**
     * @com.intel.drl.spec_ref
     */
    public BigInteger getG() {
        return g;
    }
}

