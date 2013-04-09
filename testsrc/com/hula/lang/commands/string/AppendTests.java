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

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class AppendTests extends BaseHulaTestCase
{
	@Test
	public void testAppend() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Append \"Jeff\", value=\" Hoon\" as fullname");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect name", "Jeff Hoon", hctx.getParameter("fullname"));
	}

	@Test
	public void testAppendWithSourceReference() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set name=\"Jeff\"");
		wrapper.addLine("Append $name, value=\" Hoon\" as fullname");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect name", "Jeff Hoon", hctx.getParameter("fullname"));
	}

	@Test
	public void testNullSourceIsBlanked() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Append $name, value=\"Hoon\" as fullname");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect name", "Hoon", hctx.getParameter("fullname"));
	}

	@Test
	public void testAppendWithValueReference() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set value=\" Hoon\"");
		wrapper.addLine("Append \"Jeff\", value=$value as fullname");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect name", "Jeff Hoon", hctx.getParameter("fullname"));
	}

	@Test
	public void testNullValueIsBlanked() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Append \"Jeff \", value=$value as fullname");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("value", null);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect name", "Jeff ", hctx.getParameter("fullname"));
	}

	@Test
	public void testWhatever() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set url=\"admin.demo.hularizer.com\"");
		wrapper.addLine("Append $url, value=\":9080\" as url");
		wrapper.addLine("Append \"http://\", value=$url as url");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect url", "http://admin.demo.hularizer.com:9080", hctx.getParameter("url"));
	}
}
