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

/**
 * An unchecked exception which occurs during the execution
 * of a Hula Executable. Exceptions of this type will be handled
 * by the {@link com.hula.lang.runtime.HulaPlayer}.
 */
public class HulaRuntimeException extends RuntimeException
{
	private String errorCode;
	private String errorDescription;

	/**
	 * Create a HulaException
	 * 
	 * @param errorCode The error code to report
	 * @param errorDescription The description of the error
	 */
	public HulaRuntimeException(String errorCode, String errorDescription)
	{
		super(errorDescription);
		this.errorCode = errorCode;
	}

	/**
	 * Create a HulaException
	 * 
	 * @param errorCode The error code to report
	 * @param errorDescription The description of the error
	 * @param throwable the original exception
	 */
	public HulaRuntimeException(String errorCode, String errorDescription, Throwable throwable)
	{
		super(errorDescription, throwable);
		this.errorCode = errorCode;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}

}
