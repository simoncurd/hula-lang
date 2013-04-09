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

public class NotEqualsTests extends BaseExpressionTestCase
{
	/***************************************
	 * Boolean Tests
	 ***************************************/

	@Test
	public void testBooleanNotEqualsBoolean() throws Exception
	{
		Assert.assertTrue(eval("true != false"));
		Assert.assertTrue(eval("false != true"));
	}

	@Test
	public void testBooleanVariableNotEqualsBoolean() throws Exception
	{
		rc.setVariable("active", false);
		Assert.assertTrue(eval("$active != true"));

		rc.setVariable("active", false);
		Assert.assertTrue(eval("$active != true"));
	}

	/***************************************
	 * Numeric Tests
	 ***************************************/

	@Test
	public void testNumberNotEqualsNumber() throws Exception
	{
		Assert.assertTrue(eval("33 != 32"));
		Assert.assertTrue(eval("33.5 != 32.5"));
		Assert.assertTrue(eval("33.54534534 != 32.54534534"));
	}

	@Test
	public void testBigDecimalVariableNotEqualsNumber() throws Exception
	{
		rc.setVariable("age", new BigDecimal(33));
		Assert.assertTrue(eval("$age != 32"));
	}

	@Test
	public void testIntegerVariableNotEqualsNumber() throws Exception
	{
		rc.setVariable("age", new Integer(33));
		Assert.assertTrue(eval("$age != 32"));
	}

	@Test
	public void testFloatVariableNotEqualsNumber() throws Exception
	{
		rc.setVariable("weight", new Float(33.5));
		Assert.assertTrue(eval("$weight != 32.5"));
	}

	@Test
	public void testLongVariableNotEqualsNumber() throws Exception
	{
		rc.setVariable("width", new Long(33000000000l));
		Assert.assertTrue(eval("$width != 32000000000"));
	}

	@Test
	public void testStringVariableNotEqualsNumber() throws Exception
	{
		rc.setVariable("weight", "33.5");
		Assert.assertTrue(eval("$weight != 32.5"));
	}

	/***************************************
	 * String Tests
	 ***************************************/

	@Test
	public void testStringNotEqualsString() throws Exception
	{
		// FIXME: conditional syntax bug - can't compare two strings
		Assert.assertTrue(eval("\"d\" != \"s\""));
	}


	@Test
	public void testStringVariableNotEqualsString() throws Exception
	{
		rc.setVariable("name", "d");
		Assert.assertTrue(eval("$name != \"s\""));
	}

	/***************************************
	 * Variable Tests
	 ***************************************/

	@Test
	public void testVariableNotEqualsVariable() throws Exception
	{
		rc.setVariable("thing", NotEqualsTests.class);
		Assert.assertFalse(eval("$thing != $thing"));
	}

	@Test
	public void testStringVariableNotEqualsStringVariable() throws Exception
	{
		rc.setVariable("weighta", "32.5");
		rc.setVariable("weightb", "33.5");
		Assert.assertTrue(eval("$weighta != $weightb"));
	}

	@Test
	public void testIntegerVariableNotEqualsIntegerVariable() throws Exception
	{
		rc.setVariable("weighta", new Integer("32"));
		rc.setVariable("weightb", new Integer("42"));
		Assert.assertTrue(eval("$weighta != $weightb"));
	}

	@Test
	public void testFloatVariableNotEqualsFloatVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta != $weightb"));
	}

	@Test
	public void testBigDecimalVariableNotEqualsBigDecimalVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("33.5"));
		Assert.assertTrue(eval("$weighta != $weightb"));
	}

}
