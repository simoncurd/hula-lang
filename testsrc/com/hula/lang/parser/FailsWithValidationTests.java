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

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.util.ScriptWrapper;

/**
 * 
 * For commands that declare they can fail (using the @FailsWith annotation),
 * the parser validates that failure handling is present in the script
 * 
 */
public class FailsWithValidationTests extends BaseHulaTestCase
{
	/**
	 * This tests that the parser validation works when the failure handlers
	 * directly follow the command which requires a failure handler
	 */
	@Test
	public void testFailHandlerValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ErrorHandlingRequired");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		parser.parse(wrapper.toString());

	}

	/**
	 * This tests that the parser validation works when the failure handlers
	 * directly follow a nested block containing a command which requires a
	 * failure handler
	 */
	@Test
	public void testFailHandlerValidation_2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Loop $items as item");
		wrapper.addLine("   ErrorHandlingRequired");
		wrapper.addLine("End");
		wrapper.addLine("OnFail");
		wrapper.addLine("End");

		parser.parse(wrapper.toString());

	}

	/**
	 * This tests that the parser validation works when the failure handlers
	 * are not present in a script containing a command which requires a
	 * failure handler
	 */
	@Test
	public void testMissingFailHandlerProducesParseError() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("ErrorHandlingRequired");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("error expected for missing error handling");
		}
		catch (HulaParserException e)
		{
			Assert.assertEquals("incorrect number of errors", 1, e.getErrors().size());
			ParseError error = e.getErrors().get(0);
			Assert.assertEquals("incorrect error code", "no.fail.handling", error.getErrorCode());
			Assert.assertEquals("incorrect command line", "ErrorHandlingRequired", error.getCommandLine());
			Assert.assertEquals("incorrect line number", 1, error.getLineNumber());
		}
	}

	/**
	 * This tests that the parser validation works when the failure handlers
	 * come before a command which requires a failure handler
	 */
	@Test
	public void testMissingFailHandlerProducesParseError_2() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("OnFail");
		wrapper.addLine("End");
		wrapper.addLine("ErrorHandlingRequired");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("error expected for missing error handling");
		}
		catch (HulaParserException e)
		{
			Assert.assertEquals("incorrect number of errors", 1, e.getErrors().size());
			ParseError error = e.getErrors().get(0);
			Assert.assertEquals("incorrect error code", "no.fail.handling", error.getErrorCode());
			Assert.assertEquals("incorrect command line", "ErrorHandlingRequired", error.getCommandLine());
			Assert.assertEquals("incorrect line number", 3, error.getLineNumber());
		}
	}

	/**
	 * This tests that the parser validation works when a command
	 * which requires failure handlers is invoked within an existing
	 * failure handling block
	 */
	@Test
	public void testMissingFailHandlerProducesParseError_3() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("Echo hi");
		wrapper.addLine("OnFail");
		wrapper.addLine("ErrorHandlingRequired");
		wrapper.addLine("End");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("error expected for missing error handling");
		}
		catch (HulaParserException e)
		{
			Assert.assertEquals("incorrect number of errors", 1, e.getErrors().size());
			ParseError error = e.getErrors().get(0);
			Assert.assertEquals("incorrect error code", "no.fail.handling", error.getErrorCode());
			Assert.assertEquals("incorrect command line", "ErrorHandlingRequired", error.getCommandLine());
			Assert.assertEquals("incorrect line number", 3, error.getLineNumber());
		}
	}

}
