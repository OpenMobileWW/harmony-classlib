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

package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.util.Collection;

/**
 * @com.intel.drl.spec_ref
 * 
 */

public abstract class CertStoreSpi {
    /**
     * @com.intel.drl.spec_ref
     * 
     * Parameter 'params' is unusable but required by the spec
     */
    public CertStoreSpi(CertStoreParameters params)
            throws InvalidAlgorithmParameterException {
    }

    /**
     * @com.intel.drl.spec_ref
     * 
     * FIXME: 1.5 updated are needed Collection <? extends Certificate>
     */
    public abstract Collection engineGetCertificates(CertSelector selector)
            throws CertStoreException;

    /**
     * @com.intel.drl.spec_ref
     * 
     * FIXME: 1.5 updated are needed Collection <? extends CRL>
     */
    public abstract Collection engineGetCRLs(CRLSelector selector)
            throws CertStoreException;
}