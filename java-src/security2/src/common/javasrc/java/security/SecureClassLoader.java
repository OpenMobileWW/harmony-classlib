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
* @author Alexander V. Astapchuk
* @version $Revision$
*/

package java.security;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * @com.intel.drl.spec_ref
 */

public class SecureClassLoader extends ClassLoader {

    // A cache of ProtectionDomains for a given CodeSource
    private HashMap pds = new HashMap();

    /**
     * @com.intel.drl.spec_ref 
     */
    protected SecureClassLoader() {
        super();
    }

    /**
     * @com.intel.drl.spec_ref 
     */
    protected SecureClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * @com.intel.drl.spec_ref 
     */
    protected PermissionCollection getPermissions(CodeSource codesource) {
        // Do nothing by default, ProtectionDomain will take care about
        // permissions in dynamic
        return new Permissions();
    }

    /**
     * @com.intel.drl.spec_ref 
     */
    protected final Class defineClass(String name, byte[] b, int off, int len,
            CodeSource cs) {
        return cs == null ? defineClass(name, b, off, len) : defineClass(name,
                b, off, len, getPD(cs));
    }

    /**
     * @com.intel.drl.spec_ref 
     */
    protected final Class defineClass(String name, ByteBuffer b, CodeSource cs) {
        //FIXME 1.5 - remove b.array(), call super.defineClass(,ByteBuffer,)
        // directly
        byte[] data = b.array();
        return cs == null ? defineClass(name, data, 0, data.length)
                : defineClass(name, data, 0, data.length, getPD(cs));
    }

    // Constructs and caches ProtectionDomain for the given CodeSource 
    // object.<br>
    // It calls {@link getPermssions()} to get a set of permissions.
    //
    // @param cs CodeSource object
    // @return ProtectionDomain for the passed CodeSource object
    private ProtectionDomain getPD(CodeSource cs) {
        if (cs == null) {
            return null;
        }
        // need to cache PDs, otherwise every class from a given CodeSource 
        // will have it's own ProtectionDomain, which does not look right.
        ProtectionDomain pd;
        synchronized (pds) {
            if ((pd = (ProtectionDomain) pds.get(cs)) != null) {
                return pd;
            }
            PermissionCollection perms = getPermissions(cs);
            pd = new ProtectionDomain(cs, perms, this, null);
            pds.put(cs, pd);
        }
        return pd;
    }
}
