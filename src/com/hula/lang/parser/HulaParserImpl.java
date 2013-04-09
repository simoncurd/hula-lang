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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.Parser;

import com.hula.lang.HulaConstants;
import com.hula.lang.commands.core.OnFail;
import com.hula.lang.commands.core.Try;
import com.hula.lang.factory.CommandFactory;
import com.hula.lang.factory.exception.CommandFactoryException;
import com.hula.lang.factory.exception.ErrorCreatingCommandException;
import com.hula.lang.factory.exception.UnknownCommandException;
import com.hula.lang.model.Command;
import com.hula.lang.model.FailsWith;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.parser.util.BeanShellUtil;
import com.hula.lang.parser.util.ParserUtil;

/**
 * A BeanShell implementation of the HulaParser, responsible for parsing a
 * Hula script to produce an executable BeanShell script
 */
public class HulaParserImpl implements HulaParser
{
	private Logger logger = LoggerFactory.getLogger(HulaParserImpl.class);
	private CommandFactory commandFactory;

	public HulaParserImpl(CommandFactory commandFactory)
	{
		this.commandFactory = commandFactory;
	}

	/**
	 * Parse a Hula script contained in a string to produce an
	 * executable version
	 * 
	 * @param content A string containing the Hula script
	 * @return An executable version of the script
	 * @throws HulaParserException
	 */
	@Override
	public HulaExecutable parse(String content) throws HulaParserException
	{
		String[] commandParts = content.split("\n");
		List<String> commandLines = Arrays.asList(commandParts);
		return parse(commandLines);
	}

	/**
	 * Parses the Hula command lines to produce an executable script
	 * 
	 * @param lines The command lines of the Hula script
	 * @return An executable version of the script
	 * @throws HulaParserException
	 */
	protected HulaExecutable parse(List<String> lines) throws HulaParserException
	{
		List<ParseError> parseErrors = new ArrayList<ParseError>();

		Iterator<String> lineInterator = lines.iterator();

		int lineNumber = 0;
		int varCount = 0;

		CommandModel cm = new CommandModel();

		// loop through command lines
		while (lineInterator.hasNext())
		{
			lineNumber++;

			// grab and trim the command line
			String commandLine = lineInterator.next();
			commandLine = commandLine.trim();

			// ignore blank lines
			if (commandLine.equals(""))
			{
				continue;
			}

			boolean validate = true;

			// get the identifier for the command (the first token)
			String commandId = ParserUtil.getCommandFromCommandLine(commandLine);

			// detect comment line which does get represented as a Command object
			// but does not get validated
			if (commandLine.startsWith("#"))
			{
				commandId = "Comment";
				validate = false;
			}

			// create an instance identifier for the command in the BeanShell
			// so the command can be instantiated and executed (if necessary)
			// e.g. hcmd_validate73
			String instanceId = HulaConstants.commandPrefix + commandId + varCount++;
			instanceId = instanceId.toLowerCase();

			// create an instance of the command
			Command command = null;
			try
			{
				command = commandFactory.getCommandInstance(commandId);
			}
			catch (UnknownCommandException e)
			{
				// if the command is unknown, create a parse error and continue parsing
				// the script
				parseErrors.add(new ParseError("unknown.command", lineNumber, commandLine, e));
				continue;
			}
			catch (ErrorCreatingCommandException e)
			{
				// if the command cannot be created, create a parse error and continue parsing
				// the script
				parseErrors.add(new ParseError("error.creating.command", lineNumber, commandLine, e));
				continue;
			}
			catch (CommandFactoryException e)
			{
				// if there is a general error creating the command, create a parse error and continue parsing
				// the script
				parseErrors.add(new ParseError("unknown.error", lineNumber, commandLine, e));
				continue;
			}

			Map<String, String> parameters = ParserUtil.getParametersFromCommandLine(commandLine);

			// configure the command using details of the command line
			command.setCommandLine(commandLine);
			command.setInstanceId(instanceId);
			command.setLineNumber(lineNumber);
			command.setSignatureParameters(parameters);
			try
			{
				// delegate responsibility for updating the command model to the
				// command itself. This allows it to insert itself intelligently
				// into the model
				command.updateCommandModel(cm);
			}
			catch (ParseError pe)
			{
				parseErrors.add(pe);
				continue;
			}

			// if we need to validate this command
			if (validate)
			{
				// validate whether any parameters are missing from the command
				RequiresParams rpm = command.getClass().getAnnotation(RequiresParams.class);
				if (rpm != null && rpm.names() != null)
				{

					for (String mandatoryParamName : rpm.names())
					{
						if (!parameters.keySet().contains(mandatoryParamName))
						{
							parseErrors.add(new ParseError("missing.mandatory.parameter", lineNumber, commandLine, new String[] { mandatoryParamName }, null));
						}
					}
				}

				String returnParameter = ParserUtil.getReturnParameterFromCommandLine(commandLine);

				// validate if a return parameter is required
				RequiresReturnParam rrp = command.getClass().getAnnotation(RequiresReturnParam.class);
				if (rrp != null && StringUtils.isEmpty(returnParameter))
				{
					parseErrors.add(new ParseError("missing.mandatory.return.parameter", lineNumber, commandLine, null));
				}

				// validate if a return parameter is incorrectly specified
				NoReturnParam nrp = command.getClass().getAnnotation(NoReturnParam.class);
				if (nrp != null && !StringUtils.isEmpty(returnParameter))
				{
					parseErrors.add(new ParseError("return.parameter.not.supported", lineNumber, commandLine, null));
				}

				// allow the command to run any internal validation of the configuration
				// of the command line it was instantiated from
				command.validate(parseErrors);
			}
		}

		// if the CommandModel is in a nesting state, report that an End statement
		// is missing
		if (cm.isNesting())
		{
			Command openCommand = cm.getOpenCommand();
			if (openCommand != null)
			{
				parseErrors.add(new ParseError("missing.end.statement", openCommand.getLineNumber(), openCommand.getCommandLine(), null));
			}
			else
			{
				parseErrors.add(new ParseError("missing.end.statement", -1, null, null));
			}
		}

		// validate that failures are handled (to the best of our ability)
		validateFailureHandling(cm.getCommands(), false, parseErrors);

		HulaExecutable executable = new HulaExecutable();

		// if no other parse errors have occured, convert the Command Model to BeanShell
		if (parseErrors.isEmpty())
		{
			BeanShellScript script = new BeanShellScript();
			convertToBeanShell(cm.getCommands(), 0, script);
			executable.setContent(script.toString());

			// validate the resulting BeanShell
			ParseError result = validateBeanShell(executable.getContent());
			if (result != null)
			{
				parseErrors.add(result);
			}
		}

		// throw an exception if any parsing errors were detected
		if (!parseErrors.isEmpty())
		{
			throw new HulaParserException("errors parsing hula script", parseErrors);
		}

		return executable;
	}

	/**
	 * Validate the BeanShell script
	 * 
	 * @param content The BeanShell script to validate
	 * @return A ParseError detailing an issue in the script
	 * @throws HulaParserException
	 */
	private ParseError validateBeanShell(String content) throws HulaParserException
	{
		Parser parser = null;
		boolean printNodes = false;
		try
		{
			// it's ugly but this is how BeanShell gets validated
			ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
			parser = new Parser(in);
			parser.setRetainComments(true);
			while (!parser.Line())
			{
				if (printNodes)
				{
					logger.info("{}", parser.popNode());
				}
			}
		}
		catch (Throwable e)
		{
			// Errors parsing the BeanShell should not happen. If they do, it's likely
			// we have an error parsing the Hula into BeanShell and allowed invalid java
			// to be generated.
			//
			// The user cannot resolve this, we can only tell them "an
			// error occured parsing your script, not your fault, we're looking at it"
			//
			// To work out what went wrong, we need the complete Hula script, plus the
			// bean shell, plus the exception packaged up to be fixed

			String msg = e.getMessage();

			StringBuilder errorMsg = new StringBuilder();

			errorMsg.append("\n");
			errorMsg.append("--------------------------------------------\n");
			errorMsg.append("Error parsing BeanShell [" + msg + "]\n");
			errorMsg.append("Generated Script:\n\n");
			errorMsg.append(content);

			// if the BeanShell message mentions a line number, that's good context for
			// investigating what went wrong
			if (BeanShellUtil.isLineNumberMentioned(msg))
			{
				// extract the line number
				int lineNumber = BeanShellUtil.getLineNumber(msg);

				// attempt to report a code snippet
				List<String> lines = Arrays.asList(content.split("\n"));
				String codeSnippet = BeanShellUtil.extractCodeSnippet(lineNumber, lines);

				errorMsg.append("\nOffending code snippet at line " + lineNumber + ":\n\n");
				errorMsg.append(codeSnippet);

			}

			errorMsg.append("\n--------------------------------------------\n");
			logger.error(errorMsg.toString());
			return new ParseError("unknown.parse.error", -1, null, null);
		}
		return null;
	}

	/**
	 * Recursively checks for instances where no failure handling is provided
	 * for commands which can fail
	 * 
	 * @param commands The list of commands to check
	 * @param failureHandling a flag indicating if the command list is nested under a Try Command
	 * @param parseErrors the list to add parse errors to
	 */
	private void validateFailureHandling(List<Command> commands, boolean failureHandling, List<ParseError> parseErrors)
	{
		// loop through Command list
		for (Command c : commands)
		{
			if (c instanceof Try)
			{
				// switch on failure handling
				failureHandling = true;
				continue;
			}
			else if (c instanceof OnFail)
			{
				// an OnFail when we're not handling failure will result in a BeanShell
				// error (catch without try), so flag a parse error. This shouldn't
				// happen because we're automatically creating the Try, but this is a
				// good way to validate the integrity of our own Command modelling
				if (!failureHandling)
				{
					ParseError err = new ParseError("internal.parser.error", c.getLineNumber(), c.getCommandLine(), null);
					parseErrors.add(err);
					logger.error("internal parser error for script [" + commands + "]");
				}

				// switch off failure handling once we find an OnFail (if it was on)
				failureHandling = false;
			}

			// check if the Command type declares it can fail
			// if we're not handling failure for a Command which can fail, raise
			// a parse error
			FailsWith fw = c.getClass().getAnnotation(FailsWith.class);
			if (fw != null && !failureHandling)
			{
				ParseError err = new ParseError("no.fail.handling", c.getLineNumber(), c.getCommandLine(), null);
				parseErrors.add(err);
			}

			// proceed down the Command structure
			if (!c.getCommands().isEmpty())
			{
				validateFailureHandling(c.getCommands(), failureHandling, parseErrors);
			}
		}

	}

	/**
	 * Recurse down the Command list and generate a BeanShell script
	 * 
	 * @param commands The list of commands to generate BeanShell for
	 * @param indent the current indentation level
	 * @param script The container for the BeanShellScript
	 */
	private void convertToBeanShell(List<Command> commands, int indent, BeanShellScript script)
	{
		for (Command command : commands)
		{
			command.updateBeanShellScript(script);

			// if this Command has child Commands
			if (!command.getCommands().isEmpty())
			{
				convertToBeanShell(command.getCommands(), indent + 1, script);
			}
		}
	}
}
