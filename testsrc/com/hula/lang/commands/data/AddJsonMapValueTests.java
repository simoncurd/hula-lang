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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class AddJsonMapValueTests extends BaseHulaTestCase
{
	@Test
	public void testAddJsonMapValue() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("AddJsonMapValue $json, key=k2, value=v2");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		Map<String, String> values = new HashMap<String, String>();
		values.put("k1", "v1");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("json", new Gson().toJson(values));
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("invalid json", "{\"k1\":\"v1\",\"k2\":\"v2\"}", hctx.getParameter("json"));

	}

	public class MockObject
	{
		public String mockString = null;

		public String getMockString()
		{
			return mockString;
		}

		public void setMockString(String mockString)
		{
			this.mockString = mockString;
		}

	}

	@Test
	public void testAddJsonMapValueAsNested() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("AddJsonMapValue $mockObject.mockString, key=k2, value=v2");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		Map<String, String> values = new HashMap<String, String>();
		values.put("k1", "v1");
		MockObject mo1 = new MockObject();
		mo1.mockString = new Gson().toJson(values);

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("mockObject", mo1);
		evaluateBeanShell(parserResult, hctx);

		MockObject mo2 = (MockObject) hctx.getParameter("mockObject");

		Assert.assertEquals("invalid json", "{\"k1\":\"v1\",\"k2\":\"v2\"}", mo2.mockString);

	}
}
