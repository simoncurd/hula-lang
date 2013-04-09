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
package com.hula.lang.runtime;

/**
 * A container for an error which occurs during the execution of a HulaExecutable
 */
public class HulaError
{
	private int lineNumber;
	private String message;
	private Throwable cause;
	
	/**
	 * Get the line number from the original Hula script
	 * @return the line number from the original Hula script
	 */
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	/**
	 * Set the line number from the original Hula script
	 * @param lineNumber the line number from the original Hula script
	 */
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Get the exception that was thrown
	 * @return the exception that was thrown
	 */
	public Throwable getCause()
	{
		return cause;
	}
	
	/**
	 * Set the exception that was thrown
	 * @param cause the exception that was thrown
	 */
	public void setCause(Throwable cause)
	{
		this.cause = cause;
	}
	
}
