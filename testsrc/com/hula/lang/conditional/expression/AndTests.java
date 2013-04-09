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



import org.junit.Assert;
import org.junit.Test;

public class AndTests extends BaseExpressionTestCase
{
	@Test
	public void testBooleanAndBoolean() throws Exception
	{
		Assert.assertTrue(eval("true AND true"));
		Assert.assertFalse(eval("false AND false"));
		Assert.assertFalse(eval("true AND false"));
		Assert.assertFalse(eval("false AND true"));
	}

	@Test
	public void testBooleanVariableAndBoolean() throws Exception
	{
		rc.setVariable("left", new Boolean(true));
		Assert.assertTrue(eval("$left AND true"));
		Assert.assertFalse(eval("$left AND false"));

		rc.setVariable("left", new Boolean(false));
		Assert.assertFalse(eval("$left AND false"));
		Assert.assertFalse(eval("$left AND true"));
	}

	@Test
	public void testBooleanVariableAndBooleanVariable() throws Exception
	{
		rc.setVariable("left", new Boolean(true));
		rc.setVariable("right", new Boolean(true));
		Assert.assertTrue(eval("$left AND $right"));

		rc.setVariable("right", new Boolean(false));
		Assert.assertFalse(eval("$left AND $right"));
	}

	@Test
	public void testBooleanAndString() throws Exception
	{
		try
		{
			Assert.assertTrue(eval("true AND \"true\""));
			Assert.fail("cannot compare non-boolean values");
		}
		catch (RuntimeException e)
		{
			// expected behaviour
		}
	}

	@Test
	public void testExpressionAndExpression() throws Exception
	{
		Assert.assertTrue(eval("(true AND true) AND (true AND true)"));
		Assert.assertTrue(eval("(true OR false) AND (false OR true)"));
		Assert.assertTrue(eval("(true AND false) OR (false OR true)"));
		Assert.assertTrue(eval("(false AND false) OR (false OR true)"));
		Assert.assertFalse(eval("(false AND false) OR (false OR false)"));
		Assert.assertFalse(eval("(false AND false) OR (false AND false)"));
	}

}
