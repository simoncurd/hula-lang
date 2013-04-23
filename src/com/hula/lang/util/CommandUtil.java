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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hula.lang.runtime.RuntimeConnector;

/**
 * A utility class with helper methods for working with variables
 */
public class CommandUtil
{
	// regular expression for identifying references in a string
	private static Pattern referencePattern = Pattern.compile("\\$([a-z\\._A-Z0-9]|\\[[0-9]\\])*");

	/**
	 * Evaluates whether a name is a variable reference
	 * 
	 * @param name the name to evaluate
	 * @return boolean indicating whether this is a variable reference
	 */
	public static boolean isVariableReference(String name)
	{
		return name != null && name.startsWith("$") && name.indexOf(' ') == -1;
	}

	/**
	 * Given a string value, this checks if it contains any references and replaces
	 * them with their variable values
	 * 
	 * @param string The string to check for references within
	 * @param connector The current runtime connector
	 * @return A string with any references replaced
	 */
	public static String replaceReferences(String string, RuntimeConnector connector)
	{
		if (string != null && string.indexOf('$') != -1)
		{
			Matcher m = referencePattern.matcher(string);
			StringBuffer sb = new StringBuffer();

			// loop through and create a new String with the replacements
			while (m.find())
			{
				String reference = string.substring(m.start(), m.end());

				Object value = getReferencedVariableValue(reference, connector);

				if (value != null)
				{
					// fix dollars
					value = Matcher.quoteReplacement(value.toString());
					m.appendReplacement(sb, value.toString());
				}
				else
				{
					m.appendReplacement(sb, "");
				}
			}
			m.appendTail(sb);
			string = sb.toString();
		}
		return string;
	}

	/**
	 * Get the value of the referenced variable. This handles variables referenced
	 * using a dot notation path, such as $person.address.streetName.
	 * 
	 * @param reference The reference to the variable
	 * @param connector The {@link RuntimeConnector}
	 * @return The value of the variable
	 */
	public static Object getReferencedVariableValue(String reference, RuntimeConnector connector)
	{
		// strip off the dollar notation
		reference = reference.substring(1);

		if (DotNotationUtil.isPath(reference))
		{
			// first path item will be context parameter identifier
			String contextId = DotNotationUtil.getFirstItem(reference);
			Object object = connector.getVariable(contextId);

			// further path items are navigating object graph of records
			return DotNotationUtil.getProperty(reference, object, 1);
		}
		else
		{
			// return the object
			return connector.getVariable(reference);
		}

	}
}
