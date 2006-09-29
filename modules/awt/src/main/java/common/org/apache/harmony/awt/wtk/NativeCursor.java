/*
 *  Copyright 2005 - 2006 The Apache Software Foundation or its licensors, as applicable.
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
 * @author Dmitry A. Durnev
 * @version $Revision$
 */
package org.apache.harmony.awt.wtk;

/**
 * The interface provides access to platform dependent functionality
 * for the class java.awt.Cursor.
 */
public interface NativeCursor {
    /**
     * Sets the current cursor shape
     * to this cursor when a pointer is inside
     * @param winID - window(currently used only on X11)
     */
    void setCursor(long winID);
    /**
     * Destroys the native resource associated with
     * this cursor
     */
    void destroyCursor();

    /**
     * @return Native handle associated with this cursor
     */
    long getId();

}
