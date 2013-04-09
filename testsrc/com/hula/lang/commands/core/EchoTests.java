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
import org.junit.Before;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;

public class EchoTests extends BaseHulaTestCase
{
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		// TODO: delete log file
	}

	private String readLogFile()
	{
		// TODO: read in log file
		return "";
	}

	@Test
	public void testEchoInfo() throws Exception
	{
		String testMessage = "this is the message to test for";
		String content = "Echo \"" + testMessage + "\"";

		HulaContext hctx = new HulaContext();

		HulaExecutable executable = parseAndAssert(content);
		evaluateBeanShell(executable, hctx);

		Assert.assertEquals("wrong value", testMessage, readLogFile());
	}

	@Test
	public void testEchoReferences() throws Exception
	{
		String testMessage = "Hi my name is $name, nice to meet you";
		String content = "Echo \"" + testMessage + "\"";

		HulaContext hctx = new HulaContext();

		hctx.setParameter("name", "Jeff");

		HulaExecutable executable = parseAndAssert(content);
		evaluateBeanShell(executable, hctx);

		Assert.assertEquals("wrong value", "Hi my name is Jeff, nice to meet you", readLogFile());
	}

	@Test
	public void testEchoReferencesNested() throws Exception
	{
		String testMessage = "Hi my name is $person.name, nice to meet you";
		String content = "Echo \"" + testMessage + "\"";

		HulaContext hctx = new HulaContext();

		Map<String, String> person = new HashMap<String, String>();
		person.put("name", "Jeff");
		hctx.setParameter("person", person);

		HulaExecutable executable = parseAndAssert(content);
		evaluateBeanShell(executable, hctx);

		Assert.assertEquals("wrong value", "Hi my name is Jeff, nice to meet you", readLogFile());
	}

	@Test
	public void testEchoError() throws Exception
	{
		String testMessage = "this is the message to test for";
		String content = "Echo \"" + testMessage + "\", type=error";

		HulaContext hctx = new HulaContext();

		HulaExecutable executable = parseAndAssert(content);
		evaluateBeanShell(executable, hctx);

		Assert.assertEquals("wrong value", testMessage, readLogFile());
		Assert.assertEquals("wrong value", null, readLogFile());
	}
}
