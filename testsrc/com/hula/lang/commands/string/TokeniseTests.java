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
package com.hula.lang.commands.string;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class TokeniseTests extends BaseHulaTestCase
{
	@Test
	public void testTokeniseWithoutDelimiter() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Tokenise \"simon,john,matthew\" as names");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing list", hctx.getParameter("names"));
		Object o = hctx.getParameter("names");
		Assert.assertTrue("incorrect parameter type", o instanceof List);
		List names = (List) o;
		Assert.assertTrue("missing name", names.contains("simon"));
		Assert.assertTrue("missing name", names.contains("john"));
		Assert.assertTrue("missing name", names.contains("matthew"));
	}

	@Test
	public void testTokeniseWithDelimiter() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Tokenise \":simon:john:matthew\",delimiter=: as names");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing list", hctx.getParameter("names"));
		Object o = hctx.getParameter("names");
		Assert.assertTrue("incorrect parameter type", o instanceof List);
		List names = (List) o;
		Assert.assertTrue("missing name", names.contains(""));
		Assert.assertTrue("missing name", names.contains("simon"));
		Assert.assertTrue("missing name", names.contains("john"));
		Assert.assertTrue("missing name", names.contains("matthew"));
	}

	@Test
	public void testTokeniseWithDelimiter2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Tokenise \"/simon/john/matthew\",delimiter=/ as names");
		wrapper.addLine("Echo $names.[2]");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing list", hctx.getParameter("names"));
		Object o = hctx.getParameter("names");
		Assert.assertTrue("incorrect parameter type", o instanceof List);
		List names = (List) o;
		Assert.assertTrue("missing name", names.contains(""));
		Assert.assertTrue("missing name", names.contains("simon"));
		Assert.assertTrue("missing name", names.contains("john"));
		Assert.assertTrue("missing name", names.contains("matthew"));
	}

	@Test
	public void testTokeniseWithRegexDelimiter() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Tokenise \"Jeff_John Sam\", delimiter=\"[_ ]\" as namelist");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing list", hctx.getParameter("namelist"));
		Object o = hctx.getParameter("namelist");
		Assert.assertTrue("incorrect parameter type", o instanceof List);
		List names = (List) o;
		Assert.assertTrue("missing name", names.contains("Jeff"));
		Assert.assertTrue("missing name", names.contains("John"));
		Assert.assertTrue("missing name", names.contains("Sam"));
	}
}
