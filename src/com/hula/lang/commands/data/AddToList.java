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

import java.util.List;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The AddToList command adds an object to a list. <br/>
 * <br/>
 * 
 * For example, the following command adds the value of the name variable to a
 * list variable called namelist.<br/>
 * <br/>
 * 
 * <code>AddToList $namelist, value=$name</code>
 */
@NoReturnParam
@RequiresParams(names = { "default", "value" })
public class AddToList extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		List list = (List) getVariableValue("default", connector);
		Object value = getVariableValue("value", connector);

		list.add(value);

	}

}
