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

public class EqualsTests extends BaseExpressionTestCase
{

	/***************************************
	 * Boolean Tests
	 ***************************************/

	@Test
	public void testBooleanEqualsBoolean() throws Exception
	{
		Assert.assertTrue(eval("true = true"));
		Assert.assertTrue(eval("false = false"));
	}

	@Test
	public void testBooleanVariableEqualsBoolean() throws Exception
	{
		rc.setVariable("active", true);
		Assert.assertTrue(eval("$active = true"));

		rc.setVariable("active", false);
		Assert.assertTrue(eval("$active = false"));
	}

	/***************************************
	 * Numeric Tests
	 ***************************************/

	@Test
	public void testNumberEqualsNumber() throws Exception
	{
		Assert.assertTrue(eval("32 = 32"));
		Assert.assertTrue(eval("32.5 = 32.5"));
		Assert.assertTrue(eval("32.54534534 = 32.54534534"));
	}

	@Test
	public void testBigDecimalVariableEqualsNumber() throws Exception
	{
		rc.setVariable("age", new BigDecimal(32));
		Assert.assertTrue(eval("$age = 32"));
	}

	@Test
	public void testIntegerVariableEqualsNumber() throws Exception
	{
		rc.setVariable("age", new Integer(32));
		Assert.assertTrue(eval("$age = 32"));
	}

	@Test
	public void testFloatVariableEqualsNumber() throws Exception
	{
		rc.setVariable("weight", new Float(32.5));
		Assert.assertTrue(eval("$weight = 32.5"));
	}

	@Test
	public void testLongVariableEqualsNumber() throws Exception
	{
		rc.setVariable("width", new Long(32000000000l));
		Assert.assertTrue(eval("$width = 32000000000"));
	}

	@Test
	public void testStringVariableEqualsNumber() throws Exception
	{
		rc.setVariable("weight", "32.5");
		Assert.assertTrue(eval("$weight = 32.5"));
	}

	/***************************************
	 * String Tests
	 ***************************************/

	@Test
	public void testStringEqualsString() throws Exception
	{
		Assert.assertTrue(eval("\"s\"=\"s\""));
	}

	@Test
	public void testStringEqualsString_EscapedQuotes() throws Exception
	{
		// FIXME: conditional syntax bug - doesn't support escaped quotes
		Assert.assertTrue(eval("\"s\"\" = \"s\"\""));
	}

	@Test
	public void testStringVariableEqualsString() throws Exception
	{
		rc.setVariable("name", "Jeff");
		Assert.assertTrue(eval("$name = \"Jeff\""));
	}

	/***************************************
	 * Variable Tests
	 ***************************************/

	@Test
	public void testVariableEqualsVariable() throws Exception
	{
		rc.setVariable("thing", EqualsTests.class);
		Assert.assertTrue(eval("$thing = $thing"));
	}

	@Test
	public void testStringVariableEqualsStringVariable() throws Exception
	{
		rc.setVariable("weighta", "32.5");
		rc.setVariable("weightb", "32.5");
		Assert.assertTrue(eval("$weighta = $weightb"));
	}

	@Test
	public void testIntegerVariableEqualsIntegerVariable() throws Exception
	{
		rc.setVariable("weighta", new Integer("32"));
		rc.setVariable("weightb", new Integer("32"));
		Assert.assertTrue(eval("$weighta = $weightb"));
	}

	@Test
	public void testFloatVariableEqualsFloatVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("32.5"));
		Assert.assertTrue(eval("$weighta = $weightb"));
	}

	@Test
	public void testBigDecimalVariableEqualsBigDecimalVariable() throws Exception
	{
		rc.setVariable("weighta", new Float("32.5"));
		rc.setVariable("weightb", new Float("32.5"));
		Assert.assertTrue(eval("$weighta = $weightb"));
	}

}
