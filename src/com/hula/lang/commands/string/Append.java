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
package com.hula.lang.commands.string;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Append command concatenates two strings. <br/>
 * <br/>
 * 
 * Example Usage:</br>
 * 
 * Join the value of a String called hello (hello="Hello, ") to a string
 * value containing "World!". Result? "Hello, World!". <br/>
 * <code>Append $hello, value="World!" as helloWorld</code><br/>
 * <br/>
 * 
 * Or:<br/>
 * <code>Append "Hello, ", value=$world as helloWorld</code><br/>
 * <br/>
 * 
 * Or, joining a first name and last name:<br/>
 * <code>Append $firstName, value=" " as $name</code><br/>
 * <code>Append $name, value=$lastName as $name</code><br/>
 */
@RequiresReturnParam
@RequiresParams(names = { "default", "value" })
public class Append extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		String target = getVariableValueAsString("default", connector);
		String value = getVariableValueAsString("value", connector);

		if (target == null)
		{
			target = "";
		}

		if (value == null)
		{
			value = "";
		}
		connector.setVariable(getReturnParameter(), target + value);

	}

}
