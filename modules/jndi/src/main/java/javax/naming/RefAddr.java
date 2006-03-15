/* Copyright 2004 The Apache Software Foundation or its licensors, as applicable
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package javax.naming;

import java.io.Serializable;

/**
 * This is an abstract class describing the address of an object which is 
 * outside of a naming system.
 * It contains an address type and the address itself is dealt with by its
 * subclasses, for example, <code>BinaryRefAddr</code> and <code>StringRefAddr
 * </code>.
 *
 * @see BinaryRefAddr
 * @see StringRefAddr
 */
public abstract class RefAddr implements Serializable {

    /*
     * -------------------------------------------------------------------
     * Constants
     * -------------------------------------------------------------------
     */

    /*
     * This constant is used during deserialization to check the J2SE version
     * which created the serialized object.
     */
    static final long serialVersionUID = -1468165120479154358L; //J2SE 1.4.2

    /*
     * -------------------------------------------------------------------
     * Instance variables
     * -------------------------------------------------------------------
     */

    /**
     * The type of the address.
     * 
     * @serial
     */
    protected String addrType;

    /*
     * -------------------------------------------------------------------
     * Constructors
     * -------------------------------------------------------------------
     */

    /**
     * Constructs a <code>RefAddr</code> instance using the supplied address
     * type.
     * 
     * @param type  the address type which may be null
     */
    protected RefAddr(String type) {
        this.addrType = type;
    }

    /*
     * -------------------------------------------------------------------
     * Methods
     * -------------------------------------------------------------------
     */

    /**
     * Gets the type of this address.
     * 
     * @return      the type of this address which cannot be null
     */
    public String getType() {
        return addrType;
    }

    /**
     * Gets the address itself which may be null.
     * Each subclass of <code>RefAddr</code> describes the format of the 
     * returned address.
     * 
     * @return      the address itself
     */
    public abstract Object getContent();

    /*
     * -------------------------------------------------------------------
     * Methods override parent class Object
     * -------------------------------------------------------------------
     */

    /**
     * Returns true if this address is equal to the supplied object 
     * <code>o</code>.
     * They are considered equal if the address types are equal and the address
     * contents are equivalent.
     * *
     * @param o     the object to compare with
     * @return      true if this address is equal to <code>o</code>,
     *              otherwise false
     */
    public boolean equals(Object o) {
        if (o instanceof RefAddr) {
            RefAddr a = (RefAddr) o;
            return this.addrType.equals(a.addrType)
                && (null == this.getContent()
                    ? null == a.getContent()
                    : this.getContent().equals(a.getContent()));
        }
        return false;
    }

    /**
     * Returns the hashcode for this address.
     * The result is the sum of the hashcode of its address type and address.
     * 
     * @return      the hashcode of this address
     */
    public int hashCode() {
        return this.addrType.hashCode()
            + (null == this.getContent() ? 0 : this.getContent().hashCode());
    }

    /**
     * Returns the string representation of this address. 
     * This contains the string representations of the address type and the 
     * address.
     *  
     * @return      the string representation of this address
     */
    public String toString() {
        return "The type of the address is: " //$NON-NLS-1$
            + this.addrType
            + "\nThe content of the address is: " //$NON-NLS-1$
            + (null == this.getContent() ? "null" : this.getContent().toString()) //$NON-NLS-1$
            + "\n"; //$NON-NLS-1$

    }

}


