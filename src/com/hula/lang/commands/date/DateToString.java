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
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;
import com.hula.lang.util.DateUtil;

/**
 * The DateToString command convert a {@link java.util.Date} into a String, using an optional format. <br/>
 * The default format is "<code>yyyy-MM-dd HH:mm:ss</code>"<br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Convert a {@link java.util.Date} into a String containing "<code>2013-03-06 15:55:00</code>". <br/>
 * <code>DateToString $date as dateString</code><br/><br/>
 * 
 * Convert a {@link java.util.Date} into a String containing "<code>2013/03/06</code>". <br/>
 * <code>DateToString $date, format="yyyy/MM/dd" as dateString</code><br/><br/>
 */
@RequiresReturnParam
@RequiresParams(names = { "default" })
public class DateToString extends AbstractCommand
{

	
	@Override
	public void execute(RuntimeConnector connector)
	{
		Date date = (Date) getVariableValue("default", connector);
		String format = getVariableValueAsString("format", connector);

		// our generic date
		if (StringUtils.isEmpty(format))
		{
			format = DateUtil.DATE_FORMAT;
		}

		try
		{
			String result = DateUtil.toString(date, format);
			connector.setVariable(getReturnParameter(), result);
		}
		catch (Throwable t)
		{
			throw new HulaRuntimeException("parse.error", t.getMessage(), t);
		}

	}


}
