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
package com.hula.lang.parser.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class providing helper methods for parsing Hula scripts
 */
public class ParserUtil
{
	private static final char COMMA = ',';
	private static final char QUOTE = '\"';

	/**
	 * Get the return parameter from a Hula command line
	 * 
	 * @param commandLine The command line to inspect
	 * @return the return parameter
	 */
	public static String getReturnParameterFromCommandLine(String commandLine)
	{
		String[] parts = commandLine.split(" as ");
		if (parts.length >= 2)
		{
			return parts[parts.length - 1];
		}
		return null;
	}

	/**
	 * Checks if a command line has any parameters, including return parameters
	 * 
	 * @param commandLine The command line to inspect
	 * @return boolean flag
	 */
	public static boolean hasParameters(String commandLine)
	{
		// at least one space means there's more than the command on this string
		return commandLine.trim().indexOf(' ') != -1;
	}

	/**
	 * Checks if a command line has a return parameter
	 * 
	 * @param commandLine The command line to inspect
	 * @return boolean flag
	 */
	public static boolean hasReturnParameter(String commandLine)
	{
		return commandLine.split(" as ").length > 1;
	}

	/**
	 * Strip the command declaration from the command line
	 * 
	 * @param commandLine The command line to inspect
	 * @return the stripped command line
	 */
	public static String stripCommandDeclaration(String commandLine)
	{
		if (commandLine.indexOf(' ') == -1)
		{
			return "";
		}
		return commandLine.substring(commandLine.indexOf(' ') + 1);
	}

	/**
	 * Get the parameters of a command line as a Map
	 * 
	 * @param commandLine The command line to inspect
	 * @return A map of parameters
	 */
	public static Map<String, String> getParametersFromCommandLine(String commandLine)
	{
		// QueryFor Contact, name=Jeff as contactList
		Map<String, String> parameters = new HashMap<String, String>();

		if (hasParameters(commandLine))
		{
			if (hasReturnParameter(commandLine))
			{
				// strip return parameter
				commandLine = commandLine.substring(0, commandLine.lastIndexOf(" as "));
			}

			// strip off the command
			String parameterLine = stripCommandDeclaration(commandLine);

			if (!parameterLine.trim().equals(""))
			{
				// Contact, name=Jeff, Awesome
				String[] parameterParts = splitParameters(parameterLine);

				int count = 0;
				for (String parameterStr : parameterParts)
				{
					// if no parameter name is specified
					if (parameterStr.indexOf("=") == -1)
					{
						parameterStr = trimAndStripQuotes(parameterStr);

						// first param without alias is 'default'
						// additional params without alias have incremental number
						String key = count == 0 ? "default" : "" + count;
						parameters.put(key, parameterStr);

						count++;
					}
					else
					{
						int pos = parameterStr.indexOf('=');
						String[] parts = new String[2];
						parts[0] = parameterStr.substring(0, pos);
						parts[1] = parameterStr.substring(pos + 1, parameterStr.length());

						// trim & strip encapsulating quotes
						parts[1] = trimAndStripQuotes(parts[1]);

						parameters.put(parts[0].trim(), parts[1]);
					}
				}
			}
		}

		return parameters;
	}

	/**
	 * Tidy up a parameter value by trimming whitespace and stripping
	 * quotes.
	 * 
	 * @param parameterValue The parameter value to tidy
	 * @return The tidied parameter value
	 */
	private static String trimAndStripQuotes(String parameterValue)
	{
		// trim & strip encapsulating quotes
		parameterValue = parameterValue.trim();
		if (parameterValue.startsWith("\""))
		{
			parameterValue = parameterValue.substring(1);
		}
		if (parameterValue.endsWith("\""))
		{
			parameterValue = parameterValue.substring(0, parameterValue.length() - 1);
		}
		return parameterValue;
	}

	/**
	 * Get the Command declaration from the command line
	 * 
	 * @param commandLine The command line to inspect
	 * @return The command declaraction
	 */
	public static String getCommandFromCommandLine(String commandLine)
	{
		String[] parts = commandLine.split(" ");
		return parts[0];
	}

	/**
	 * Tokenise the comma-separated parameters of a command line, respecting
	 * commas in quoted sections.
	 * 
	 * @param parameterString the parameterString to tokenise
	 * @return a String[] of parameters
	 */
	private static String[] splitParameters(String parameterString)
	{
		// if there's no commas to split on
		if (parameterString.indexOf(COMMA) == -1)
		{
			return new String[] { parameterString };
		}

		// or if there's no quotes to care about
		else if (parameterString.indexOf(QUOTE) == -1)
		{
			return parameterString.split(",");
		}

		List<String> resultList = new ArrayList<String>();

		// flags
		boolean inQuotedToken = false;
		int startPos = 0;

		// interpret the line char by char and tokenise the parameterString
		for (int pos = 0; pos != parameterString.length(); pos++)
		{
			char c = parameterString.charAt(pos);
			if (c == QUOTE && inQuotedToken == false)
			{
				inQuotedToken = true;
			}
			else if (c == QUOTE && inQuotedToken == true)
			{
				inQuotedToken = false;
			}
			else if (c == COMMA && inQuotedToken == false)
			{
				String token = getToken(parameterString, startPos, pos);
				resultList.add(token);
				startPos = pos + 1;
			}
		}

		// validate we're not left with an open quote
		if (inQuotedToken == true)
		{
			throw new RuntimeException("unterminated quote error [" + getToken(parameterString, startPos, parameterString.length()) + "]");
		}

		// pickup the final token
		String token = getToken(parameterString, startPos, parameterString.length());
		resultList.add(token);

		// build the array of parameters
		String[] results = new String[resultList.size()];
		for (int index = 0; index != resultList.size(); index++)
		{
			results[index] = resultList.get(index);
		}

		return results;

	}

	/**
	 * Extract a token from a String based on start and end positions
	 * 
	 * @param line The line to extract the token from
	 * @param startPos The start position to extract from
	 * @param endPos The end position to extract from
	 * @return The token
	 */
	private static String getToken(String line, int startPos, int endPos)
	{
		String token = line.substring(startPos, endPos);
		return token.trim();
	}
}
