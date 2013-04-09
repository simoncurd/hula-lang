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
package com.hula.lang.commands.date;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.DateUtil;
import com.hula.lang.util.ScriptWrapper;

public class StringToDateTests extends BaseHulaTestCase
{
	@Test
	public void testGetDate() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("StringToDate $dateStr as date");
		//

		String dateStr = "2012-05-20 09:08:34";

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("dateStr", dateStr);
		evaluateBeanShell(parserResult, hctx);

		Date expected = DateUtil.toDate(dateStr, DateUtil.DATE_FORMAT);

		Assert.assertEquals("incorrect date value", expected, hctx.getParameter("date"));
	}

	@Test
	public void testGetDateWithFormat() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("StringToDate $dateStr, format=$format as date");

		String dateStr = "2012-05-20 09:08:34";

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("dateStr", "2012/05/20 090834");
		hctx.setParameter("format", "yyyy/MM/dd HHmmss");
		evaluateBeanShell(parserResult, hctx);

		Date expected = DateUtil.toDate(dateStr, DateUtil.DATE_FORMAT);

		Assert.assertEquals("incorrect date value", expected, hctx.getParameter("date"));
	}
}
