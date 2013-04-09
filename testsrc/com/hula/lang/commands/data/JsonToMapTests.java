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

public class JsonToMapTests extends BaseHulaTestCase
{
	@Test
	public void testJsonToMap() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("JsonToMap $json as data");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		Map<String, String> values = new HashMap<String, String>();
		values.put("k1", "v1");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("json", new Gson().toJson(values));
		evaluateBeanShell(parserResult, hctx);

		Map data = (Map) hctx.getParameter("data");

		Assert.assertNotNull("no data map provided", data);
		Assert.assertTrue("missing map key", data.containsKey("k1"));
		Assert.assertEquals("incorrect map value", "v1", data.get("k1"));

	}
}
