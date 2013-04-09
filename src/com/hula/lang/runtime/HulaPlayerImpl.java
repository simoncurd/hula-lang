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
package com.hula.lang.runtime;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

import com.hula.lang.HulaConstants;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.parser.util.BeanShellUtil;
import com.hula.lang.reader.ScriptReader;
import com.hula.lang.runtime.exception.HulaPlayerException;
import com.hula.lang.runtime.exception.NestedQuitException;
import com.hula.lang.runtime.exception.UndefinedVariableException;

/**
 * Responsible for running a Hula Executable
 */
public class HulaPlayerImpl implements HulaPlayer
{
	private static String[] ignores = { HulaConstants.beanShellId, HulaConstants.runtimeConnector, HulaConstants.hulaContext };
	private static List<String> ignoreList = Arrays.asList(ignores);

	private Logger logger = LoggerFactory.getLogger(HulaPlayerImpl.class);
	private Interpreter interpreter = null;
	private ScriptReader scriptReader;

	public HulaPlayerImpl(ScriptReader scriptReader)
	{
		this.interpreter = new Interpreter();
		this.scriptReader = scriptReader;
	}

	@Override
	public void run(String scriptName, HulaContext hulaContext) throws HulaPlayerException, HulaParserException, IOException
	{
		// get the script from the script reader
		HulaExecutable executable = scriptReader.read(scriptName);

		try
		{
			// set the parameters in the context into the interpreter
			for (String key : hulaContext.getParameters().keySet())
			{
				Object value = hulaContext.getParameters().get(key);
				interpreter.set(key, value);
			}

			// the connector to be provided to the interpreter at runtime
			// connector gets a reference to the interpreter so that Commands
			// can get to variable values
			RuntimeConnector runtimeConnector = new RuntimeConnector(interpreter, scriptReader);

			// interpreter gets a reference to the connector so that
			// Commands can get to variable values
			interpreter.set(HulaConstants.runtimeConnector, runtimeConnector);

			interpreter.set(HulaConstants.hulaContext, hulaContext);

			// execute the BeanShell script
			try
			{
				interpreter.eval(executable.getContent());
			}
			catch (TargetError te)
			{
				if (te.getTarget() instanceof NestedQuitException)
				{
					// A NestedQuit is not an error state
				}
				else
				{
					// otherwise use normal error handling
					throw te;
				}
			}

			// loop through variables in the interpreter
			for (String key : interpreter.getNameSpace().getVariableNames())
			{
				Object value = interpreter.getNameSpace().getVariable(key);

				// skip variables on the ignore list
				if (ignoreList.contains(key) || key.startsWith(HulaConstants.commandPrefix))
				{
					continue;
				}
				//logger.info("mapping [{}] [{}]", key, value);

				// add the variables to the HulaContext
				hulaContext.setParameter(key, value);
			}
		}

		catch (Throwable e)
		{
			// attempt to get the line number which failed using the Hula script
			// line reference
			int lineNumber = -1;
			try
			{
				Object lineNumberObj = interpreter.getNameSpace().getVariable(HulaConstants.lineNumber);
				if (lineNumberObj != null)
				{
					lineNumber = new Integer(lineNumberObj.toString());
				}
			}
			catch (Throwable t)
			{
				// ignore errors finding line number
			}

			// attempt to get all variables
			Map<String, Object> variables = new HashMap<String, Object>();
			try
			{
				for (String key : interpreter.getNameSpace().getVariableNames())
				{
					Object value = interpreter.getNameSpace().getVariable(key);

					// skip variables on the ignore list
					if (ignoreList.contains(key) || key.startsWith(HulaConstants.commandPrefix))
					{
						continue;
					}

					variables.put(key, value);
				}
			}
			catch (Throwable t)
			{
				// ignore
			}

			// attempt to handle evaluation errors
			if (e instanceof EvalError)
			{
				if (BeanShellUtil.isUndefinedVariableMentioned(e.getMessage()))
				{
					String variableName = BeanShellUtil.getUndefinedVariableName(e.getMessage());
					throw new UndefinedVariableException(variableName, "variable.not.found", e, lineNumber, variables);
				}
			}
			// unwrap the underlying exception
			if (e instanceof TargetError)
			{
				e = ((TargetError) e).getTarget();

			}

			throw new HulaPlayerException("unexpected.error.occured", e, lineNumber, variables);
		}

	}

}
