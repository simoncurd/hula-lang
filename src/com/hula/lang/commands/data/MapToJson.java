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
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The MapToJson command converts the contents of a {@link Map} into a Json string.  
 * This command does the opposite of the {@link JsonToMap} command. <br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Converts a Map variable called mapObject into a Json string called jsonString.<br/>
 * <code>MapToJson $mapObject as jsonString</code>
 */
@RequiresReturnParam
@RequiresParams(names = { "default" })
public class MapToJson extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		Object map = getVariableValue("default", connector);

		Gson gson = new Gson();

		String json = gson.toJson(map);

		connector.setVariable(getReturnParameter(), json);

	}

}
