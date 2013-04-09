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

import java.util.List;

/**
 * This class provides helper methods for working with BeanShell at parse
 * time and runtime.
 * 
 * Unfortunately this requires some nasty workarounds to provide helpful
 * context to the user.
 */
public class BeanShellUtil
{	
	/**
	 * Check if a line number is mentioned in the message. 
	 *  
	 * @param message the original message
	 * @return boolean flag
	 */
	public static boolean isLineNumberMentioned(String message)
	{
		return message.contains("at line");
	}

	/**
	 * Check if a variable is undefined
	 * 
	 * @param message the original message 
	 * @return boolean flag
	 */
	public static boolean isUndefinedVariableMentioned(String message)
	{
		return message.contains("undefined variable or class name");
	}

	/**
	 * Attempt to find the undefined variable name
	 * 
	 * @param message the original message
	 * @return the undefined variable name
	 */
	public static String getUndefinedVariableName(String message)
	{
		try
		{
			String variableName = message.substring(message.lastIndexOf(':') + 1, message.length());
			return variableName.trim();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return null;
		}
	}

	/**
	 * Attempt to find the line number
	 * 
	 * @param message the original message
	 * @return the line number
	 */
	public static int getLineNumber(String message)
	{
		// extract & convert the line number
		try
		{
			String lineStr = message.substring(message.indexOf("line ") + 5, message.indexOf(","));
			int lineNumber = Integer.parseInt(lineStr);
			return lineNumber;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return 0;
		}
	}

	/**
	 * Attempt to extract a meaningful code snippet given a line number an list of
	 * strings
	 * 
	 * @param lineNumber the line number of the offending line
	 * @param lines the list of strings containin the BeanShell line
	 * @return a code snippet
	 */
	public static String extractCodeSnippet(int lineNumber, List<String> lines)
	{
		// get x lines either side of the error
		int from = lineNumber - 3;
		int to = lineNumber + 3;
		if (from < 0)
		{
			from = 0;
		}
		if (to > lines.size())
		{
			to = lines.size();
		}

		StringBuilder sb = new StringBuilder();

		// print the range of lines
		for (int i = from; i != to; i++)
		{
			sb.append((i + 1) + ": " + lines.get(i));
			sb.append("\n");
		}

		return sb.toString();
	}

	
}
