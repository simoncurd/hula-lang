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
package com.hula.lang.parser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.runtime.exception.HulaPlayerException;
import com.hula.lang.util.ScriptWrapper;

public class HulaParserTests extends BaseHulaTestCase
{
	@Test
	public void testMissingEndFailsParsing() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("	Set value=a");

		// parse

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("missing end statement should result in ParserException");
		}
		catch (HulaParserException e)
		{
			// expected behaviour

			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			Assert.assertEquals("missing line number", 1, error.getLineNumber());
			Assert.assertEquals("missing command line", "If true", error.getCommandLine());
			Assert.assertEquals("missing error code", "missing.end.statement", error.getErrorCode());
		}

	}

	@Test
	public void testMissingEndFailsParsing_2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Loop $names as name");
		wrapper.addLine("	Set value=$name");

		// parse

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("missing end statement should result in ParserException");
		}
		catch (HulaParserException e)
		{
			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			// expected behaviour
			Assert.assertEquals("missing line number", 1, error.getLineNumber());
			Assert.assertEquals("missing command line", "Loop $names as name", error.getCommandLine());
			Assert.assertEquals("missing error code", "missing.end.statement", error.getErrorCode());
		}

	}

	@Test
	public void testSuperfluousEndFailsParsing() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("	Set value=a");
		wrapper.addLine("End");
		wrapper.addLine("End");

		// parse

		try
		{
			HulaExecutable parserResult = parser.parse(wrapper.toString());
			System.out.println(parserResult.getContent());
			Assert.fail("additional end statement should result in ParserException");
		}
		catch (HulaParserException e)
		{
			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			// expected behaviour
			Assert.assertEquals("missing line number", 4, error.getLineNumber());
			Assert.assertEquals("missing command line", "End", error.getCommandLine());
			Assert.assertEquals("missing error code", "unnecessary.end.statement", error.getErrorCode());
		}

	}

	@Test
	public void testSuperfluousEndFailsParsing_2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("End");

		// parse

		try
		{
			HulaExecutable parserResult = parser.parse(wrapper.toString());
			System.out.println(parserResult.getContent());
			Assert.fail("additional end statement should result in ParserException");
		}
		catch (HulaParserException e)
		{
			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			// expected behaviour
			Assert.assertEquals("missing line number", 1, error.getLineNumber());
			Assert.assertEquals("missing command line", "End", error.getCommandLine());
			Assert.assertEquals("missing error code", "unnecessary.end.statement", error.getErrorCode());
		}

	}

	@Test
	public void testUnknownCommandFailsParsing() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Meh");

		// parse

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("unknown command should result in ParserException");
		}
		catch (HulaParserException e)
		{
			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			// expected behaviour
			Assert.assertEquals("missing line number", 1, error.getLineNumber());
			Assert.assertEquals("missing command line", "Meh", error.getCommandLine());
			Assert.assertEquals("missing error code", "unknown.command", error.getErrorCode());
		}
	}

	@Test
	public void testParserValidatesGeneratedCode() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Echo hello");
		wrapper.addLine("ProduceJunkCode");
		wrapper.addLine("Echo goodbye");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("unknown command should result in ParserException");
		}
		catch (HulaParserException e)
		{
			List<ParseError> errors = e.getErrors();
			ParseError error = errors.get(0);

			// expected behaviour
			Assert.assertEquals("missing line number", -1, error.getLineNumber());
			Assert.assertEquals("missing command line", null, error.getCommandLine());
			Assert.assertEquals("missing error code", "unknown.parse.error", error.getErrorCode());
		}
	}

	@Test
	public void testUnhandledFailuresThrowCorrectException() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Echo test");
		wrapper.addLine("ProduceError");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();

		try
		{
			evaluateBeanShell(parserResult, hctx);
			Assert.fail("expected validate to fail");
		}
		catch (HulaPlayerException e)
		{
			// expected behaviour
			Assert.assertEquals("incorrect line number", 2, e.getLineNumber());
		}

	}

	@Test
	public void testNPEThrowCorrectException() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Echo meh");
		wrapper.addLine("If $s1 = \"yes\"");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();

		try
		{
			evaluateBeanShell(parserResult, hctx);
			Assert.fail("expected validate to fail");
		}
		catch (HulaPlayerException e)
		{
			// expected behaviour
			// assertEquals("incorrect variable name", "s1", e.getVariableName());
			Assert.assertEquals("incorrect line number", 2, e.getLineNumber());
		}

	}

	@Test
	public void testUnknownCommandFailsParsing_2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Meh 1");
		wrapper.addLine("Echo \"hello\"");
		wrapper.addLine("Meh 2");
		wrapper.addLine("Meh 3");

		try
		{
			HulaExecutable parserResult = parser.parse(wrapper.toString());
			System.out.println(parserResult.getContent());
			Assert.fail("unknown command should result in ParserException");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			Assert.assertEquals("wrong number of errors", 3, e.getErrors().size());
			assertError(e, 0, 1, "Meh 1", "unknown.command");
			assertError(e, 1, 3, "Meh 2", "unknown.command");
			assertError(e, 2, 4, "Meh 3", "unknown.command");
		}
	}

	@Test
	public void testUnknownCommandFailsParsing_3() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("");
		wrapper.addLine("Meh 1");
		wrapper.addLine("Echo \"hello\"");
		wrapper.addLine("If true");
		wrapper.addLine("   Meh 2");
		wrapper.addLine("   Meh 3");

		try
		{
			parser.parse(wrapper.toString());
			// System.out.println(parserResult.getContent());
			Assert.fail("unknown command should result in ParserException");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			System.out.println(e.getErrors());
			Assert.assertEquals("wrong number of errors", 4, e.getErrors().size());

			assertError(e, 0, 2, "Meh 1", "unknown.command");
			assertError(e, 1, 5, "Meh 2", "unknown.command");
			assertError(e, 2, 6, "Meh 3", "unknown.command");
			assertError(e, 3, 4, "If true", "missing.end.statement");
		}
	}

	/**
	 * This test was introduced to fix a parser bug which resulted in a command model like this:
	 * 
	 * Try, Echo, OnFail, Try, Try, Echo, OnFail, Echo, OnFail
	 * 
	 * What should have been produced is:
	 * 
	 * Try, Echo, OnFail, Try, Echo, OnFail, Try, Echo, OnFail
	 * 
	 * The bug was that the CommandModel.lastIndexOf() was not reversing through the list of commands
	 * so it was returning the first index, not the last, hence the bunched-up Try's at
	 * positions 4 and 5
	 * 
	 * @throws Exception
	 */
	@Test
	public void testParseWorksForMultipleSiblingOnFails() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Echo hello");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		wrapper.addLine("Echo hello");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		wrapper.addLine("Echo hello");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		parser.parse(wrapper.toString());

	}
}
