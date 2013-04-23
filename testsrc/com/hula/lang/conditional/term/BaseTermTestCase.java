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
package com.hula.lang.conditional.term;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Assert;

import com.hula.lang.conditional.HulaConditionalLexer;
import com.hula.lang.conditional.HulaConditionalParser;
import com.hula.lang.conditional.HulaConditionalParser.term_return;
import com.hula.lang.conditional.HulaConditionalTree;

public class BaseTermTestCase
{

	protected Object parseTerm(String term) throws Exception
	{
		HulaConditionalLexer lex = new HulaConditionalLexer(new ANTLRStringStream(term));
		CommonTokenStream tokenStream = new CommonTokenStream(lex);

		HulaConditionalParser parser = new HulaConditionalParser(tokenStream);
		term_return parserResult = parser.term();

		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(parserResult.getTree());
		HulaConditionalTree tree = new HulaConditionalTree(nodeStream);

		return tree.test();
	}

	protected void assertFails(String term) throws Exception
	{
		try
		{
			Object result = parseTerm(term);
			Assert.fail("term [" + term + "] should not parse - result [" + result + "]");
		}
		catch (RuntimeException e)
		{
			// expected behaviour
		}
		catch (NoViableAltException e)
		{
			// expected behaviour
		}
	}

	protected String quotes(String value)
	{
		return "\"" + value + "\"";
	}

}
