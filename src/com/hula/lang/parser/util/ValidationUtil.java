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
package com.hula.lang.parser.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.conditional.ExpressionUtil;
import com.hula.lang.parser.exception.ParseError;

/**
 * Utility class providing helper methods for validating Hula scripts
 */
public class ValidationUtil
{
	/**
	 * Assert that no parameters are specified for the command
	 * 
	 * @param command The command to inspect
	 * @param errors A list of error to append to
	 */
	public static void assertNoParameters(AbstractCommand command, List<ParseError> errors)
	{
		Map<String, String> parameters = ParserUtil.getParametersFromCommandLine(command.getCommandLine());
		if (parameters.size() != 0)
		{
			errors.add(new ParseError("No valid parameters for command", command.getLineNumber(), command.getCommandLine(), null));
		}
	}

	/**
	 * Assert that the signature of this command line is BeanShell
	 * 
	 * @param command The command to inspect
	 * @param errors A list of error to append to
	 */
	public static void assertBeanShellSignature(AbstractCommand command, List<ParseError> errors)
	{
		String signature = ParserUtil.stripCommandDeclaration(command.getCommandLine());

		if (StringUtils.isEmpty(signature))
		{
			errors.add(new ParseError("Test must be specified", command.getLineNumber(), command.getCommandLine(), null));

		}
		else
		{
			try
			{
				ExpressionUtil.validate(signature);
			}
			catch (Throwable e)
			{
				errors.add(new ParseError("Test cannot be evaluated", command.getLineNumber(), command.getCommandLine(), e));
			}
		}

	}

	/**
	 * Assert that at least one parameter is specified for the command
	 * 
	 * @param command The command to inspect
	 * @param errors A list of error to append to
	 */
	public static void assertAtLeastOneParameter(AbstractCommand command, List<ParseError> errors)
	{
		Map<String, String> parameters = ParserUtil.getParametersFromCommandLine(command.getCommandLine());
		if (parameters.size() == 0)
		{
			errors.add(new ParseError("At least one key-value pair is required", command.getLineNumber(), command.getCommandLine(), null));
		}
	}

	/**
	 * Assert that a parameter of a command contains a value which matches
	 * one of a pre-defined set of values
	 * 
	 * @param command The command to inspect
	 * @param errors A list of error to append to
	 * @param key The parameter key
	 * @param expectedCSV The possible values as a comma-separated string
	 */
	public static void assertParameterValueOneOf(AbstractCommand command, List<ParseError> errors, String key, String expectedCSV)
	{
		Map<String, String> parameters = ParserUtil.getParametersFromCommandLine(command.getCommandLine());

		String[] values = expectedCSV.split(",");

		String value = parameters.get(key);
		if (value != null)
		{
			// compare to valid values
			for (String valid : values)
			{
				if (value.equals(valid))
				{
					return;
				}
			}
		}
		errors.add(new ParseError("Invalid parameter value $0, expected one of ($1)", command.getLineNumber(), command.getCommandLine(), new String[] { value, expectedCSV }, null));
	}
}
