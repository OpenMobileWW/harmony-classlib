/* 
 *  Licensed to the Apache Software Foundation (ASF) under one or more 
 *  contributor license agreements.  See the NOTICE file distributed with 
 *  this work for additional information regarding copyright ownership. 
 *  The ASF licenses this file to You under the Apache License, Version 2.0 
 *  (the "License"); you may not use this file except in compliance with 
 *  the License.  You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */

package org.apache.harmony.jndi.provider.ldap;

import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.harmony.jndi.provider.ldap.asn1.ASN1TestUtils;
import org.apache.harmony.jndi.provider.ldap.asn1.LdapASN1Constant;
import org.apache.harmony.jndi.provider.ldap.asn1.Utils;

public class LdapAttributeTest extends TestCase {
    public void test_encode_decode() throws NamingException {
        String id = "test";
        byte[] v1 = Utils.getBytes("value1");
        byte[] v2 = Utils.getBytes("value2");
        byte[] v3 = Utils.getBytes("value3");
        LdapAttribute attr = new LdapAttribute(id);
        attr.add(v1);
        attr.add(v2);
        attr.add(v3);
        ASN1TestUtils.checkEncode(attr, LdapASN1Constant.Attribute);
        
        Object[] encoded = new Object[2];
        attr.encodeValues(encoded);
        LdapAttribute decoded = new LdapAttribute("");
        decoded.decodeValues(encoded);
        
        assertEquals(attr.getID(), decoded.getID());

    }
}