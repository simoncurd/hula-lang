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
package com.hula.lang.commands.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hula.lang.HulaConstants;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The GetVariableNames command is used to retrieve a list of variable
 * names. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Get all variable names<br/>
 * <code>GetVariableNames as varnames<br/><br/>
 * 
 * Get all variable names beginning with h_</br>
 * <code>GetVariableNames prefix="h_" as varnames<br/><br/>
 * 
 */
@RequiresReturnParam
public class GetVariableNames extends AbstractCommand
{
	private static String[] exclusions = new String[] { HulaConstants.beanShellId, HulaConstants.hulaContext, HulaConstants.lineNumber, HulaConstants.runtimeConnector };
	private static List<String> exclusionList = Arrays.asList(exclusions);
	
	@Override
	public void execute(RuntimeConnector connector)
	{
		List list = new ArrayList();

		connector.setVariable(getReturnParameter(), list);

		String prefix = getVariableValueAsString("prefix", connector);
		if (prefix == null)
		{
			prefix = "";
		}

		for (String key : connector.getVariables().keySet())
		{
			if (key.startsWith(HulaConstants.commandPrefix) || exclusionList.contains(key))
			{
				continue;
			}
			if (key.startsWith(prefix))
			{
				list.add(key);
			}
		}

	}

}
