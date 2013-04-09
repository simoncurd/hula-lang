/**
 * Copyright 2013 Simon Curd <simoncurd@gmail.com>
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
package com.hula.lang.conditional.expression;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class LessThanTests extends BaseExpressionTestCase
{

	/***************************************
	 * Numeric Tests
	 ***************************************/

	@Test
	public void testNumberGreaterThanNumber() throws Exception
	{
		Assert.assertTrue(eval("32 < 33"));
		Assert.assertTrue(eval("32.5 < 33.5"));
		Assert.assertTrue(eval("32.54534534 < 33.54534534"));

		Assert.assertFalse(eval("33 < 32"));
		Assert.assertFalse(eval("33.5 < 32.5"));
		Assert.assertFalse(eval("33.54534534 < 32.54534534"));
	}

	@Test
	public void testBigDecimalVariableGreaterThanNumber() throws Exception
	{
		rc.setVariable("age", new BigDecimal(32));
		Assert.assertTrue(eval("$age < 33"));
	}

	@Test
	public void testIntegerVariableGreaterThanNumber() throws Exception
	{
		rc.setVariable("age", new Integer(32));
		Assert.assertTrue(eval("$age < 33"));
	}

	@Test
	public void testFloatVariableGreaterThanNumber() throws Exception
	{
		rc.setVariable("weight", new Float(32.5));
		Assert.assertTrue(eval("$weight < 33.5"));
	}

	@Test
	public void testLongVariableGreaterThanNumber() throws Exception
	{
		rc.setVariable("width", new Long(32000000000l));
		Assert.assertTrue(eval("$width < 33000000000"));
	}

	@Test
	public void testStringVariableGreaterThanNumber() throws Exception
	{
		rc.setVariable("weight", "32.5");
		Assert.assertTrue(eval("$weight < 33.5"));
	}

	/***************************************
	 * String Tests
	 ***************************************/

	@Test
	public void testStringGreaterThanString() throws Exception
	{
		// FIXME: syntactic string bug here
		try
		{
			eval("\"b\" < \"a\"");
			Assert.fail("string gt/ge/lt/le comparisions not supported");
		}
		catch (NumberFormatException e)
		{
			// expected behaviour
		}
	}

	@Test
	public void testStringVariableGreaterThanString() throws Exception
	{
		rc.setVariable("name", "b\"");
		try
		{
			eval("$name < \"a\"");
			Assert.fail("string gt/ge/lt/le comparisions not supported");
		}
		catch (NumberFormatException e)
		{
			// expected behaviour
		}
	}

	/***************************************
	 * Variable Tests
	 ***************************************/

	@Test
	public void testVariableGreaterThanVariable() throws Exception
	{
		rc.setVariable("thing", LessThanTests.class);
		try
		{
			Assert.assertFalse(eval("$thing < $thing"));
			Assert.fail("Object comparison for Greater Than shouldn't be possible");
		}
		catch (RuntimeException e)
		{
			// expected behaviour
		}
	}

	@Test
	public void testStringVariableGreaterThanStringVariable() throws Exception
	{
		rc.setVariable("weighta", "32.5");
		rc.setVariable("weightb", "33.5");
		Assert.assertTrue(eval("$weighta < $weightb"));
	}

	@Test
	public void testIntegerVariableGreaterThanIntegerVariable() throws Exception
	{
		rc.setVariable("weighta", new Integer("32"));
		rc.setVariable("weightb", new Integer("42"));
		Assert.assertTrue(eval("$weighta < $weightb"));
	}

	@Test
	public void testFloatVariableGreaterThanFloatVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta < $weightb"));
	}

	@Test
	public void testBigDecimalVariableGreaterThanBigDecimalVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta < $weightb"));
	}

}
