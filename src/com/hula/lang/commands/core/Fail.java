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
package com.hula.lang.commands.core;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.FailsWith;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * The Fail command is used to indicate that a failure has occured.
 * Execution will continue within the next {@link OnFail} command. <br/>
 * <br/>
 * 
 * See also {@link OnFail}<br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Begin a failure process if the incorrect name is provided<br/>
 * 
 * <pre>
 * Set name="Jeff"
 * If $name="Jeff"
 *    Fail "unexpected.name", "that name was not expected"
 * End
 * OnFail as error
 *    Echo "An error occured - id [$error.code] message [$error.message]"
 * End
 * </pre>
 * 
 */
@FailsWith(errorCodes = {})
public class Fail extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		String errorCode = getVariableValueAsString("default", connector);
		String errorDescription = getVariableValueAsString("1", connector);

		throw new HulaRuntimeException(errorCode, errorDescription);

	}
}
