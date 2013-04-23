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
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The JsonToMap command converts the contents of a Json string into a {@link Map} object.
 * This command does the opposite of the {@link MapToJson} command. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Converts a Json String contained in a variable called
 * jsonString into a Map variable called mapObject.<br/>
 * <code>JsonToMap $jsonString as mapObject</code>
 */
@RequiresReturnParam
@RequiresParams(names = { "default" })
public class JsonToMap extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		// get the json string
		String jsonString = getVariableValueAsString("default", connector);

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

		// save as variable
		connector.setVariable(getReturnParameter(), map);

	}

}
