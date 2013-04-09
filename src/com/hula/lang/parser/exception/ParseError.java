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

/**
 * Responsible for reporting a specific error raised by the {@link com.hula.lang.parser.HulaParser}. 
 */
public class ParseError extends Exception
{
	// the Hula script line number
	private int lineNumber;
	
	// the command line which caused the error
	private String commandLine;
	
	// the error code
	private String errorCode;
	
	// parameters of the error
	private String[] params;
	
	// the specific error thrown
	private Throwable throwable;

	/**
	 * Create a new ParseError
	 * 
	 * @param errorCode the error code
	 * @param lineNumber the Hula script line number
	 * @param commandLine the command line which caused the error
	 * @param throwable the specific error thrown
	 */
	public ParseError(String errorCode, int lineNumber, String commandLine, Throwable throwable)
	{
		super(throwable);
		this.errorCode = errorCode;
		this.lineNumber = lineNumber;
		this.commandLine = commandLine;
	}

	/**
	 * Create a new ParseError
	 * 
	 * @param errorCode the error code
	 * @param lineNumber the Hula script line number
	 * @param commandLine the command line which caused the error
	 * @param params parameters of the error
	 * @param throwable the specific error thrown
	 * 
	 */
	public ParseError(String errorCode, int lineNumber, String commandLine, String[] params, Throwable throwable)
	{
		super(throwable);
		this.errorCode = errorCode;
		this.lineNumber = lineNumber;
		this.commandLine = commandLine;
		this.params = params;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	public String getCommandLine()
	{
		return commandLine;
	}

	public void setCommandLine(String commandLine)
	{
		this.commandLine = commandLine;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public Throwable getThrowable()
	{
		return throwable;
	}

	public String toString()
	{
		String exceptionStr = "";
		if (throwable != null)
		{
			exceptionStr = "exception [" + throwable.getMessage() + "]";
		}

		return "parse error [" + errorCode + "] for command [" + commandLine + "] on line [" + lineNumber + "] params " + params + " " + exceptionStr;
	}

	public String[] getParams()
	{
		return params;
	}

	public void setParams(String[] params)
	{
		this.params = params;
	}

}
