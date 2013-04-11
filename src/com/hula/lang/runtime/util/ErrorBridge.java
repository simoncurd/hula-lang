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
package com.hula.lang.runtime.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hula.lang.HulaConstants;
import com.hula.lang.runtime.HulaError;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * Responsible for packaging an exception caught by the OnFail Command
 * into a form which relates to the original Hula script, specifically
 * the line number
 */
public class ErrorBridge
{
	private Logger logger = LoggerFactory.getLogger(ErrorBridge.class);

	/**
	 * Package the context of an error as a HulaError
	 * 
	 * @param connector the RuntimeConnector
	 * @param throwable the thrown exception
	 * @param returnParameter the variable name to store the HulaError with
	 */
	public void execute(RuntimeConnector connector, Throwable throwable, String returnParameter)
	{
		HulaError hulaError = new HulaError();

		// store the exception on the HulaError
		hulaError.setCause(throwable);

		if (throwable instanceof HulaRuntimeException)
		{
			hulaError.setId(((HulaRuntimeException) throwable).getErrorCode());
		}

		// lookup the original line number and set in the HulaError
		Object lineNumberObject = connector.getVariable(HulaConstants.lineNumber);
		try
		{
			hulaError.setLineNumber(Integer.parseInt(lineNumberObject.toString()));
		}
		catch (NumberFormatException e)
		{
			// nothing can be done about this
			logger.error("error extracting line number for error [" + lineNumberObject + "]", e);
		}

		// lookup the message and set in the HulaError
		String throwableMessage = throwable.getMessage();
		if (throwable.getMessage() == null)
		{
			throwableMessage = throwable.toString();
		}
		hulaError.setMessage(throwableMessage);

		// store on the context using the named return parameter
		connector.setVariable(returnParameter, hulaError);

	}
}
