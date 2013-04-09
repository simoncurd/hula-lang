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

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class GetVariableNamesTests extends BaseHulaTestCase
{
	@Test
	public void testNewListFromPrefix() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("GetVariableNames prefix=sub_ as items");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("sub_3924fji", "item1");
		hctx.setParameter("sub_3sfgd", "item2");
		hctx.setParameter("sub_vjfhjd", "item3");
		hctx.setParameter("subfdsgf", "item4");
		hctx.setParameter("ub_vsfdsd", "item5");
		evaluateBeanShell(parserResult, hctx);

		Assert.assertNotNull("missing list", hctx.getParameter("items"));
		List list = (List) hctx.getParameter("items");

		Assert.assertEquals("wrong number of items", 3, list.size());

		assertContains(list, "sub_3924fji");
		assertContains(list, "sub_3sfgd");
		assertContains(list, "sub_vjfhjd");

	}

	public void assertContains(List list, Object object) throws Exception
	{
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Object obj = iter.next();
			if (obj.equals(object))
			{
				return;
			}
		}
		Assert.fail("object [" + object + "] not found in list");
	}
}
