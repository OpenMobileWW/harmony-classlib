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

package javax.security.sasl;

/**
 * @com.intel.drl.spec_ref
 *  
 */
public interface SaslServer {
    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public void dispose() throws SaslException;

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public byte[] evaluateResponse(byte[] response) throws SaslException;

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public String getAuthorizationID();

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public String getMechanismName();

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public Object getNegotiatedProperty(String propName);

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public boolean isComplete();

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public byte[] unwrap(byte[] incoming, int offset, int len)
            throws SaslException;

    /**
     * @com.intel.drl.spec_ref
     *  
     */
    public byte[] wrap(byte[] outgoing, int offset, int len)
            throws SaslException;
}