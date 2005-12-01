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

package com.ibm.platform;


/**
 * Endianness
 * 
 */
public final class Endianness {
	/**
	 * Private mapping mode (equivalent to copy on write).
	 */
	public static final Endianness BIG_ENDIAN = new Endianness("BIG_ENDIAN"); //$NON-NLS-1$

	/**
	 * Read-only mapping mode.
	 */
	public static final Endianness LITTLE_ENDIAN = new Endianness(
			"LITTLE_ENDIAN"); //$NON-NLS-1$

	// The string used to display the mapping mode.
	private final String displayName;

	/*
	 * Private constructor prevents others creating new Endians.
	 */
	private Endianness(String displayName) {
		super();
		this.displayName = displayName;
	}

	/**
	 * Answers a string version of the endianness
	 * 
	 * @return the mode string.
	 */
	public String toString() {
		return displayName;
	}
}
