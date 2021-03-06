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

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The AddJsonMapValue command is used to add a key-value mapping into a
 * Json Map. <br/>
 * <br/>
 * This command does the opposite of the {@link RemoveJsonMapValue} command. <br/>
 * <br/>
 * 
 * For example, if a variable called jsonmap contains the following map<br/>
 * <br/>
 * 
 * <code>{"name":"jeff"}</code> <br/>
 * <br/>
 * 
 * Then this command could be used as follows: <code>AddJsonMapValue $jsonmap, key=age, value=32</code><br/>
 * <br/>
 * 
 * The resulting map would be <code>{"name":"jeff","age":"32"}</code>
 * 
 */
@NoReturnParam
@RequiresParams(names = { "default", "key", "value" })
public class AddJsonMapValue extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		// get the json string

		// get the variable reference ($trigger.data)
		String jsonStringId = getSignatureParameter("default");

		// get the value referenced by the variable - a Json string
		String jsonString = getVariableValueAsString("default", connector);

		String key = getVariableValueAsString("key", connector);
		String value = getVariableValueAsString("value", connector);

		// convert to Map
		Map map = null;
		Gson gson = new Gson();
		try
		{
			map = gson.fromJson(jsonString, Map.class);
		}
		catch (JsonSyntaxException e)
		{
			throw new HulaRuntimeException("invalid.json.value", "value [" + jsonString + "] is invalid as a json Map", e);
		}

		if (map == null)
		{
			map = new HashMap();
		}

		// put into map
		map.put(key, value);

		String updatedJson = gson.toJson(map);

		assignVariable(jsonStringId, updatedJson, connector);
	}

}
