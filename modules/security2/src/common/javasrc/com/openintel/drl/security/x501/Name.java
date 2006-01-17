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
* @author Alexander V. Esin
* @version $Revision$
*/

package com.openintel.drl.security.x501;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.x500.X500Principal;

import org.apache.harmony.security.asn1.ASN1Exception;
import org.apache.harmony.security.asn1.ASN1SequenceOf;
import org.apache.harmony.security.asn1.ASN1SetOf;
import org.apache.harmony.security.asn1.BerInputStream;
import org.apache.harmony.security.asn1.DerInputStream;

import com.openintel.drl.security.x509.DNParser;

/**
 * X.501 Name
 */
public class Name {

    //ASN.1 DER encoding of Name
    private volatile byte[] encoded;

    // RFC1779 string
    private String rfc1779String;

    // RFC2253 string
    private String rfc2253String;

    //CANONICAL string
    private String canonicalString;

    //Collection of RDNs
    private List rdn;

    /**
     * Creates new <code>Name</code> instance from its DER encoding
     * 
     * @param encoding - ASN.1 DER encoding
     * @throws IOException - if encoding is wrong
     */
    public Name(byte[] encoding) throws IOException {

        DerInputStream in = new DerInputStream(encoding);

        if (in.getEndOffset() != encoding.length) {
            throw new IllegalArgumentException("Wrong content length");
        }

        ASN1.decode(in);

        this.rdn = (List) in.content;
    }

    /**
     * Creates new <code>Name</code> instance from its DER encoding obtained
     * from <code>InputStream</code>
     * 
     * @param is
     *            <code>InputStream</code> containing DER encoding
     * @throws IOException
     *             if encoding is wrong
     */
    public Name(InputStream is) throws IOException {

        try {
            this.rdn = (List) ASN1.getValues(is);
        } catch (ASN1Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Creates new <code>Name</code> instance
     * 
     * @param name - Name as String
     * @throws IOException - if string is wrong
     */
    public Name(String name) throws IOException {
        rdn = new DNParser(name).parse();
    }

    // Creates Name instance
    private Name(List rdn) {
        this.rdn = rdn;
    }

    /**
     * Returns Relative Distinguished Name as <code>String</code> according
     * the format requested
     * 
     * @param format
     *            Name format requested
     * @return Relative Distinguished Name as <code>String</code> according
     *         the format requested
     */
    public String getName(String format) {

        //
        // check X500Principal constants first
        //
        if (format == X500Principal.RFC1779) {

            if (rfc1779String == null) {
                rfc1779String = getName0(format);
            }
            return rfc1779String;

        } else if (format == X500Principal.RFC2253) {

            if (rfc2253String == null) {
                rfc2253String = getName0(format);
            }
            return rfc2253String;

        } else if (format == X500Principal.CANONICAL) {

            if (canonicalString == null) {
                canonicalString = getName0(format);
            }
            return canonicalString;

        }
        //
        // compare ignore case
        //
        else if (X500Principal.RFC1779.equalsIgnoreCase(format)) {

            if (rfc1779String == null) {
                rfc1779String = getName0(X500Principal.RFC1779);
            }
            return rfc1779String;

        } else if (X500Principal.RFC2253.equalsIgnoreCase(format)) {

            if (rfc2253String == null) {
                rfc2253String = getName0(X500Principal.RFC2253);
            }
            return rfc2253String;

        } else if (X500Principal.CANONICAL.equalsIgnoreCase(format)) {

            if (canonicalString == null) {
                canonicalString = getName0(X500Principal.CANONICAL);
            }
            return canonicalString;

        } else {
            throw new IllegalArgumentException("Illegal format: " + format);
        }
    }

    /**
     * Returns Relative Distinguished Name as <code>String</code> according
     * the format requested, format is int value
     * 
     * @param format
     *            Name format requested
     * @return Relative Distinguished Name as <code>String</code> according
     *         the format requested
     */
    private String getName0(String format) {
        
        StringBuffer name = new StringBuffer();

        // starting with the last element and moving to the first.
        for (int i = rdn.size() - 1; i >= 0; i--) {
            List atavList = (List) rdn.get(i);

            if (X500Principal.CANONICAL == format) {
                List sortedList = new LinkedList(atavList);
                Collections.sort(sortedList,
                        new AttributeTypeAndValueComparator());
                atavList = sortedList;
            }

            // Relative Distinguished Name to string
            Iterator it = atavList.iterator();
            while (it.hasNext()) {
                AttributeTypeAndValue _ava = (AttributeTypeAndValue) it.next();
                _ava.appendName(format, name);
                if (it.hasNext()) {
                    // multi-valued RDN
                    if (X500Principal.RFC1779 == format) {
                        name.append(" + ");
                    } else {
                        name.append('+');
                    }
                }
            }

            if (i != 0) {
                name.append(',');
                if (format == X500Principal.RFC1779) {
                    name.append(' ');
                }
            }
        }

        String sName = name.toString();
        if (format == X500Principal.CANONICAL) {
            sName = sName.toLowerCase(Locale.US);
        }
        return sName;
    }

    /**
     * Gets encoded form of DN
     * 
     * @return encoding of DN
     */
    public byte[] getEncoded() {
        getInternalEncoding();
        byte[] enc = new byte[encoded.length];
        System.arraycopy(encoded, 0, enc, 0, encoded.length);
        return enc;
    }

    // @return encoding, no copying is performed
    public byte[] getInternalEncoding() {
        if (encoded == null) {
            encoded = ASN1.encode(this);
        }
        return encoded;
    }

    /**
     * According to RFC 3280 (http://www.ietf.org/rfc/rfc3280.txt) 
     * X.501 Name structure is defined as follows:
     * 
     * Name ::= CHOICE {
     *     RDNSequence }
     *  
     * RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
     *  
     * RelativeDistinguishedName ::=
     *     SET OF AttributeTypeAndValue
     * 
     */

    public static final ASN1SetOf ASN1_RDN = new ASN1SetOf(
            AttributeTypeAndValue.ASN1);

    public static final ASN1SequenceOf ASN1 = new ASN1SequenceOf(ASN1_RDN) {

        public Object getDecodedObject(BerInputStream in) {
            return new Name((List) in.content);
        }

        public Collection getValues(Object object) {
            return ((Name) object).rdn; //FIXME what about get method?
        }
    };
}