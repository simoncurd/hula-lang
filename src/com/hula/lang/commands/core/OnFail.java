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

import com.hula.lang.HulaConstants;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.Command;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.parser.util.ParserUtil;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;
import com.hula.lang.runtime.util.ErrorBridge;

/**
 * The OnFail command is used to handle failures elsewhere in a Hula script.
 * For commands that explicitly fail, a following OnFail block is mandatory. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * For a command which can fail (TrySomething). <br/>
 * 
 * <pre>
 * TrySomething
 * OnFail
 *    # handle the failure
 * End
 * </pre>
 * 
 * OnFail can wrap the exception in an {@link com.hula.lang.runtime.HulaError} object. <br/>
 * 
 * <pre>
 * TrySomething
 * OnFail as e
 *    Echo "something failed, the message was $e.message", type=error, cause=e
 * End
 * </pre>
 */
public class OnFail extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{
		String exceptionClassName = HulaRuntimeException.class.getName();
		script.add("}");
		script.add("catch ( e )");
		script.add("{");
		script.add("if (e instanceof " + exceptionClassName + ")");
		script.add("{");
		script.add(HulaConstants.runtimeConnector + ".getHulaContext().setLastException((" + exceptionClassName + ")e);");
		script.add("}");

		// if a return value is specified, store the exception in that return value
		String returnValue = ParserUtil.getReturnParameterFromCommandLine(commandLine);
		if (returnValue != null)
		{
			String errorBridgeClassName = ErrorBridge.class.getName();
			script.add(errorBridgeClassName + " " + instanceId + " = new " + errorBridgeClassName + "();");
			script.add(instanceId + ".execute(" + HulaConstants.runtimeConnector + ", e, \"" + returnValue + "\");");
			script.add("");

		}

	}

	public void updateCommandModel(CommandModel cm)
	{
		// create a Try Command
		Command c = new Try();
		c.setCommandLine("Try-dynamic");

		// find the previous instance of an OnFail Command at this nesting
		// level in the CommandModel
		int onFailPos = cm.lastIndexOf(OnFail.class);

		if (onFailPos == -1)
		{
			// insert Try at beginning
			cm.insertCommand(c, 0);
		}
		else
		{
			// insert Try after the previous OnFail
			cm.insertCommand(c, onFailPos + 1);
		}

		cm.addCommand(this);
		cm.startNesting(this);
	}

}
