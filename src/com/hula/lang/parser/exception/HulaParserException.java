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
package com.hula.lang.parser.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for containing a set of {@link ParseError} instances.
 */
public class HulaParserException extends Exception
{
	// a list of errors
	private List<ParseError> errors = new ArrayList<ParseError>(0);

	/**
	 * Create a new ParserException
	 * 
	 * @param message The message to report for this exception
	 * @param errors The {@link ParseError} instances
	 */
	public HulaParserException(String message, List<ParseError> errors)
	{
		super(message);
		this.errors = errors;
	}

	public List<ParseError> getErrors()
	{
		return errors;
	}

	@Override
	public String getMessage()
	{
		return super.getMessage() + " (" + this.errors.toString() + ")";
	}

}
