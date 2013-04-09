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

import org.apache.commons.lang.StringUtils;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;
import com.hula.lang.util.DateUtil;

/**
 * The StringToDate command convert a string into a date, using an optional format. <br/>
 * The default format is "<code>yyyy-MM-dd HH:mm:ss</code>"<br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Convert a String containing "<code>2013-03-06 15:55:00</code>" into a Date object. <br/>
 * <code>StringToDate $datestring as startDate</code><br/>
 * <br/>
 * 
 * Convert a String containing "<code>2013/03/06</code>" into a Date object. <br/>
 * <code>StringToDate $datestring, format="yyyy/MM/dd" as startDate</code><br/>
 * <br/>
 */
public class StringToDate extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		String dateString = getVariableValueAsString("default", connector);
		String format = getVariableValueAsString("format", connector);

		// our generic date
		if (StringUtils.isEmpty(format))
		{
			format = DateUtil.DATE_FORMAT;
		}

		try
		{
			Date date = DateUtil.toDate(dateString, format);
			connector.setVariable(getReturnParameter(), date);
		}
		catch (Throwable t)
		{
			throw new HulaRuntimeException("parse.error", t.getMessage(), t);
		}
	}

}
