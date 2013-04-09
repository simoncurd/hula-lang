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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.hula.lang.HulaConstants;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.parser.util.ParserUtil;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Loop command will iterate through a list of items. <br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Loop through a list of names<br/>
 * <pre>
 * Loop $nameList as $name
 *    Echo "name is $name
 * End
 * </pre>
 * 
 */
public class Loop extends AbstractCommand
{
	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{

		// run the loop command to get the iterator
		super.updateBeanShellScript(script);

		String returnValue = ParserUtil.getReturnParameterFromCommandLine(commandLine);

		script.add("Iterator " + instanceId + "iter = " + instanceId + ".getIterator(" + HulaConstants.runtimeConnector + ");");

		script.add("while (" + instanceId + "iter.hasNext())");
		script.add("{");
		script.add(returnValue + " = " + instanceId + "iter.next();");

	}

	public void updateCommandModel(CommandModel cm)
	{
		cm.addCommand(this);
		cm.startNesting(this);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getIterator(RuntimeConnector runtimeContext)
	{
		Object obj = getVariableValue("default", runtimeContext);

		if (obj instanceof Iterator)
		{
			return (Iterator) obj;
		}

		Collection col = (Collection) obj;
		return col.iterator();
	}

	private static String[] mandatory = { "default" };

	public String[] getMandatoryParameters()
	{
		return mandatory;
	}

	public void validate(List<ParseError> errors)
	{
		String loopReference = signatureParameters.get("default");
		if (!loopReference.startsWith("$"))
		{
			errors.add(new ParseError("Parameter $0 is not a variable reference", lineNumber, commandLine, new String[] { loopReference }, null));
		}

	}
}
