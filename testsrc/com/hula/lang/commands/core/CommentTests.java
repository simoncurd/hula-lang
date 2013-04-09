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

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class CommentTests extends BaseHulaTestCase
{
	@Test
	public void testComments() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("# this is a comment");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		System.out.println("[" + parserResult.getContent() + "]");

		Assert.assertEquals("comment is incorrectly parsed", "// # this is a comment\n", parserResult.getContent());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

	}

	@Test
	public void testComments2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Comment this is a comment");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		System.out.println("[" + parserResult.getContent() + "]");

		Assert.assertEquals("comment is incorrectly parsed", "// Comment this is a comment\n", parserResult.getContent());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

	}
}
