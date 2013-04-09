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


import java.util.Map;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;


/**
 * The AddToMap command adds an object to a map. <br/><br/>
 * 
 * For example, the following command adds the address variable to a
 * map called p.<br/><br/>
 * 
 * <code>AddToMap $person, key="address", value=$address</code>
 */
@NoReturnParam
@RequiresParams(names = { "default", "key", "value" })
public class AddToMap extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		Map map = (Map) getVariableValue("default", connector);
		Object key = getVariableValue("key", connector);
		Object value = getVariableValue("value", connector);

		map.put(key, value);
	}

}
