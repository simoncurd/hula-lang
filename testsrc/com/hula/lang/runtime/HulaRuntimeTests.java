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
package com.hula.lang.runtime;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.util.ScriptWrapper;

public class HulaRuntimeTests extends BaseHulaTestCase
{
	@Test
	public void testReferenceReplacement() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Set eventname=\"latest-gadgets-live\"");
		wrapper.addLine("Set eventid=\"123\"");
		wrapper.addLine("Set eventURL=\"/$eventname/event/$eventid\"");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("incorrect eventURL", "/latest-gadgets-live/event/123", hctx.getParameter("eventURL"));

	}
	
	@Test
	public void testUndefinedVariableIsNull() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();

		wrapper.addLine("NewMap as session");
		wrapper.addLine("Set name=$session.name");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);
		
		Assert.assertEquals("invalid result", null, hctx.getParameter("name"));

	}
	
	@Test
	public void testNullConditional() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();

		wrapper.addLine("If $name=null");
		wrapper.addLine("	Set result=\"true\"");
		wrapper.addLine("Else");
		wrapper.addLine("	Set result=\"false\"");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);
		
		Assert.assertEquals("invalid result", "true", hctx.getParameter("result"));

	}
}
