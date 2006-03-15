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

package java.sql;

/**
 * TODO Type description An interface for the custom mapping of an SQL User
 * Defined Type (UDT) to a Java Class. The Java Class object will be added to
 * the Connection's type map with the SQL Name of the UDT which it maps.
 * <p>
 * Usually within an implementation of SQLData, there is a corresponding field
 * for every attribute of an SQL type, or only one field if the type is SQL
 * DISTINCT. When the UDT is returned within a ResultSet, it is accessed with
 * the ResultSet.getObject method and is returned as an Object which is an
 * instance of the class defined by the SQLData mapping. The application can use
 * this object just like any other Java object and can store changes back into
 * the database using the PreparedStatement.setObject method which performs the
 * reverse mapping into the SQL UDT.
 * <p>
 * It is standard for an implementation for a custom mapping to be generated by
 * a tool. The tool usually requires the name of the SQL UDT, the name of the
 * class which it is going to be mapped to, and the field names to which the UDT
 * attributes will be mapped. The tool can then implement the SQLData readSQL
 * and writeSQL methods. readSQL reads attributes from an SQLInput object, and
 * writeSQL writes them. This is done via SQLInput and SQLOutput method calls
 * respectively
 * <p>
 * Ordinarily a programmer would not call SQLData methods directly. Similarly
 * SQLInput and SQLOutput methods are not usually called directly.
 * 
 */
public interface SQLData {

	/**
	 * Gets the SQL name of the User Defined Type (UDT) that this object
	 * represents. This method, usually invoked by the JDBC driver, retrieves
	 * the name of the UDT instance associated with this SQLData object.
	 * 
	 * @return a string with UDT type name for this object mapping, passed to
	 *         readSQL when the object was created
	 * @throws SQLException
	 *             if a database error occurs
	 */
	public String getSQLTypeName() throws SQLException;

	/**
	 * Reads data from the database into this object. This method follows these
	 * steps:
	 * <ul>
	 * <li>Utilise the passed input stream to read the attributes or entries of
	 * the SQL type</li>
	 * <li>This is carried out by reading each entry from the input stream,
	 * ordered as the are the SQL definition.</li>
	 * <li>Assign the data to the appropriate fields or elements. This is done
	 * by calling the relevant reader method for the type involved (eg.
	 * SQLInput.readString, SQLInputreadBigDecimal). If the type is distinct,
	 * then read its only data entry. For structured types, read every entry.</li>
	 * </ul>
	 * The supplied input stream is typically initialized by the calling JDBC
	 * driver with the type map before readSQL is called.
	 * 
	 * @param stream
	 *            the SQLInput stream from which the type map data is read for
	 *            the custom mapping
	 * @param typeName
	 *            the SQL Type name for the type which is being mapped
	 * @throws SQLException
	 *             if a database error occurs
	 */
	public void readSQL(SQLInput stream, String typeName) throws SQLException;

	/**
	 * Writes the object to a supplied SQLOuput data stream, writing it out as
	 * an SQL value to the data source.
	 * <p>
	 * This method follows the following steps:
	 * <ul>
	 * <li>Write each attribute of the SQL type to the output stream.</li>
	 * <li>Write each item by calling a method on the output stream, in the
	 * order they appear in the SQL definition of the type. Use the appropriate
	 * SQLOutput methods (eg. writeInt, writeString). Write a single data
	 * element for a Distinct type. For a Structured type, write a value for
	 * each attribute of the the SQL type.</li>
	 * </ul>
	 * 
	 * @param stream
	 *            the SQLOutput stream to use to write out the data for the
	 *            custom mapping
	 * @throws SQLException
	 *             if a database error occurs
	 */
	public void writeSQL(SQLOutput stream) throws SQLException;

} // end interface SQLData

