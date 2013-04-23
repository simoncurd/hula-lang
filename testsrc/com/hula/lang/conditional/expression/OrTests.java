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

public class OrTests extends BaseExpressionTestCase
{
	@Test
	public void testBooleanOrBoolean() throws Exception
	{
		Assert.assertTrue(eval("true OR true"));
		Assert.assertFalse(eval("false OR false"));
		Assert.assertTrue(eval("true OR false"));
		Assert.assertTrue(eval("false OR true"));
	}

	@Test
	public void testBooleanVariableOrBoolean() throws Exception
	{
		rc.setVariable("left", new Boolean(true));
		Assert.assertTrue(eval("$left OR true"));
		Assert.assertTrue(eval("$left OR false"));

		rc.setVariable("left", new Boolean(false));
		Assert.assertFalse(eval("$left OR false"));
		Assert.assertTrue(eval("$left OR true"));
	}

	@Test
	public void testBooleanVariableOrBooleanVariable() throws Exception
	{
		rc.setVariable("left", new Boolean(true));
		rc.setVariable("right", new Boolean(true));
		Assert.assertTrue(eval("$left OR $right"));

		rc.setVariable("right", new Boolean(false));
		Assert.assertTrue(eval("$left OR $right"));
	}

	@Test
	public void testBooleanOrString() throws Exception
	{
		try
		{
			Assert.assertTrue(eval("true OR \"true\""));
			Assert.fail("cannot compare non-boolean values");
		}
		catch (RuntimeException e)
		{
			// expected behaviour
		}
	}

}
