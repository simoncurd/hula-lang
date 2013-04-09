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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The ListContains command checks through a list of objects for a match
 * to a value or reference. <br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Check a list of names called namelist if the name "Jeff" appears. Put the result
 * (a {@link Boolean}) in a variable called present.<br/> 
 * <code>ListContains $namelist, value="Jeff" as present</code>
 * <br/><br/>
 * 
 * Check a list of objects called personlist if a person with name "Jeff" appears.
 * Put the result (a {@link Boolean}) in a variable called present. This assumes
 * all objects have an attribute called name. <br/>
 * <code>ListContains $personlist, path=name, value="Jeff" as present</code>
 * <br/><br/>
 * 
 * Check a list of objects called addresslist if the person referenced by the
 * address has name "Jeff". 
 * Put the result (a {@link Boolean}) in a variable called present. This assumes
 * all address objects has a reference to a person object, which have an 
 * attribute called name. <br/>
 * <code>ListContains $addresslist, path=person.name, value="Jeff" as present</code>
 */
@RequiresReturnParam
@RequiresParams(names = { "default", "value" })
public class ListContains extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		List list = (List) this.getVariableValue("default", connector);
		String path = this.getVariableValueAsString("path", connector);
		Object value = this.getVariableValue("value", connector);

		boolean found = false;

		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Object listItem = iter.next();

			Object compare = listItem;
			if (path != null)
			{
				try
				{
					compare = PropertyUtils.getProperty(listItem, path);
				}
				catch (Exception e)
				{
					throw new HulaRuntimeException("error.getting.path", "error getting path [" + path + "] for object [" + listItem + "]", e);
				}
			}

			if (compare.equals(value))
			{
				found = true;
				break;
			}

		}

		connector.setVariable(getReturnParameter(), found);

	}

}
