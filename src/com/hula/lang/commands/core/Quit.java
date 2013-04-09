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
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.NestedQuitException;

/**
 * The Quit command terminates the script immediately, to return control
 * to the container. All calling scripts (using the {@link Call} command) 
 * will also be terminated. <br/><br/>
 * 
 * See also: {@link Return}<br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Exit the script based on a variable value<br/>
 * <pre>
 * If input="exit"
 *    Quit
 * End
 * </pre>
 */
@NoReturnParam
public class Quit extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		throw new NestedQuitException();
	}
}
