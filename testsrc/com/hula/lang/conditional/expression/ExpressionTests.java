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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionTests extends BaseExpressionTestCase
{
	@Test
	public void testExpression() throws Exception
	{
		rc.setVariable("name", "Jeff");
		rc.setVariable("age", "32");

		Map<String, String> person = new HashMap<String, String>();
		rc.setVariable("person", person);
		person.put("name", "Jeff");

		Assert.assertTrue(eval("$age = 32 AND $name = \"Jeff\""));
		Assert.assertTrue(eval("  $age = 32  AND  $name = \"Jeff\"   "));
		Assert.assertTrue(eval("  $age = 32  AND  $name = (\"Jeff\")   "));
		Assert.assertTrue(eval("  $age = 32  AND  ( $name = (\"Jeff\"))   "));

		Assert.assertTrue(eval("$person.name = \"Jeff\""));
	}
}
