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
* @author Vladimir N. Molotkov
* @version $Revision$
*/

package com.openintel.drl.security.cert;

import java.security.cert.CRL;
import java.security.cert.Certificate;

/**
 * Stub class for <code>java.security.cert.CRL</code> tests
 * 
 */
public class MyCRL extends CRL {

    /**
     * Constructor
     * 
     * @param type
     */
    public MyCRL(String type) {
        super(type);
    }

    /**
     * @return <code>String</code> representation
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "MyCRL: [" + getType() + "]";
    }

    /**
     * @param cert <code>Certificate</code> to be checked
     * @return always <code>false</code>
     * @see java.security.cert.CRL#isRevoked(java.security.cert.Certificate)
     */
    public boolean isRevoked(Certificate cert) {
        return false;
    }

}
