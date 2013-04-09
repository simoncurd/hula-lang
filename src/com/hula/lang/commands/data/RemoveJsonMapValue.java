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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The RemoveJsonMapValue command removes a value from a Json Map. <br/><br/>
 * This command does the opposite of the {@link AddJsonMapValue} command. <br/><br/>
 * 
 * Example Usage: <br/><br/>
 * 
 * Remove the age from this Json string called jsonmap: <code>{"name":"Jeff","age":"32"}</code><br/>
 * <code>RemoveJsonMapValue $jsonmap, key=age</code><br/>
 * Result: <code>{"name":"Jeff"}</code>
 * 
 */
@NoReturnParam
@RequiresParams(names = { "default", "key" })
public class RemoveJsonMapValue extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		// get the json string

		String jsonStringId = getSignatureParameter("default");
		String jsonString = getVariableValueAsString("default", connector);

		String key = getVariableValueAsString("key", connector);

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

		// remove from map
		map.remove(key);

		String updatedJson = gson.toJson(map);

		assignVariable(jsonStringId, updatedJson, connector);
	}

}
