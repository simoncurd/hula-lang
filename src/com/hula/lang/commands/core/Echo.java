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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.util.CommandUtil;

/**
 * The Echo command writes a message to the log. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Write an informational message<br/>
 * <code>Echo "this is a message"</code><br/>
 * <br/>
 * 
 * Write an informational message containing other variables<br/>
 * <code>Echo "my name is $name, nice to meet you"</code><br/>
 * <br/>
 * 
 * Write an informational message containing other variables<br/>
 * <code>Echo "my name is $person.name, nice to meet you"</code><br/>
 * <br/>
 * 
 * Write an error message<br/>
 * <code>Echo "something bad happened", type=error</code><br/>
 * <br/>
 * 
 * Write an error message as well as a stacktrace<br/>
 * <code>Echo "something bad happened", type=error, cause=ex</code><br/>
 * <br/>
 */
@RequiresParams(names = { "default" })
public class Echo extends AbstractCommand
{
	private Logger logger = LoggerFactory.getLogger(Echo.class);

	@Override
	public void execute(RuntimeConnector connector)
	{
		String value = getVariableValueAsString("default", connector);

		value = CommandUtil.replaceReferences(value, connector);

		String type = getVariableValueAsString("type", connector);

		if (type != null && type.equals("error"))
		{
			Throwable t = (Throwable) getVariableValue("cause", connector);
			logger.error(value, t);
		}
		else
		{
			logger.info(value);
		}

	}

}
