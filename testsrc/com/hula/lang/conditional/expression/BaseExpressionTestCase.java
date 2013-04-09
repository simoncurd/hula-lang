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
package com.hula.lang.conditional.expression;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Before;

import bsh.Interpreter;

import com.hula.lang.conditional.HulaConditionalLexer;
import com.hula.lang.conditional.HulaConditionalParser;
import com.hula.lang.conditional.HulaConditionalParser.parse_return;
import com.hula.lang.conditional.HulaConditionalTree;
import com.hula.lang.runtime.RuntimeConnector;

public class BaseExpressionTestCase
{
	protected RuntimeConnector rc;

	@Before
	public void setUp() throws Exception
	{
		Interpreter i = new Interpreter();
		this.rc = new RuntimeConnector(i, null);
	}

	protected boolean eval(String expression) throws Exception
	{
		HulaConditionalLexer lex = new HulaConditionalLexer(new ANTLRStringStream(expression));
		CommonTokenStream tokenStream = new CommonTokenStream(lex);
		HulaConditionalParser parser = new HulaConditionalParser(tokenStream);
		parse_return parserResult = parser.parse();

		System.out.println(parserResult.getTree().toStringTree());

		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(parserResult.getTree());
		HulaConditionalTree tree = new HulaConditionalTree(nodeStream);
		tree.setRuntimeConnector(rc);

		Object testResult = tree.test();
		if (testResult instanceof Boolean)
		{
			return ((Boolean) testResult).booleanValue();
		}
		throw new RuntimeException("result not boolean [" + testResult + "]");
	}

	protected String quotes(String value)
	{
		return "\"" + value + "\"";
	}

}
