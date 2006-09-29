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
 * @author Denis M. Kishenko
 * @version $Revision$
 */
package java.awt.geom;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import java.awt.Tools;

public abstract class ShapeTest extends GeomTestCase {

    final static String SHAPE_PATH = "java/awt/geom/shapes/";
    final static String OUTPUT_PATH = "java/awt/geom/output/";
    final static double SHAPE_DELTA = 0.01;
    final static int RECT_WIDTH = 46;
    final static int RECT_HEIGHT = 34;
    final static int POINT_MIN_COUNT = 10;
    final static int ERROR_MAX_COUNT = 5;
    final static int CHECK_STEP = 8;

    final static Color colorInside = Color.lightGray;
    final static Color colorOutside = Color.white;
    final static Color colorShape = Color.gray;
    final static Color colorGrid = new Color(0xA0, 0xA0, 0xA0);

    final static int cInside = colorInside.getRGB();
    final static int cOutside = colorOutside.getRGB();
    final static int cShape = colorShape.getRGB();
    final static int cGrid = colorGrid.getRGB();

    final static Color errorColor = Color.red;
    final static Color successColor = Color.green;

    String shapePath, outputPath;
    String tests[];
    protected FilenameFilter filterImage, filterShape, filterBounds;

    int[] prevRect;
    int[] count;

    String[] testNames = new String[] {
            "Unknown",
            "cr",
            "cr2",
            "ir",
            "ir2",
            "cp",
            "cp2"
    };

    class TextTokenizer {

        StreamTokenizer t;
        String buf;

        TextTokenizer(String text) {
            t = new StreamTokenizer(new StringReader(text));
            buf = text;
        }

        double getDouble() throws IOException {
            while (t.nextToken() != StreamTokenizer.TT_EOF) {
                if (t.ttype == StreamTokenizer.TT_NUMBER) {
                    return t.nval;
                }
            }
            throw new IOException("Double not found");
        }

        String getString() throws IOException {
            while (t.nextToken() != StreamTokenizer.TT_EOF) {
                if (t.ttype == StreamTokenizer.TT_WORD) {
                    return t.sval;
                }
            }
            throw new IOException("String not found");
        }

        boolean findString(String substr) {
            int pos = buf.indexOf(substr);
            if (pos != -1) {
                t = new StreamTokenizer(new StringReader(buf.substring(pos + substr.length())));
            }
            return pos != -1;
        }

    }

    public ShapeTest(String name) {
        super(name);
        String basePath = System.getProperty("TEST_CLASSES_DIR");
        if (basePath == null) {
            basePath = ".";
        } else {
            if (!basePath.endsWith(File.separator)) {
                basePath += File.separator;
            }
        }

        shapePath = basePath + SHAPE_PATH;
        outputPath = basePath + OUTPUT_PATH;

        new File(outputPath).mkdirs();
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    FilenameFilter createFilter(final String include, final String exclude) {
        return new FilenameFilter() {
              public boolean accept(File dir, String name) {
                return
                    (include == null || Pattern.matches(include, name)) &&
                    (exclude == null || !Pattern.matches(exclude, name));
              }
        };
    }

    BufferedImage createImage(Shape shape, AffineTransform t) {
        return Tools.Shape.createImage(shape, t, colorShape, colorInside);
    }

    String getShapeDesc(RectangularShape shape) {
        String desc =
            doubleToStr(shape.getX()) + "," +
            doubleToStr(shape.getY()) + "," +
            doubleToStr(shape.getWidth()) + "," +
            doubleToStr(shape.getHeight());

        if (shape instanceof Arc2D) {
            Arc2D arc = (Arc2D)shape;
            desc = "arc(" + desc;
            switch(arc.getArcType()) {
            case Arc2D.CHORD:
                desc += ",CORD)";
            case Arc2D.OPEN:
                desc += ",OPEN)";
            case Arc2D.PIE:
                desc += ",PIE)";
            }
        } else
        if (shape instanceof Rectangle2D) {
            desc = "rect(" + desc + ")";
        } else
        if (shape instanceof Ellipse2D) {
            desc = "ellipse(" + desc + ")";
        } else
        if (shape instanceof RoundRectangle2D) {
            RoundRectangle2D rr = (RoundRectangle2D)shape;
            desc = "rrect(" + desc + "," +
                doubleToStr(rr.getArcWidth()) + "," +
                doubleToStr(rr.getArcHeight())  + ")";
        }
        return desc;
    }

    String [] getTestList(String path, FilenameFilter filter) {
          File folder = new File(path);
          String list[] = folder.list(filter);
          if (list != null) {
              for(int i = 0; i < list.length; i++) {
                list[i] = folder.getAbsolutePath() + File.separator + list[i];
              }
          }
          assertTrue("Shapes weren't found in " + folder.getAbsolutePath(), list != null && list.length > 0);
//        System.out.println("Found " + list.length + " shape(s) " + shapePath);
          return list;
    }

    double StrToDouble(String value) {
        return new Double(value).doubleValue();
    }

    int StrToArcType(String value) {
        if (value.equals("PIE")) {
            return Arc2D.PIE;
        }
        if (value.equals("CHORD")) {
            return Arc2D.CHORD;
        }
        if (value.equals("OPEN")) {
            return Arc2D.OPEN;
        }
        fail("Unknown Arc2D type " + value);
        return -1;
    }

    double getFlatness(String fileName) {
        try {
            TextTokenizer t = new TextTokenizer(Tools.File.extractFileName(fileName));
            if (t.findString("flat(")) {
                return t.getDouble();
            }
        } catch(IOException e) {
            fail("Cann't read flatness " + fileName);
        }
        return -1.0;
    }

    Shape createShape(String fileName) {
        Shape shape = null;
        try {
            String fname = Tools.File.extractFileName(fileName);
            TextTokenizer t = new TextTokenizer(fname);

            int type = 0;
            if (t.findString("rect(")) {
                shape = new Rectangle2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            } else
            if (t.findString("ellipse(")) {
                shape = new Ellipse2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            } else
            if (t.findString("round(")) {
                shape = new RoundRectangle2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            } else
            if (t.findString("arc(")) {
                shape = new Arc2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        StrToArcType(t.getString()));
            } else
            if (t.findString("line(")) {
                shape = new Line2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            } else {
                // GeneralPath
                shape = Tools.Shape.load(Tools.File.changeExt(fileName, ".shape"));
            }

        } catch (IOException e) {
            fail("Cann't read shape " + fileName);
        }
        return shape;
    }

    AffineTransform createTransform(String fileName) {
        AffineTransform at = null;
        try {
            String fname = Tools.File.extractFileName(fileName);
            TextTokenizer t = new TextTokenizer(fname);


            if (t.findString("affine(")) {
/*
                String ttype = t.getString();
                if (ttype.equals("M")) {
                    at.setTransform(AffineTransform.getTranslateInstance(t.getDouble(), t.getDouble()));
                } else
                if (ttype.equals("R")) {
                    at.setTransform(AffineTransform.getRotateInstance(t.getDouble()));
                } else
                if (ttype.equals("SC")) {
                    at.setTransform(AffineTransform.getScaleInstance(t.getDouble(), t.getDouble()));
                } else
                if (ttype.equals("SH")) {
                    at.setTransform(AffineTransform.getShearInstance(t.getDouble(), t.getDouble()));
                } else
                if (ttype.equals("F")) {
                    at.setTransform(AffineTransform.getShearInstance(t.getDouble(), t.getDouble()));
                }
*/
                at = new AffineTransform(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            }

        } catch (IOException e) {
            fail("Cann't read transform " + fileName);
        }
        return at;
    }

    Rectangle2D createBounds2D(String fileName) {
        try {
            TextTokenizer t = new TextTokenizer(Tools.File.extractFileName(fileName));
            if (t.findString("bounds(")) {
                return new Rectangle2D.Double(
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble(),
                        t.getDouble());
            }
        } catch(IOException e) {
            fail("Cann't read bounds " + fileName);
        }
        return null;
    }

    int[][] createImageBuffer(BufferedImage img) {
        int buf[][] = new int[img.getWidth()][img.getHeight()];
        for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++) {
                buf[x][y] = img.getRGB(x, y);
            }
        return buf;
    }

    int getColor(int color) {
        if (color == cInside || color == cOutside || color == cShape) {
            return color;
        }
        int xored = (color ^ cGrid ^ 0xFF808080);
        if (xored == cInside || xored == cOutside || xored == cShape) {
            return xored;
        }
        return color;
    }

    void countRect(int[][] buf, int[] r1, int[] r2, int[] count) {
        if (r1 != null && (r1[0] > r2[2] || r1[2] < r2[0] || r1[1] > r2[3] || r1[3] < r2[1])) {
            count[0] = count[1] = count[2] = 0;
            countRect(buf, null, r2, count);
            return;
        }

        int x1, y1, x2, y2;
        if (r1 == null) {
            x1 = r2[0];
            y1 = r2[1];
            x2 = r2[2];
            y2 = r2[3];
        } else {
            x1 = Math.min(r1[0], r2[0]);
            y1 = Math.min(r1[1], r2[1]);
            x2 = Math.max(r1[2], r2[2]);
            y2 = Math.max(r1[3], r2[3]);
        }
        for(int x = x1; x <= x2; x++)
            for(int y = y1; y <= y2; y++) {
                boolean inside1 = r1 != null && r1[0] <= x && x <= r1[2] && r1[1] <= y && y <= r1[3];
                boolean inside2 = r2 != null && r2[0] <= x && x <= r2[2] && r2[1] <= y && y <= r2[3];

                if (inside1 ^ inside2) {
                    int index = 3;
                    int color = getColor(buf[x][y]);
                    if (color == colorOutside.getRGB()) {
                        index = 0;
                    } else
                    if (color == colorInside.getRGB()) {
                        index = 1;
                    } else
                    if (color == colorShape.getRGB()) {
                        index = 2;
                    }
                    if (inside1) {
                        count[index]--;
                    } else {
                        count[index]++;
                    }
                }
            }
    }

    int getRectType(BufferedImage img, int buf[][], int rx, int ry, int rw, int rh, boolean usePrev) {

        if ((rx + ry) % 2 == 0) {
            return 0;
        }

        if (!usePrev) {
            prevRect = null;
            count = new int[3];
        }

        int[] newRect = new int[]{rx, ry, rx + rw, ry + rh};
        countRect(buf, prevRect, newRect, count);
        prevRect = newRect;

        if (count[0] > POINT_MIN_COUNT && count[1] == 0 && count[2] == 0) {
            return 1; // Outside
        }
        if (count[1] > POINT_MIN_COUNT && count[0] == 0 && count[2] == 0) {
            return 2; // Inside
        }
        if (count[0] > POINT_MIN_COUNT && count[1] > POINT_MIN_COUNT) {
            return 3; // Both
        }
        return 0; // Invalid rectangle
    }

    int getRectType_(BufferedImage img, int buf[][], int rx, int ry, int rw, int rh) {

        if ((rx + ry) % 2 == 0) {
            return 0;
        }

        int countOutside = 0;
        int countInside = 0;
        int countShape = 0;

        for(int x = rx; x <= rx + rw; x++) {
            for(int y = ry; y <= ry + rh; y++) {
                if (x == rx || x == rx + rw ||
                    y == ry || y == ry + rh ||
                    (x + y) % CHECK_STEP == 0 || (x - y) % CHECK_STEP == 0)
                {
                    if (img != null) {
//                      img.setRGB(x, y, Color.blue.getRGB());
                    }
                    int color = getColor(buf[x][y]);
                    if (color == colorOutside.getRGB()) {
                        countOutside++;
                    } else
                    if (color == colorInside.getRGB()) {
                        countInside++;
                    } else
                    if (color == colorShape.getRGB()) {
                        countShape++;
                    }
                }
            }
        }

        if (countInside == 0 && countOutside > POINT_MIN_COUNT && countShape == 0) {
            return 1; // Outside
        }
        if (countOutside == 0 && countInside > POINT_MIN_COUNT && countShape == 0) {
            return 2; // Inside
        }
        if (countInside > POINT_MIN_COUNT && countOutside > POINT_MIN_COUNT) {
            return 3; // Both
        }
        return 0; // Invalid rectangle
    }

    void checkRunner(String name, FilenameFilter filter, String methodName, Object[] params) {
        if (filter == null) {
            return; // skip test
        }
        tests = getTestList(shapePath, filter);
        int countErr = 0;
        try {
            Method method = this.getClass().getMethod(methodName, new Class[] {String.class, Object[].class});
            for(int i = 0; i < tests.length; i++) {
                Object res = method.invoke(this, new Object[] {tests[i], params});
                if (!((Boolean)res).booleanValue()) {
                    if (countErr == 0) {
                        System.out.println(name);
                    }
                    countErr++;
                    System.out.println("Failed " + tests[i]);
                }
            }
        } catch(Exception e) {
            fail(e.toString());
        }
        assertTrue("Failed " + countErr + " test(s)", countErr == 0);
    }

    public boolean checkRect1(String fileName, Object[] params) {
        boolean error = false;
        try {
            String methodName = (String)params[0];
            String prefix = (String)params[1];
            boolean[] expected = (boolean[])params[2];

            Method method = Shape.class.getMethod(methodName, new Class[]{double.class, double.class, double.class, double.class});
            Shape shape = createShape(fileName);
            BufferedImage img = Tools.BufferedImage.loadIcon(fileName);
            int buf[][] = createImageBuffer(img);
            Graphics g = img.getGraphics();
            g.setColor(errorColor);

            count = new int[]{0, 0, 0};
            prevRect = null;

            for(int x = 0; x < img.getWidth() - RECT_WIDTH; x++)
                for(int y = 0; y < img.getHeight() - RECT_HEIGHT; y++) {
                    int rectType = getRectType(null, buf, x, y, RECT_WIDTH, RECT_HEIGHT, true);
                    if (rectType == 0) {
                        // Invalid rectangle
                        continue;
                    }
                    Object res = method.invoke(shape, new Object[]{
                            new Double(x),
                            new Double(y),
                            new Double(RECT_WIDTH),
                            new Double(RECT_HEIGHT)});
                    if (expected[rectType] != ((Boolean)res).booleanValue()) {
                        g.drawRect(x, y, RECT_WIDTH, RECT_HEIGHT);
                        error = true;
                    }
                }

            int errCount = 0;
            Random rnd = new Random();
            for(int i = 0; i < 1000; i ++) {
                int rx = (int)(rnd.nextDouble() * (img.getWidth() - RECT_WIDTH));
                int ry = (int)(rnd.nextDouble() * (img.getHeight() - RECT_HEIGHT));
                int rw = (int)(rnd.nextDouble() * (img.getWidth() - rx - 1)) + 1;
                int rh = (int)(rnd.nextDouble() * (img.getHeight() - ry - 1)) + 1;

                int rectType = getRectType(img, buf, rx, ry, rw, rh, false);
                if (rectType == 0) {
                    // Invalid rectangle
                    continue;
                }

                Object res = method.invoke(shape, new Object[]{
                        new Double(rx),
                        new Double(ry),
                        new Double(rw),
                        new Double(rh)});

                if (expected[rectType] != ((Boolean)res).booleanValue()) {
                    g.drawRect(rx, ry, rw, rh);
                    error = true;
                    errCount++;
                }
                if (errCount > ERROR_MAX_COUNT) {
                    break;
                }
            }

            Tools.BufferedImage.saveIcon(img, outputPath + prefix + Tools.File.extractFileName(fileName));
        } catch(Exception e) {
            fail(e.toString());
        }
        return !error;
    }

    public boolean checkRect2(String fileName, Object[] params) {
        boolean error = false;
        try {
            String methodName = (String)params[0];
            String prefix = (String)params[1];

            Method method1 = Shape.class.getMethod(methodName, new Class[]{double.class, double.class, double.class, double.class});
            Method method2 = Shape.class.getMethod(methodName, new Class[]{Rectangle2D.class});
            Shape shape = createShape(fileName);
            BufferedImage img = Tools.BufferedImage.loadIcon(fileName);
            int buf[][] = createImageBuffer(img);
            Graphics g = img.getGraphics();
            g.setColor(errorColor);

            Random rnd = new Random();
            for(int i = 0; i < 100; i ++) {
                int rx = (int)(rnd.nextDouble() * (img.getWidth() - RECT_WIDTH));
                int ry = (int)(rnd.nextDouble() * (img.getHeight() - RECT_HEIGHT));
                int rw = (int)(rnd.nextDouble() * (img.getWidth() - rx - 1)) + 1;
                int rh = (int)(rnd.nextDouble() * (img.getHeight() - ry - 1)) + 1;

                Object res1 = method1.invoke(shape, new Object[]{
                        new Double(rx),
                        new Double(ry),
                        new Double(rw),
                        new Double(rh)});

                Object res2 = method2.invoke(shape, new Object[]{new Rectangle2D.Double(rx, ry, rw, rh)});

                if (!res1.equals(res2)) {
                    g.drawRect(rx, ry, rw, rh);
                    error = true;
                }
            }

            Tools.BufferedImage.saveIcon(img, outputPath + prefix + Tools.File.extractFileName(fileName));
        } catch(Exception e) {
            fail(e.toString());
        }
        return !error;
    }

    public boolean checkContainsPoint1(String fileName, Object[] params) {
        boolean error = false;
        Shape shape = createShape(fileName);
        BufferedImage img = Tools.BufferedImage.loadIcon(fileName);
        for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++) {
                int color = getColor(img.getRGB(x, y));
                boolean res = shape.contains(x, y);
                if ((color == colorInside.getRGB() && !res) ||
                    (color == colorOutside.getRGB() && res))
                {
                    img.setRGB(x, y, errorColor.getRGB());
                    error = true;
                }
            }
        Tools.BufferedImage.saveIcon(img, outputPath + "cp_" + Tools.File.extractFileName(fileName));
        return !error;
    }

    public boolean checkContainsPoint2(String fileName, Object[] params) {
        boolean error = false;
        Shape shape = createShape(fileName);
        BufferedImage img = Tools.BufferedImage.loadIcon(fileName);
        Random rnd = new Random();
        for(int i = 0; i < 100; i++) {
            int x = (int)(rnd.nextDouble() * img.getWidth());
            int y = (int)(rnd.nextDouble() * img.getHeight());
            if (shape.contains(x, y) != shape.contains(new Point2D.Double(x, y))) {
                img.setRGB(x, y, errorColor.getRGB());
                error = true;
            }
        }
        Tools.BufferedImage.saveIcon(img, outputPath + "cp2_" + Tools.File.extractFileName(fileName));
        return !error;
    }

    public boolean checkPathIterator(String fileName, Object[] params) {
        boolean error = false;
        double flatness = getFlatness(fileName);
        AffineTransform at = createTransform(fileName);
        Shape shape1 = createShape(fileName);
        Shape shape2 = Tools.Shape.load(fileName);
        String name = at == null ? "pi_" : "pia_";
        GeneralPath path = new GeneralPath();
        path.append(flatness < 0.0 ? shape1.getPathIterator(at) : shape1.getPathIterator(at, flatness), false);
        Tools.Shape.save(path, outputPath + name + Tools.File.extractFileName(fileName));
        return Tools.PathIterator.equals(
                path.getPathIterator(null),
                shape2.getPathIterator(null),
                SHAPE_DELTA);
    }

    public void testContainsPoint1() {
        checkRunner(
                "contains(double,double)",
                filterImage,
                "checkContainsPoint1",
                null);
    }

    public void testContainsPoint2() {
        checkRunner(
                "contains(Point2D)",
                filterImage,
                "checkContainsPoint2",
                null);
    }

    public void testContainsRect1() {
        checkRunner(
                "contains(double,double,double,double)",
                filterImage,
                "checkRect1",
                new Object[]{"contains", "cr_", new boolean[] {false, false, true, false}});
    }

    public void testContainsRect2() {
        checkRunner(
                "contains(Rectangle2D)",
                filterImage,
                "checkRect2",
                new Object[]{"contains", "cr2_"});
    }

    public void testIntersectsRect1() {
        checkRunner(
                "intersects(double,double,double,double)",
                filterImage,
                "checkRect1",
                new Object[]{"intersects", "ir_", new boolean[] {false, false, true, true}});
    }

    public void testIntersectsRect2() {
        checkRunner(
                "contains(Rectangle2D)",
                filterImage,
                "checkRect2",
                new Object[]{"intersects", "ir2_"});
    }

    public void testGetPathIterator() {
        checkRunner(
                "getPathIterator()",
                filterShape,
                "checkPathIterator",
                null);
    }


    /*
    public boolean checkBounds(String fileName) {
        boolean error = false;
        Shape shape = createShape(fileName);
        Rectangle2D expectBounds = createBounds2D(fileName);
        Rectangle2D actualBounds = bounds2D ? shape.getBounds2D() : shape.getBounds();
        BufferedImage img = Tools.BufferedImage.loadIcon(fileName);
        Graphics g = img.getGraphics();
        g.setColor(Color.red);
        ((Graphics2D)g).draw(actualBounds);
        String name = bounds2D ? "bounds2D_" : "bounds_";
        Tools.BufferedImage.saveIcon(img, outputPath + name + Tools.File.extractFileName(fileName));
        return !equalRects(expectBounds, actualBounds, SHAPE_DELTA);
    }


    public void testGetBounds2D() {
        System.out.println("testdsfsd");
        if (filterBounds == null) {
            return; // skip test
        }
        tests = getTestList(shapePath, filterBounds);
        System.out.println("test");
        int countErr = 0;
        for(int i = 0; i < tests.length; i++) {
            if (checkBounds(tests[i], true)) {
                countErr++;
                System.out.println("Failed " + tests[i]);
            }
        }
        assertTrue("Failed " + countErr + " test(s)", countErr == 0);
    }
*/
/*
    public void testGetBounds() {
        if (filterBounds == null) {
            return; // skip test
        }
        tests = getTestList(shapePath, filterBounds);
        int countErr = 0;
        for(int i = 0; i < tests.length; i++) {
            if (checkBounds(tests[i], false)) {
                countErr++;
                System.out.println("Failed " + tests[i]);
            }
        }
        assertTrue("Failed " + countErr + " test(s)", countErr == 0);
    }
*/
}

