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

public class GreaterThanEqualToTests extends BaseExpressionTestCase
{

	/***************************************
	 * Numeric Tests
	 ***************************************/

	@Test
	public void testNumberGreaterThanEqualToNumber() throws Exception
	{
		Assert.assertTrue(eval("33 >= 32"));
		Assert.assertTrue(eval("33.5 >= 32.5"));
		Assert.assertTrue(eval("33.54534534 >= 32.54534534"));

		Assert.assertFalse(eval("32 >= 33"));
		Assert.assertFalse(eval("32.5 >= 33.5"));
		Assert.assertFalse(eval("32.54534534 >= 33.54534534"));

		Assert.assertTrue(eval("32 >= 32"));
		Assert.assertTrue(eval("32.5 >= 32.5"));
		Assert.assertTrue(eval("32.54534534 >= 32.54534534"));

		Assert.assertFalse(eval("32 >= 33"));
		Assert.assertFalse(eval("32.5 >= 33.5"));
		Assert.assertFalse(eval("32.54534534 >= 33.54534534"));
	}

	@Test
	public void testBigDecimalVariableGreaterThanEqualToNumber() throws Exception
	{
		rc.setVariable("age", new BigDecimal(33));
		Assert.assertTrue(eval("$age >= 32"));
		Assert.assertTrue(eval("$age >= 33"));
	}

	@Test
	public void testIntegerVariableGreaterThanEqualToNumber() throws Exception
	{
		rc.setVariable("age", new Integer(33));
		Assert.assertTrue(eval("$age >= 32"));
		Assert.assertTrue(eval("$age >= 33"));
	}

	@Test
	public void testFloatVariableGreaterThanEqualToNumber() throws Exception
	{
		rc.setVariable("weight", new Float(33.5));
		Assert.assertTrue(eval("$weight >= 32.5"));
		Assert.assertTrue(eval("$weight >= 33.5"));
	}

	@Test
	public void testLongVariableGreaterThanEqualToNumber() throws Exception
	{
		rc.setVariable("width", new Long(33000000000l));
		Assert.assertTrue(eval("$width >= 32000000000"));
		Assert.assertTrue(eval("$width >= 33000000000"));
	}

	@Test
	public void testStringVariableGreaterThanEqualToNumber() throws Exception
	{
		rc.setVariable("weight", "33.5");
		Assert.assertTrue(eval("$weight >= 32.5"));
		Assert.assertTrue(eval("$weight >= 33.5"));
	}

	/***************************************
	 * String Tests
	 ***************************************/

	@Test
	public void testStringGreaterThanEqualToString() throws Exception
	{
		// FIXME: syntactic string bug here
		try
		{
			eval("\"b\" >= \"a\"");
			Assert.fail("string gt/ge/lt/le comparisions not supported");
		}
		catch (NumberFormatException e)
		{
			// expected behaviour
		}
	}

	@Test
	public void testStringVariableGreaterThanEqualToString() throws Exception
	{
		rc.setVariable("name", "b\"");
		try
		{
			eval("$name >= \"a\"");
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
	public void testVariableGreaterThanEqualToVariable() throws Exception
	{
		rc.setVariable("thing", GreaterThanEqualToTests.class);
		try
		{
			Assert.assertFalse(eval("$thing >= $thing"));
			Assert.fail("Object comparison for Greater Than shouldn't be possible");
		}
		catch (RuntimeException e)
		{
			// expected behaviour
		}
	}

	@Test
	public void testStringVariableGreaterThanEqualToStringVariable() throws Exception
	{
		rc.setVariable("weighta", "33.5");
		rc.setVariable("weightb", "32.5");
		Assert.assertTrue(eval("$weighta >= $weightb"));

		rc.setVariable("weightb", "33.5");
		Assert.assertTrue(eval("$weighta >= $weightb"));
	}

	@Test
	public void testIntegerVariableGreaterThanEqualToIntegerVariable() throws Exception
	{
		rc.setVariable("weighta", new Integer("42"));
		rc.setVariable("weightb", new Integer("32"));
		Assert.assertTrue(eval("$weighta >= $weightb"));

		rc.setVariable("weightb", new Integer("32"));
		Assert.assertTrue(eval("$weighta >= $weightb"));
	}

	@Test
	public void testFloatVariableGreaterThanEqualToFloatVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("33.5"));
		rc.setVariable("weightb", new Float("32.5"));
		Assert.assertTrue(eval("$weighta >= $weightb"));

		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta >= $weightb"));
	}

	@Test
	public void testBigDecimalVariableGreaterThanEqualToBigDecimalVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("33.5"));
		rc.setVariable("weightb", new Float("32.5"));
		Assert.assertTrue(eval("$weighta >= $weightb"));

		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta >= $weightb"));
	}

}
