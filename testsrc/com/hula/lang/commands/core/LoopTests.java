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
package com.hula.lang.commands.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class LoopTests extends BaseHulaTestCase
{
	@Test
	public void testLoop() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("NewList csv=\"jack, jake, john\" as names");
		wrapper.addLine("Loop $names as nm");
		wrapper.addLine("	Echo \"name is $nm\"");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		// test
		Object value = hctx.getParameter("names");
		Assert.assertNotNull("missing list", value);
		Assert.assertTrue("incorrect list type", value instanceof List<?>);

		List<String> names = (List<String>) value;
		Assert.assertEquals("missing expected value", "jack", names.get(0));
		Assert.assertEquals("missing expected value", "jake", names.get(1));
		Assert.assertEquals("missing expected value", "john", names.get(2));

	}
}
