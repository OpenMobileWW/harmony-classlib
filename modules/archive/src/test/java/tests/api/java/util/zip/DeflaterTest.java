/* Copyright 1998, 2005 The Apache Software Foundation or its licensors, as applicable
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
package tests.api.java.util.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import tests.support.resource.Support_Resources;

public class DeflaterTest extends junit.framework.TestCase {

	class MyDeflater extends java.util.zip.Deflater {
		MyDeflater() {
			super();
		}

		MyDeflater(int lvl) {
			super(lvl);
		}

		MyDeflater(int lvl, boolean noHeader) {
			super(lvl, noHeader);
		}

		void myFinalize() {
			finalize();
		}

		int getDefCompression() {
			return DEFAULT_COMPRESSION;
		}

		int getDefStrategy() {
			return DEFAULT_STRATEGY;
		}

		int getHuffman() {
			return HUFFMAN_ONLY;
		}

		int getFiltered() {
			return FILTERED;
		}
	}

	/**
	 * @tests java.util.zip.Deflater#deflate(byte[])
	 */
	public void test_deflate$B() {
		// test method of java.util.zip.deflater.deflate(byte)
		byte outPutBuf[] = new byte[50];
		byte byteArray[] = { 1, 3, 4, 7, 8 };
		byte outPutInf[] = new byte[50];
		int x = 0;

		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			x += defl.deflate(outPutBuf);
		assertEquals("Deflater at end of stream, should return 0", 0, defl
				.deflate(outPutBuf));
		int totalOut = defl.getTotalOut();
		int totalIn = defl.getTotalIn();
		assertTrue(
				"The total number of bytes from deflate did not equal getTotalOut",
				x == totalOut);
		assertTrue(
				"The number of input bytes from the array did not correspond with getTotalIn: Was "
						+ totalIn + " Should Be " + byteArray.length,
				totalIn == byteArray.length);
		defl.end();

		Inflater infl = new Inflater();
		try {
			infl.setInput(outPutBuf);
			while (!infl.finished())
				infl.inflate(outPutInf);
		} catch (DataFormatException e) {
			fail("Invalid input to be decompressed");
		}
		assertTrue(
				"Inflates getTotalOut() did not correspond with deflates getTotalIn()",
				infl.getTotalOut() == totalIn);
		assertTrue(
				"Inflates getTotalIn() did not correspond with deflates getTotalOut()",
				infl.getTotalIn() == totalOut);
		for (int i = 0; i < byteArray.length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i] == outPutInf[i]);
		assertEquals("Final decompressed data contained more bytes than original",
				0, outPutInf[byteArray.length]);
		infl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#deflate(byte[], int, int)
	 */
	public void test_deflate$BII() {
		// test method of java.util.zip.deflater.deflate(byte,int,int)
		byte outPutBuf[] = new byte[50];
		byte byteArray[] = { 5, 2, 3, 7, 8 };
		byte outPutInf[] = new byte[50];
		int offSet = 1;
		int length = outPutBuf.length - 1;
		int x = 0;

		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			x += defl.deflate(outPutBuf, offSet, length);
		assertEquals("Deflater at end of stream, should return 0", 0, defl.deflate(
				outPutBuf, offSet, length));
		int totalOut = defl.getTotalOut();
		int totalIn = defl.getTotalIn();
		assertTrue(
				"The total number of bytes from deflate did not equal getTotalOut",
				totalOut == x);
		assertTrue(
				"The number of input bytes from the array did not correspond with getTotalIn - side effect of deflate()",
				totalIn == byteArray.length);
		defl.end();

		Inflater infl = new Inflater();
		try {
			infl.setInput(outPutBuf, offSet, length);
			while (!infl.finished())
				infl.inflate(outPutInf);
		} catch (DataFormatException e) {
			fail("Invalid input to be decompressed");
		}
		assertTrue(
				"inflates getTotalOut() did not correspond with deflates getTotalIn()",
				infl.getTotalOut() == totalIn);
		assertTrue(
				"inflates getTotalIn() did not correspond with deflates getTotalOut()",
				infl.getTotalIn() == totalOut);
		for (int i = 0; i < byteArray.length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i] == outPutInf[i]);
		assertEquals("Final decompressed data contained more bytes than original",
				0, outPutInf[byteArray.length]);
		infl.end();

		// Set of tests testing the boundaries of the offSet/length
		defl = new Deflater();
		outPutBuf = new byte[100];
		defl.setInput(byteArray);
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				offSet = outPutBuf.length + 1;
				length = outPutBuf.length;
			} else {
				offSet = 0;
				length = outPutBuf.length + 1;
			}
			try {
				defl.deflate(outPutBuf, offSet, length);
				fail("Test " + i
						+ ": ArrayIndexOutOfBoundsException not thrown");
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		defl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#end()
	 */
	public void test_end() {
		// test method java.util.zip.deflater.end();

		byte byteArray[] = { 5, 2, 3, 7, 8 };
		byte outPutBuf[] = new byte[100];

		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		defl.end();
		helper_end_test(defl, "end");
	}

	/**
	 * @tests java.util.zip.Deflater#finalize()
	 */
	public void test_finalize() {
		// test method java.util.zip.deflater.finalize()
		MyDeflater mdefl = new MyDeflater();
		mdefl.myFinalize();
		System.gc();
		helper_end_test(mdefl, "finalize");
	}

	/**
	 * @tests java.util.zip.Deflater#finish()
	 */
	public void test_finish() {
		// test method java.util.zip.deflater.finish()
		// This test already here, its the same as test_deflate()
		byte byteArray[] = { 5, 2, 3, 7, 8 };
		byte outPutBuf[] = new byte[100];
		byte outPutInf[] = new byte[100];
		int x = 0;
		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();

		// needsInput should never return true after finish() is called
		if (System.getProperty("java.vendor").startsWith("IBM"))
			assertTrue(
					"needsInput() should return false after finish() is called",
					!defl.needsInput());

		while (!defl.finished())
			x += defl.deflate(outPutBuf);
		int totalOut = defl.getTotalOut();
		int totalIn = defl.getTotalIn();
		assertTrue(
				"The total number of bytes from deflate did not equal getTotalOut",
				x == totalOut);
		assertTrue(
				"The number of input bytes from the array did not correspond with getTotalIn",
				totalIn == byteArray.length);
		defl.end();

		Inflater infl = new Inflater();
		try {
			infl.setInput(outPutBuf);
			while (!infl.finished())
				infl.inflate(outPutInf);
		} catch (DataFormatException e) {
			fail("Invalid input to be decompressed");
		}
		assertTrue(
				"Inflates getTotalOut() did not correspond with deflates getTotalIn()",
				infl.getTotalOut() == totalIn);
		assertTrue(
				"Inflates getTotalIn() did not correspond with deflates getTotalOut()",
				infl.getTotalIn() == totalOut);
		for (int i = 0; i < byteArray.length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i] == outPutInf[i]);
		assertEquals("Final decompressed data contained more bytes than original",
				0, outPutInf[byteArray.length]);
		infl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#finished()
	 */
	public void test_finished() {
		// test method java.util.zip.deflater.finish()
		byte byteArray[] = { 5, 2, 3, 7, 8 };
		byte outPutBuf[] = new byte[100];
		Deflater defl = new Deflater();
		assertTrue("Test 1: Deflater should not be finished.", !defl.finished());
		defl.setInput(byteArray);
		assertTrue("Test 2: Deflater should not be finished.", !defl.finished());
		defl.finish();
		assertTrue("Test 3: Deflater should not be finished.", !defl.finished());
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue("Test 4: Deflater should be finished.", defl.finished());
		defl.end();
		assertTrue("Test 5: Deflater should be finished.", defl.finished());
	}

	/**
	 * @tests java.util.zip.Deflater#getAdler()
	 */
	public void test_getAdler() {
		// test method for java.util.zip.Deflater.getAdler()
		byte byteArray[] = { 'a', 'b', 'c', 1, 2, 3 };
		byte outPutBuf[] = new byte[100];
		Deflater defl = new Deflater();

		// getting the checkSum value using the Adler
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		long checkSumD = defl.getAdler();
		defl.end();

		// getting the checkSum value through the Adler32 class
		Adler32 adl = new Adler32();
		adl.update(byteArray);
		long checkSumR = adl.getValue();
		assertTrue(
				"The checksum value returned by getAdler() is not the same as the checksum returned by creating the adler32 instance",
				checkSumR == checkSumD);
	}

	/**
	 * @tests java.util.zip.Deflater#getTotalIn()
	 */
	public void test_getTotalIn() {
		// test method java.util.zip.deflater.getTotalIn()
		byte outPutBuf[] = new byte[5];
		byte byteArray[] = { 1, 3, 4, 7, 8 };

		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue(
				"The number of input byte from the array did not correspond with getTotalIn",
				defl.getTotalIn() == byteArray.length);
		defl.end();

		defl = new Deflater();
		int offSet = 2;
		int length = 3;
		outPutBuf = new byte[5];
		defl.setInput(byteArray, offSet, length);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue(
				"The number of input byte sent to setInputBII() did not corrsepond with getTotalIn",
				defl.getTotalIn() == length);
		defl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#getTotalOut()
	 */
	public void test_getTotalOut() {
		// test method java.util.zip.deflater.getTotalOut()
		// the getTotalOut should equal the sum of value returned by deflate()
		byte outPutBuf[] = new byte[5];
		byte byteArray[] = { 5, 2, 3, 7, 8 };
		int x = 0;
		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			x += defl.deflate(outPutBuf);
		assertTrue(
				"The total number of bytes from deflate() did not equal getTotalOut",
				x == defl.getTotalOut());
		defl.end();

		x = 0;
		int offSet = 2;
		int length = 3;
		defl = new Deflater();
		outPutBuf = new byte[5];
		defl.setInput(byteArray, offSet, length);
		defl.finish();
		while (!defl.finished())
			x += defl.deflate(outPutBuf);
		assertTrue(
				"The total number of bytes from deflateBII() did not equal getTotalOut",
				x == defl.getTotalOut());
	}

	/**
	 * @tests java.util.zip.Deflater#needsInput()
	 */
	public void test_needsInput() {
		// test method of java.util.zip.deflater.needsInput()
		Deflater defl = new Deflater();
		assertTrue(
				"needsInput give the wrong boolean value as a result of no input buffer",
				defl.needsInput());
		byte byteArray[] = { 1, 2, 3 };
		defl.setInput(byteArray);
		assertTrue(
				"needsInput give wrong boolean value as a result of a full input buffer",
				!defl.needsInput());
		byte[] outPutBuf = new byte[50];
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		byte emptyByteArray[] = new byte[0];
		defl.setInput(emptyByteArray);
		assertTrue(
				"needsInput give wrong boolean value as a result of an empty input buffer",
				defl.needsInput());
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		// needsInput should NOT return true after finish() has been
		// called.
		if (System.getProperty("java.vendor").startsWith("IBM"))
			assertTrue(
					"needsInput gave wrong boolean value as a result of finish() being called",
					!defl.needsInput());
		defl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#reset()
	 */
	public void test_reset() {
		// test method of java.util.zip.deflater.reset()
		byte outPutBuf[] = new byte[100];
		byte outPutInf[] = new byte[100];
		byte curArray[] = new byte[5];
		byte byteArray[] = { 1, 3, 4, 7, 8 };
		byte byteArray2[] = { 8, 7, 4, 3, 1 };
		int x = 0;
		int orgValue = 0;
		Deflater defl = new Deflater();

		for (int i = 0; i < 3; i++) {
			if (i == 0)
				curArray = byteArray;
			else if (i == 1)
				curArray = byteArray2;
			else
				defl.reset();

			defl.setInput(curArray);
			defl.finish();
			while (!defl.finished())
				x += defl.deflate(outPutBuf);

			if (i == 0)
				assertTrue(
						"The total number of bytes from deflate did not equal getTotalOut",
						x == defl.getTotalOut());
			else if (i == 1)
				assertTrue(
						"The total number of bytes from deflate should still be the same ("
								+ x + ")", x == orgValue);
			else
				assertTrue(
						"The total number of bytes from deflate should be doubled ("
								+ orgValue * 2 + ")", x == orgValue * 2);

			if (i == 0)
				orgValue = x;

			try {
				Inflater infl = new Inflater();
				infl.setInput(outPutBuf);
				while (!infl.finished())
					infl.inflate(outPutInf);
				infl.end();
			} catch (DataFormatException e) {
				fail("Test " + i + ": Invalid input to be decompressed");
			}

			if (i == 1)
				curArray = byteArray;

			for (int j = 0; j < curArray.length; j++)
				assertTrue(
						"Test "
								+ i
								+ ": Final decompressed data does not equal the original data",
						curArray[j] == outPutInf[j]);
			assertTrue(
					"Test "
							+ i
							+ ": Final decompressed data contained more bytes than original",
					outPutInf[curArray.length] == 0);
		}
	}

	/**
	 * @tests java.util.zip.Deflater#setDictionary(byte[])
	 */
	public void test_setDictionary$B() {
		// test method of java.util.zip.deflater.setDictionary(byte[])
		// This test is very close to getAdler()
		byte dictionaryArray[] = { 'e', 'r', 't', 'a', 'b', 2, 3 };
		byte byteArray[] = { 4, 5, 3, 2, 'a', 'b', 6, 7, 8, 9, 0, 's', '3',
				'w', 'r' };
		byte outPutBuf[] = new byte[100];

		Deflater defl = new Deflater();
		long deflAdler = defl.getAdler();
		assertEquals("No dictionary set, no data deflated, getAdler should return 1",
				1, deflAdler);
		defl.setDictionary(dictionaryArray);
		deflAdler = defl.getAdler();

		// getting the checkSum value through the Adler32 class
		Adler32 adl = new Adler32();
		adl.update(dictionaryArray);
		long realAdler = adl.getValue();
		assertTrue(
				"Dictionary is set, getAdler() should equal the adler value of the dictionaryArray",
				deflAdler == realAdler);

		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		deflAdler = defl.getAdler();
		adl = new Adler32();
		adl.update(byteArray);
		realAdler = adl.getValue();
		// Deflate is finished and there were bytes deflated that did not occur
		// in the dictionaryArray, therefore a new dictionary was automatically
		// set.
		assertTrue("getAdler() returned " + deflAdler + " should be "
				+ realAdler, deflAdler == realAdler);
		defl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#setDictionary(byte[], int, int)
	 */
	public void test_setDictionary$BII() {
		// test method of java.util.zip.deflater.setDictionary(byte)
		// This test is very close to getAdler()
		byte dictionaryArray[] = { 'e', 'r', 't', 'a', 'b', 2, 3, 'o', 't' };
		byte byteArray[] = { 4, 5, 3, 2, 'a', 'b', 6, 7, 8, 9, 0, 's', '3',
				'w', 'r', 't', 'u', 'i', 'o', 4, 5, 6, 7 };
		byte outPutBuf[] = new byte[500];

		int offSet = 4;
		int length = 5;

		Deflater defl = new Deflater();
		long deflAdler = defl.getAdler();
		assertEquals("No dictionary set, no data deflated, getAdler should return 1",
				1, deflAdler);
		defl.setDictionary(dictionaryArray, offSet, length);
		deflAdler = defl.getAdler();

		// getting the checkSum value through the Adler32 class
		Adler32 adl = new Adler32();
		adl.update(dictionaryArray, offSet, length);
		long realAdler = adl.getValue();
		assertTrue(
				"Dictionary is set, getAdler() should equal the adler value of the dictionaryArray",
				deflAdler == realAdler);

		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		deflAdler = defl.getAdler();
		adl = new Adler32();
		adl.update(byteArray);
		realAdler = adl.getValue();
		// Deflate is finished and there were bytes deflated that did not occur
		// in the dictionaryArray, therefore a new dictionary was automatically
		// set.
		assertTrue("getAdler() returned " + deflAdler + " should be "
				+ realAdler, deflAdler == realAdler);
		defl.end();

		// boundary check
		defl = new Deflater();
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				offSet = 0;
				length = dictionaryArray.length + 1;
			} else {
				offSet = dictionaryArray.length + 1;
				length = 1;
			}
			try {
				defl.setDictionary(dictionaryArray, offSet, length);
				fail(
						"Test "
								+ i
								+ ": boundary check for setDictionary failed for offset "
								+ offSet + " and length " + length);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * @tests java.util.zip.Deflater#setInput(byte[])
	 */
	public void test_setInput$B() {
		// test method of java.util.zip.Deflater.setInput(byte)
		byte[] byteArray = { 1, 2, 3 };
		byte[] outPutBuf = new byte[50];
		byte[] outPutInf = new byte[50];

		Deflater defl = new Deflater();
		defl.setInput(byteArray);
		assertTrue("the array buffer in setInput() is empty", !defl
				.needsInput());
		// The second setInput() should be ignored since needsInput() return
		// false
		defl.setInput(byteArray);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		defl.end();

		Inflater infl = new Inflater();
		try {
			infl.setInput(outPutBuf);
			while (!infl.finished())
				infl.inflate(outPutInf);
		} catch (DataFormatException e) {
			fail("Invalid input to be decompressed");
		}
		for (int i = 0; i < byteArray.length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i] == outPutInf[i]);
		assertTrue(
				"Inflater.getTotalOut should have been equal to the length of the input",
				infl.getTotalOut() == byteArray.length);
		infl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#setInput(byte[], int, int)
	 */
	public void test_setInput$BII() {
		// test methods of java.util.zip.Deflater.setInput(byte,int,int)
		byte[] byteArray = { 1, 2, 3, 4, 5 };
		byte[] outPutBuf = new byte[50];
		byte[] outPutInf = new byte[50];
		int offSet = 1;
		int length = 3;

		Deflater defl = new Deflater();
		defl.setInput(byteArray, offSet, length);
		assertTrue("the array buffer in setInput() is empty", !defl
				.needsInput());
		// The second setInput() should be ignored since needsInput() return
		// false
		defl.setInput(byteArray, offSet, length);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		defl.end();

		Inflater infl = new Inflater();
		try {
			infl.setInput(outPutBuf);
			while (!infl.finished())
				infl.inflate(outPutInf);
		} catch (DataFormatException e) {
			fail("Invalid input to be decompressed");
		}
		for (int i = 0; i < length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i + offSet] == outPutInf[i]);
		assertTrue(
				"Inflater.getTotalOut should have been equal to the length of the input",
				infl.getTotalOut() == length);
		infl.end();

		// boundary check
		defl = new Deflater();
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				offSet = 0;
				length = byteArray.length + 1;
			} else {
				offSet = byteArray.length + 1;
				length = 1;
			}
			try {
				defl.setInput(byteArray, offSet, length);
				fail("Test " + i
						+ ": boundary check for setInput failed for offset "
						+ offSet + " and length " + length);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * @tests java.util.zip.Deflater#setLevel(int)
	 */
	public void test_setLevelI() {
		// test methods of java.util.zip.deflater.setLevel(int)
		// Very similar to test_Constructor(int)
		byte[] byteArray = new byte[100];
		try {
			InputStream inFile = Support_Resources
					.getStream("hyts_checkInput.txt");
			inFile.read(byteArray);
			inFile.close();
		} catch (IOException e) {
			fail("Unexpected IOException " + e + " during test");
		}

		byte[] outPutBuf;
		int totalOut;
		for (int i = 0; i < 10; i++) {
			Deflater defl = new Deflater();
			defl.setLevel(i);
			outPutBuf = new byte[500];
			defl.setInput(byteArray);
			while (!defl.needsInput())
				defl.deflate(outPutBuf);
			defl.finish();
			while (!defl.finished())
				defl.deflate(outPutBuf);
			totalOut = defl.getTotalOut();
			defl.end();

			outPutBuf = new byte[500];
			defl = new Deflater(i);
			defl.setInput(byteArray);
			while (!defl.needsInput())
				defl.deflate(outPutBuf);
			defl.finish();
			while (!defl.finished())
				defl.deflate(outPutBuf);
			assertTrue(
					"getTotalOut() not equal comparing two Deflaters with same compression level.",
					defl.getTotalOut() == totalOut);
			defl.end();
		}

		// testing boundaries
		try {
			Deflater boundDefl = new Deflater();
			// Level must be between 0-9
			boundDefl.setLevel(-2);
			fail(
					"IllegalArgumentException not thrown when setting level to a number < 0.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Deflater boundDefl = new Deflater();
			boundDefl.setLevel(10);
			fail(
					"IllegalArgumentException not thrown when setting level to a number > 9.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * @tests java.util.zip.Deflater#setStrategy(int)
	 */
	public void test_setStrategyI() {
		// test method of java.util.zip.deflater.setStrategy(int)
		byte[] byteArray = new byte[100];
		try {
			InputStream inFile = Support_Resources
					.getStream("hyts_checkInput.txt");
			inFile.read(byteArray);
			inFile.close();
		} catch (IOException e) {
			fail("Unexpected IOException " + e + " during test.");
		}

		for (int i = 0; i < 3; i++) {
			byte outPutBuf[] = new byte[500];
			MyDeflater mdefl = new MyDeflater();

			if (i == 0)
				mdefl.setStrategy(mdefl.getDefStrategy());
			else if (i == 1)
				mdefl.setStrategy(mdefl.getHuffman());
			else
				mdefl.setStrategy(mdefl.getFiltered());

			mdefl.setInput(byteArray);
			while (!mdefl.needsInput())
				mdefl.deflate(outPutBuf);
			mdefl.finish();
			while (!mdefl.finished())
				mdefl.deflate(outPutBuf);

			if (i == 0) {
				// System.out.println(mdefl.getTotalOut());
				// ran JDK and found that getTotalOut() = 86 for this particular
				// file
				assertEquals("getTotalOut() for the default strategy did not correspond with JDK",
						86, mdefl.getTotalOut());
			} else if (i == 1) {
				// System.out.println(mdefl.getTotalOut());
				// ran JDK and found that getTotalOut() = 100 for this
				// particular file
				assertEquals("getTotalOut() for the Huffman strategy did not correspond with JDK",
						100, mdefl.getTotalOut());
			} else {
				// System.out.println(mdefl.getTotalOut());
				// ran JDK and found that totalOut = 93 for this particular file
				assertEquals("Total Out for the Filtered strategy did not correspond with JDK",
						93, mdefl.getTotalOut());
			}
			mdefl.end();
		}

		// Attempting to setStrategy to an invalid value
		try {
			Deflater defl = new Deflater();
			defl.setStrategy(-412);
			fail(
					"IllegalArgumentException not thrown when setting strategy to an invalid value.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * @tests java.util.zip.Deflater#Deflater()
	 */
	public void test_Constructor() {
		// test methods of java.util.zip.Deflater()
		byte[] byteArray = new byte[100];
		try {

			InputStream inFile = Support_Resources
					.getStream("hyts_checkInput.txt");
			inFile.read(byteArray);
			inFile.close();
		} catch (IOException e) {
			fail("Unexpected IOException " + e + " during test");
		}

		Deflater defl = new Deflater();
		byte[] outPutBuf = new byte[500];
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		int totalOut = defl.getTotalOut();
		defl.end();

		// creating a Deflater using the DEFAULT_COMPRESSION as the int
		MyDeflater mdefl = new MyDeflater();
		mdefl = new MyDeflater(mdefl.getDefCompression());
		outPutBuf = new byte[500];
		mdefl.setInput(byteArray);
		while (!mdefl.needsInput())
			mdefl.deflate(outPutBuf);
		mdefl.finish();
		while (!mdefl.finished())
			mdefl.deflate(outPutBuf);
		assertTrue(
				"getTotalOut() not equal comparing two Deflaters with same compression level.",
				mdefl.getTotalOut() == totalOut);
		mdefl.end();
	}

	/**
	 * @tests java.util.zip.Deflater#Deflater(int, boolean)
	 */
	public void test_ConstructorIZ() {
		// test methods of java.util.zip.deflater(int,bool)
		byte byteArray[] = { 4, 5, 3, 2, 'a', 'b', 6, 7, 8, 9, 0, 's', '3',
				'w', 'r' };

		Deflater defl = new Deflater();
		byte outPutBuf[] = new byte[500];
		defl.setLevel(2);
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		int totalOut = defl.getTotalOut();
		defl.end();

		outPutBuf = new byte[500];
		defl = new Deflater(2, false);
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue(
				"getTotalOut() not equal comparing two Deflaters with same compression level.",
				defl.getTotalOut() == totalOut);
		defl.end();

		outPutBuf = new byte[500];
		defl = new Deflater(2, true);
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue(
				"getTotalOut() should not be equal comparing two Deflaters with different header options.",
				defl.getTotalOut() != totalOut);
		defl.end();

		byte outPutInf[] = new byte[500];
		Inflater infl = new Inflater(true);
		try {
			while (!infl.finished()) {
				if (infl.needsInput())
					infl.setInput(outPutBuf);
				infl.inflate(outPutInf);
			}
		} catch (DataFormatException e) {
			fail(
					"invalid input to inflate - called in test constructorIZ");
		}
		for (int i = 0; i < byteArray.length; i++)
			assertTrue(
					"Final decompressed data does not equal the original data",
					byteArray[i] == outPutInf[i]);
		assertEquals("final decompressed data contained more bytes than original - construcotrIZ",
				0, outPutInf[byteArray.length]);
		infl.end();

		infl = new Inflater(false);
		outPutInf = new byte[500];
		int r = 0;
		try {
			while (!infl.finished()) {
				if (infl.needsInput())
					infl.setInput(outPutBuf);
				infl.inflate(outPutInf);
			}
		} catch (DataFormatException e) {
			r = 1;
		}
		assertEquals("header option did not correspond", 1, r);

		// testing boundaries
		try {
			Deflater boundDefl = new Deflater();
			// Level must be between 0-9
			boundDefl.setLevel(-2);
			fail(
					"IllegalArgumentException not thrown when setting level to a number < 0.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Deflater boundDefl = new Deflater();
			boundDefl.setLevel(10);
			fail(
					"IllegalArgumentException not thrown when setting level to a number > 9.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * @tests java.util.zip.Deflater#Deflater(int)
	 */
	public void test_ConstructorI() {
		// test methods of java.util.zip.Deflater(int)
		byte[] byteArray = new byte[100];
		try {
			InputStream inFile = Support_Resources
					.getStream("hyts_checkInput.txt");
			inFile.read(byteArray);
			inFile.close();
		} catch (IOException e) {
			fail("Unexpected IOException " + e + " during test");
		}

		byte outPutBuf[] = new byte[500];
		Deflater defl = new Deflater(3);
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		int totalOut = defl.getTotalOut();
		defl.end();

		// test to see if the compression ratio is the same as setting the level
		// on a deflater
		outPutBuf = new byte[500];
		defl = new Deflater();
		defl.setLevel(3);
		defl.setInput(byteArray);
		while (!defl.needsInput())
			defl.deflate(outPutBuf);
		defl.finish();
		while (!defl.finished())
			defl.deflate(outPutBuf);
		assertTrue(
				"getTotalOut() not equal comparing two Deflaters with same compression level.",
				defl.getTotalOut() == totalOut);
		defl.end();

		// testing boundaries
		try {
			Deflater boundDefl = new Deflater();
			// Level must be between 0-9
			boundDefl.setLevel(-2);
			fail(
					"IllegalArgumentException not thrown when setting level to a number < 0.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Deflater boundDefl = new Deflater();
			boundDefl.setLevel(10);
			fail(
					"IllegalArgumentException not thrown when setting level to a number > 9.");
		} catch (IllegalArgumentException e) {
		}
	}

	private void helper_end_test(Deflater defl, String desc) {
		// Help tests for test_end() and test_reset().
		byte byteArray[] = { 5, 2, 3, 7, 8 };

		// Methods where we expect IllegalStateException or NullPointerException
		// to be thrown
		try {
			defl.getTotalOut();
			fail("defl.getTotalOut() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			defl.getTotalIn();
			fail("defl.getTotalIn() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			defl.getAdler();
			fail("defl.getAdler() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			byte[] dict = { 'a', 'b', 'c' };
			defl.setDictionary(dict);
			fail("defl.setDictionary() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			defl.getTotalIn();
			fail("defl.getTotalIn() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			defl.getTotalIn();
			fail("defl.getTotalIn() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}
		try {
			defl.deflate(byteArray);
			fail("defl.deflate() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (IllegalStateException e) {
		} catch (NullPointerException e) {
		}

		// Methods where we expect NullPointerException to be thrown
		try {
			defl.reset();
			fail("defl.reset() can still be used after " + desc
					+ " is called in test_" + desc);
		} catch (NullPointerException e) {
		}

		// Methods that should be allowed to be called after end() is called
		try {
			defl.needsInput();
		} catch (Exception e) {
			fail("Test 1: No exception should have been thrown: " + e
					+ " in test_" + desc);
		}
		try {
			defl.setStrategy(1);
		} catch (IllegalStateException e) {
			fail("Test 2: No exception should have been thrown: " + e
					+ " in test_" + desc);
		}
		try {
			defl.setLevel(1);
		} catch (IllegalStateException e) {
			fail("Test 3: No exception should have been thrown: " + e
					+ " in test_" + desc);
		}
		try {
			defl.end();
		} catch (Exception e) {
			fail("Test 4: No exception should have been thrown: " + e
					+ " in test_" + desc);
		}

		// Methods where exceptions should be thrown
		String vendor = System.getProperty("java.vendor");
		if (vendor.indexOf("IBM") != -1) {
			try {
				defl.setInput(byteArray);
				fail("defl.setInput() can still be used after " + desc
						+ " is called in test_" + desc);
			} catch (IllegalStateException e) {
			}
		}
	}

    /**
     * @tests java.util.zip.Deflater()
     */
    public void test_needsDictionary() {
        Deflater inf = new Deflater();
        assertEquals(0, inf.getTotalIn());
        assertEquals(0, inf.getTotalOut());
        assertEquals(0, inf.getBytesRead());
        assertEquals(0, inf.getBytesWritten());
    }
    
	protected void setUp() {
	}

	protected void tearDown() {
	}

}
