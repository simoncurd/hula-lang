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
package com.hula.lang.util;

import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A utility class with helper methods for working with Dates.
 */
public class DateUtil
{

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static
	{
		DateTimeZone.setDefault(DateTimeZone.UTC);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Convert a Date into a String
	 * 
	 * @param date The date to convert
	 * @param format The format of the Date
	 * @return A string containing the datre
	 */
	public static String toString(Date date, String format)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		return fmt.print(date.getTime());
	}

	/**
	 * Convert a String into a Date
	 * 
	 * @param string The string to convert
	 * @param format The format of the String
	 * @return A Date
	 */
	public static Date toDate(String string, String format)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
		DateTime dt = fmt.parseDateTime(string);
		return dt.toDate();
	}

}
