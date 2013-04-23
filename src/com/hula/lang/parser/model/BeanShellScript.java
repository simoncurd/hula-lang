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
package com.hula.lang.parser.model;

/**
 * A data structure used to build up the BeanShell script during parsing
 * of the Hula script. This wraps a StringBuilder and ensures indentation
 * is provided as part of nesting to ensure readable scripts are produced.
 */
public class BeanShellScript
{
	private int indent = 0;

	private StringBuilder sb = new StringBuilder();

	/**
	 * Add a line of BeanShell to the script. Understands that braces mean
	 * indentation.
	 * 
	 * @param line The line of BeanShell to add
	 */
	public void add(String line)
	{
		if (line.equals("}"))
		{
			indent--;
		}
		sb.append(indent() + line);
		if (line.equals("{"))
		{
			indent++;
		}
		sb.append("\n");
	}

	/**
	 * Provides an indentation prefix with the correct quantity of whitespace
	 * 
	 * @return an indentation prefix
	 */
	private String indent()
	{
		String indentStr = "";
		if (indent < 0)
		{
			indent = 0;
		}
		for (int i = 0; i != indent; i++)
		{
			indentStr += "   ";
		}
		return indentStr;
	}

	public String toString()
	{
		return sb.toString();
	}

}
