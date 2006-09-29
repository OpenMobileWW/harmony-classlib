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
 * @author Anton Avtamonov
 * @version $Revision$
 */

package javax.swing.plaf.basic;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

public class BasicComboBoxEditor implements ComboBoxEditor, FocusListener {

    public static class UIResource extends BasicComboBoxEditor implements javax.swing.plaf.UIResource {
    }

    protected JTextField editor = new JTextField();

    public void addActionListener(final ActionListener l) {
        editor.addActionListener(l);
    }

    public void removeActionListener(final ActionListener l) {
        editor.removeActionListener(l);
    }

    public void focusGained(final FocusEvent e) {
    }

    public void focusLost(final FocusEvent e) {

    }

    public Component getEditorComponent() {
        return editor;
    }

    public Object getItem() {
        return editor.getText();
    }

    public void setItem(final Object item) {
        editor.setText(item != null ? item.toString() : null);
    }

    public void selectAll() {
        editor.requestFocus();
        editor.selectAll();
    }
}
