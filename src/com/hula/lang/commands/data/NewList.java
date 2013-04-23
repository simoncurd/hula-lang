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

import java.util.ArrayList;
import java.util.List;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The NewList command creates a list variable, optionally populated with the contents
 * of a CSV-based String. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Create an empty list called namelist.<br/>
 * <code>NewList as namelist</code> <br/>
 * <br/>
 * 
 * Create a list called namelist populated from a CSV-based list of names.<br/>
 * <code>NewList csv="Jeff,Bill,John" as namelist</code>
 * 
 */
@RequiresReturnParam
public class NewList extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		// create the list & store with the appropriate name
		List list = new ArrayList();

		connector.setVariable(getReturnParameter(), list);

		String csv = getVariableValueAsString("csv", connector);
		if (csv != null)
		{
			// if we only have one item in the CSV
			if (csv.indexOf(",") == -1)
			{
				list.add(csv.trim());
			}

			// handle multiple items
			else
			{

				// we're doing this as a list
				String[] items = csv.split(",");

				for (int i = 0; i != items.length; i++)
				{
					list.add(items[i].trim());
				}
			}
		}
	}

}
