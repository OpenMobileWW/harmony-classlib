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
* @author Alexey V. Varlamov
* @version $Revision$
*/

package com.openintel.drl.security;

import java.security.CodeSource;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import com.openintel.fortress.drl.security.PolicyUtils;

/**
 * This class represents an elementary block of a security policy. It associates
 * a CodeSource of an executable code, Principals allowed to execute the code,
 * and a set of granted Permissions.
 * 
 * @see com.openintel.drl.security.DefaultPolicy
 */
public class PolicyEntry {

    // Store CodeSource
    private final CodeSource cs;

    // Array of principals 
    private final Principal[] principals;

    // Permossions collection
    private final Collection permissions;

    /**
     * Constructor with initialization parameters. Passed collections are not
     * referenced directly, but copied.
     */
    public PolicyEntry(CodeSource cs, Collection prs, Collection permissions) {
        this.cs = cs;
        this.principals = (prs == null || prs.size() == 0) ? null : (Principal[])prs
            .toArray(new Principal[prs.size()]);
        this.permissions = (permissions == null || permissions.size() == 0)
            ? null : Collections.unmodifiableCollection(permissions);
    }

    /**
     * Checks if passed CodeSource matches this PolicyEntry. Null CodeSource of
     * PolicyEntry implies any CodeSource; non-null CodeSource forwards to its
     * imply() method.
     */
    public boolean impliesCodeSource(CodeSource codeSource) {
        return (cs == null) ? true : cs.implies(codeSource);
    }

    /**
     * Checks if specified Principals match this PolicyEntry. Null or empty set
     * of Principals of PolicyEntry implies any Principals; otherwise specified
     * array must contain all Principals of this PolicyEntry.
     */
    public boolean impliesPrincipals(Principal[] prs) {
        return PolicyUtils.matchSubset(principals, prs);
    }

    /**
     * Returns unmodifiable collection of permissions defined by this
     * PolicyEntry, may be <code>null</code>.
     */
    public Collection getPermissions() {
        return permissions;
    }

    /**
     * Returns true if this PolicyEntry defines no Permissions, false otherwise.
     */
    public boolean isVoid() {
        return permissions == null || permissions.size() == 0;
    }
}
