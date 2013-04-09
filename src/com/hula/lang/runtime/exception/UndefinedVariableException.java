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
 * An exception caused by a reference in a Hula script to an unknown
 * variable. 
 */
public class UndefinedVariableException extends HulaPlayerException
{
	private String variableName;

	/**
	 * Create a new UndefinedVariableException
	 * 
	 * @param variableName the name of the undefined variable
	 * @param errorCode the error code to report
	 * @param throwable The exception thrown
	 * @param lineNumber The line number of the failure
	 * @param variables The variables at the time of execution
	 */
	public UndefinedVariableException(String variableName, String errorCode, Throwable throwable, int lineNumber, Map<String, Object> variables)
	{
		super(errorCode, throwable, lineNumber, variables);
		this.variableName = variableName;
	}

	public String getVariableName()
	{
		return variableName;
	}

}
