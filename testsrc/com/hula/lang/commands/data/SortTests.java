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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class SortTests extends BaseHulaTestCase
{
	@Test
	public void testSortOnNumbers() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Sort $list, type=Numeric");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List list = new ArrayList();
		list.add("3");
		list.add("87");
		list.add("9");
		list.add("1");
		list.add("2");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("list", list);
		evaluateBeanShell(parserResult, hctx);

		assertValue(list, 0, "1");
		assertValue(list, 1, "2");
		assertValue(list, 2, "3");
		assertValue(list, 3, "9");
		assertValue(list, 4, "87");
	}

	@Test
	public void testSortOnLetters() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Sort $list, type=Alpha");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		List list = new ArrayList();
		list.add("c");
		list.add("e");
		list.add("d");
		list.add("a");
		list.add("b");

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("list", list);
		evaluateBeanShell(parserResult, hctx);

		assertValue(list, 0, "a");
		assertValue(list, 1, "b");
		assertValue(list, 2, "c");
		assertValue(list, 3, "d");
		assertValue(list, 4, "e");
	}

	public class MockObject
	{
		private String num;

		public MockObject(String num)
		{
			this.num = num;
		}

		public String getNum()
		{
			return num;
		}

		public void setNum(String num)
		{
			this.num = num;
		}

	}

	@Test
	public void testSortOnNumberProperty() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Sort $list, property=num, type=Numeric");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		MockObject mo3 = new MockObject("3");
		MockObject mo87 = new MockObject("87");
		MockObject mo9 = new MockObject("9");
		MockObject mo1 = new MockObject("1");
		MockObject mo2 = new MockObject("2");

		List list = new ArrayList();
		list.add(mo3);
		list.add(mo87);
		list.add(mo9);
		list.add(mo1);
		list.add(mo2);

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("list", list);
		evaluateBeanShell(parserResult, hctx);

		assertValue(list, 0, mo1);
		assertValue(list, 1, mo2);
		assertValue(list, 2, mo3);
		assertValue(list, 3, mo9);
		assertValue(list, 4, mo87);
	}

	public void assertValue(List list, int index, Object value)
	{
		Object s = list.get(index);
		Assert.assertEquals("wrong item at index [" + index + "]", value, s);
	}
}
