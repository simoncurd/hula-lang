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

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Pause command waits for a specified number of seconds. <br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Wait for 5 seconds before proceeding<br/>
 * <code>Pause 5</code><br/>
 */
@NoReturnParam
@RequiresParams(names={"default"})
public class Pause extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		String lengthStr = getVariableValueAsString("default", connector);

		int length = 1;
		try
		{
			length = new Integer(lengthStr);
		}
		catch (Throwable t)
		{
			// if we fail to parse the lengthStr, swallow it
		}

		if (length < 1000)
		{
			length *= 1000;
		}

		try
		{
			Thread.sleep(length);
		}
		catch (InterruptedException e)
		{
			// if this is interrupted, swallow it. Best Intentions
		}

	}

}
