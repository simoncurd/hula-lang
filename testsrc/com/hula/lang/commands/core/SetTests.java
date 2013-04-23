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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class SetTests extends BaseHulaTestCase
{
	@Test
	public void testSet() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set value=$name");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("name", "simon");
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect value set", "simon", hctx.getParameter("value"));

	}

	@Test
	public void testSetInternalCheck() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set value=$name");
		wrapper.addLine("If $value = \"simon\"");
		wrapper.addLine("	Set value2=true");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("name", "simon");
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect value set", "true", hctx.getParameter("value2"));

	}

	@Test
	public void testSetParameterInterdependencies() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set name=$person.name, age=32, $person.age=$age");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		Map<String, String> person = new HashMap<String, String>();
		person.put("name", "Jeff");
		hctx.setParameter("person", person);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect value set", "Jeff", hctx.getParameter("name"));
		Assert.assertEquals("incorrect value set", "32", person.get("age"));

	}

	@Test
	public void testSetWithEquals() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set value=\"a+b=c\"");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect value set", "a+b=c", hctx.getParameter("value"));
	}

	@Test
	public void testValidateMethodFailsParsing() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("no parameters for Set command should result in ParserException when unsupported");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			Assert.assertEquals("wrong number of errors", 1, e.getErrors().size());
			assertError(e, 0, 1, "Set", "At least one key-value pair is required");
		}
	}
}
