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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.util.ValidationUtil;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The Sort command sorts a list of objects. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Sort a list of names in ascending order. <br/>
 * <code>Sort $namelist, type=Alpha</code><br/>
 * <br/>
 * 
 * Sort a list of names in descending order. <br/>
 * <code>Sort $namelist, type=Alpha, order=desc</code><br/>
 * <br/>
 * 
 * Sort a list of person objects on the name attribute.<br/>
 * <code>Sort $personlist, type=Alpha, property=name</code><br/>
 * <br/>
 * 
 * Sort a list of person objects on the age attribute.<br/>
 * <code>Sort $personlist, type=Numeric, property=age</code><br/>
 * <br/>
 */
@NoReturnParam
@RequiresParams(names = { "default", "type" })
public class Sort extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		List list = (List) getVariableValue("default", connector);
		String propertyName = getVariableValueAsString("property", connector);
		String sortType = getVariableValueAsString("type", connector);
		String order = getVariableValueAsString("order", connector);

		Collections.sort(list, new HulaComparator(sortType, propertyName));

		if (order != null && order.equals("desc"))
		{
			Collections.reverse(list);
		}
	}

	public void validate(List<ParseError> errors)
	{
		ValidationUtil.assertParameterValueOneOf(this, errors, "type", "Alpha,Numeric");

		if (signatureParameters.containsKey("order"))
		{
			ValidationUtil.assertParameterValueOneOf(this, errors, "order", "asc,desc");
		}

	}

	class HulaComparator implements Comparator<Object>
	{
		String type = null;
		String property = null;

		public HulaComparator(String type, String property)
		{
			this.type = type;
			this.property = property;
		}

		@Override
		public int compare(Object left, Object right)
		{
			try
			{
				if (property != null)
				{
					left = PropertyUtils.getProperty(left, property);
					right = PropertyUtils.getProperty(right, property);
				}

				// if both null, return 0
				if (left == null && right == null)
				{
					return 0;
				}

				else
				{

					if (type.equals("Numeric"))
					{
						BigDecimal leftnum = new BigDecimal(left.toString());
						BigDecimal rightnum = new BigDecimal(right.toString());
						return leftnum.compareTo(rightnum);
					}
					else if (type.equals("Alpha"))
					{
						return left.toString().compareTo(right.toString());
					}

				}

				return 0;
			}
			catch (Throwable t)
			{
				throw new HulaRuntimeException("error.sorting.list", "error sorting list", t);
			}
		}

	}

}
