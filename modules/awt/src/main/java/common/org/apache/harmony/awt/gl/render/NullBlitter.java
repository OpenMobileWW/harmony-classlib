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
 * @author Igor V. Stolyarov
 * @version $Revision$
 * Created on 07.12.2005
 *
 */
package org.apache.harmony.awt.gl.render;

import java.awt.Color;
import java.awt.Composite;
import java.awt.geom.AffineTransform;

import org.apache.harmony.awt.gl.MultiRectArea;
import org.apache.harmony.awt.gl.Surface;


public class NullBlitter implements Blitter {

    static Blitter inst = new NullBlitter();
    public static Blitter getInstance(){
        return inst;
    }

    public void blit(int srcX, int srcY, Surface srcSurf, int dstX, int dstY,
            Surface dstSurf, int width, int height, AffineTransform sysxform,
            AffineTransform xform, Composite comp, Color bgcolor,
            MultiRectArea clip) {
    }

    public void blit(int srcX, int srcY, Surface srcSurf, int dstX, int dstY,
            Surface dstSurf, int width, int height, AffineTransform sysxform,
            Composite comp, Color bgcolor, MultiRectArea clip) {
    }

    public void blit(int srcX, int srcY, Surface srcSurf, int dstX, int dstY,
            Surface dstSurf, int width, int height, Composite comp,
            Color bgcolor, MultiRectArea clip) {
    }

}
