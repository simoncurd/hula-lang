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
package com.hula.lang.commands.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class ListContainsTests extends BaseHulaTestCase
{
	@Test
	public void testListContainsForPresentString() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ListContains $words, value=\"Jeff\" as result");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List<String> list = new ArrayList<String>();
		list.add("Jeff");
		list.add("John");
		list.add("Henry");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("words", list);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing result", hctx.getParameter("result"));
		Assert.assertEquals("wrong result", true, hctx.getParameter("result"));
	}

	@Test
	public void testListContainsForPresentObject() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ListContains $items, value=$record as result");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		Map<String, String> r = new HashMap<String, String>();

		List<Object> list = new ArrayList<Object>();
		list.add("Jeff");
		list.add(r);

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("items", list);
		hctx.setParameter("record", r);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing result", hctx.getParameter("result"));
		Assert.assertEquals("wrong result", true, hctx.getParameter("result"));
	}

	@Test
	public void testListContainsForMissingString() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ListContains $words, value=\"jeff\" as result");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List<String> list = new ArrayList<String>();
		list.add("Jeff");
		list.add("John");
		list.add("Henry");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("words", list);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing result", hctx.getParameter("result"));
		Assert.assertEquals("wrong result", false, hctx.getParameter("result"));
	}

	public class MockItem
	{
		public String name;

		public MockItem(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

	}

	@Test
	public void testListContainsForPathForPresentString() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ListContains $words, path=name, value=\"Jeff\" as result");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List<MockItem> list = new ArrayList<MockItem>();
		list.add(new MockItem("Jeff"));
		list.add(new MockItem("John"));
		list.add(new MockItem("Henry"));

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("words", list);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing result", hctx.getParameter("result"));
		Assert.assertEquals("wrong result", true, hctx.getParameter("result"));
	}

	@Test
	public void testListContainsForPathForMissingString() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ListContains $words, path=name, value=\"jeff\" as result");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List<MockItem> list = new ArrayList<MockItem>();
		list.add(new MockItem("Jeff"));
		list.add(new MockItem("John"));
		list.add(new MockItem("Henry"));

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("words", list);
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing result", hctx.getParameter("result"));
		Assert.assertEquals("wrong result", false, hctx.getParameter("result"));
	}

}
