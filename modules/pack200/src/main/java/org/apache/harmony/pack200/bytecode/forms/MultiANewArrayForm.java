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
package org.apache.harmony.pack200.bytecode.forms;

import org.apache.harmony.pack200.bytecode.ByteCode;
import org.apache.harmony.pack200.bytecode.OperandManager;

/**
 * This class implements the byte code form for the
 * multianewarray instruction. It has a class reference
 * and a byte operand. Like other NewClassRefForms, it
 * keeps track of the last object created so other New
 * forms can make use of this information.
 */
public class MultiANewArrayForm extends NewClassRefForm {

    public MultiANewArrayForm(int opcode, String name,
            int[] rewrite) {
        super(opcode, name, rewrite);
        // TODO Auto-generated constructor stub
    }

    public int getOperandType() {
        return TYPE_MULTIANEWARRAY;
    }
    
    public boolean hasMultianewarrayOperand() {
        return true;
    }

    public boolean hasClassRefOperand() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.apache.harmony.pack200.bytecode.forms.ByteCodeForm#setByteCodeOperands(org.apache.harmony.pack200.bytecode.ByteCode, org.apache.harmony.pack200.bytecode.OperandTable, org.apache.harmony.pack200.SegmentConstantPool)
     */
    public void setByteCodeOperands(ByteCode byteCode,
            OperandManager operandManager, int codeLength) {
        // multianewarray has a class ref and a dimension.
        // The superclass handles the class ref.
        super.setByteCodeOperands(byteCode, operandManager, codeLength);
        
        // We have to handle the dimension.
        int dimension = operandManager.nextByte();
        byteCode.setOperandByte(dimension, 2);
    }
}
