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
package com.hula.lang.runtime.exception;

import java.util.Map;

/**
 * A checked exception thrown by the {@link com.hula.lang.runtime.HulaPlayer} which must be explicitly
 * caught by the container. It attempts to provide as much context as
 * possible about the exception which occured.
 */
public class HulaPlayerException extends Exception
{
	private int lineNumber;
	private Map<String, Object> variables;

	/**
	 * Create a new UnhandledErrorException
	 * 
	 * @param errorCode the error code to report
	 * @param throwable The exception thrown
	 * @param lineNumber The line number of the failure
	 * @param variables The variables at the time of execution
	 */
	public HulaPlayerException(String errorCode, Throwable throwable, int lineNumber, Map<String, Object> variables)
	{
		super(errorCode, throwable);
		this.lineNumber = lineNumber;
		this.variables = variables;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public Map<String, Object> getVariables()
	{
		return variables;
	}

	@Override
	public String toString()
	{
		return this.getMessage() + " at hula line [" + lineNumber + "]";
	}
}
