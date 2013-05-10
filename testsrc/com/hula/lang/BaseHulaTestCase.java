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
package com.hula.lang;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;

import bsh.Interpreter;
import bsh.NameSpace;
import bsh.ParseException;
import bsh.Parser;

import com.hula.lang.factory.CommandFactory;
import com.hula.lang.factory.CommandFactoryImpl;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.HulaParser;
import com.hula.lang.parser.HulaParserImpl;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.reader.MockScriptReader;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.runtime.HulaPlayer;
import com.hula.lang.runtime.HulaPlayerImpl;

public abstract class BaseHulaTestCase
{
	protected HulaParser parser = null;
	protected HulaPlayer player = null;
	protected MockScriptReader scriptReader = null;

	@Before
	public void setUp() throws Exception
	{
		PropertyConfigurator.configure("conf/log4j.properties");
		
		CommandFactory commandFactory = new CommandFactoryImpl();

		commandFactory.loadCommands("conf/lang.commands.properties");
		commandFactory.loadCommands("test/conf/test.commands.properties");
		parser = new HulaParserImpl(commandFactory);
		scriptReader = new MockScriptReader();
		player = new HulaPlayerImpl(scriptReader);
	}

	public HulaExecutable parseAndAssert(String content) throws Exception
	{
		HulaExecutable parserResult = parser.parse(content);

		assertValidBeanShell(parserResult.getContent(), false);
		return parserResult;
	}

	public void evaluateBeanShell(HulaExecutable result, HulaContext hctx) throws Exception
	{
		// HulaPlayerImpl bsp = new HulaPlayerImpl();
		scriptReader.setContent("testscript", result.getContent());
		player.run("testscript", hctx);
	}

	public void assertValidBeanShell(String script, boolean printNodes) throws Exception
	{
		// split the script into lines
		String[] lines = script.split("\n");
		Parser parser = null;
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(script.getBytes());
			parser = new Parser(in);
			parser.setRetainComments(true);
			while (!parser.Line())
			{
				// print the node
				if (printNodes)
				{
					System.out.println(parser.popNode());
				}
			}
		}
		catch (ParseException e)
		{
			String msg = e.getMessage();

			// show the error message
			System.out.println(msg);

			// need to hack the line number out of the error message!
			if (msg.startsWith("Parse error at line"))
			{
				// extract & convert the line number
				String lineStr = msg.substring(msg.indexOf("line ") + 5, msg.indexOf(","));
				int lineNumber = Integer.parseInt(lineStr);

				// get x lines either side of the error
				int from = lineNumber - 3;
				int to = lineNumber + 3;
				if (from < 0)
				{
					from = 0;
				}
				if (to > lines.length)
				{
					to = lines.length;
				}

				// print the range of lines
				for (int i = from; i != to; i++)
				{
					System.out.println((i + 1) + ": " + lines[i]);
				}
			}

			throw new RuntimeException("Error parsing bean shell script", e);
		}
	}

	// if you need the variables created in the interpreter, this will extract them
	public Map<String, Object> getResults(Interpreter i) throws Exception
	{
		NameSpace ns = i.getNameSpace();
		String[] variableNames = ns.getVariableNames();
		Map<String, Object> results = new HashMap<String, Object>(variableNames.length);
		for (String key : variableNames)
		{
			Object value = ns.getVariable(key);
			results.put(key, value);
			System.out.println("out: [" + key + "] [" + value + "]");
		}
		return results;
	}

	protected void assertError(HulaParserException e, int errorNumber, int lineNumber, String commandLine, String errorCode)
	{
		List<ParseError> errors = e.getErrors();
		ParseError error = errors.get(errorNumber);
		Assert.assertEquals("missing line number", lineNumber, error.getLineNumber());
		Assert.assertEquals("missing command line", commandLine, error.getCommandLine());
		Assert.assertEquals("missing error code", errorCode, error.getErrorCode());
	}

}
