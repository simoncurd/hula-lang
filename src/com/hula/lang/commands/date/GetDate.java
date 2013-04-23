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
package com.hula.lang.commands.date;

import java.util.Date;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The GetDate command provides a Date for right now. <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Get the date and time for right now. <br/>
 * <code>GetDate as now</code><br/>
 * <br/>
 * 
 */
@RequiresReturnParam
public class GetDate extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		connector.setVariable(getReturnParameter(), new Date());
	}

}
