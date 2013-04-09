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

/**
 * Used by the {@link com.hula.lang.parser.HulaParser} to ensure the original 
 * Hula comments are retained in the resulting BeanShell script. <br/><br/>
 * 
 * This is NOT a Hula script command<br/><br/>
 * 
 * Comments are made in Hula scripts using the hash (#) symbol. Comments must be the
 * first and only item on a line of Hula script - they cannot be attached
 * after another command line.  
 * For example:</br><br/>
 * 
 * <pre>
 * # this is a comment
 * # this is a second comment
 * # this is a second comment
 * </pre>
 * 
 * This is not a valid comment:<br/>
 * <pre>
 * Echo "Jeff" # write out Jeff's name
 * </pre>
 */
@NoReturnParam
public class Comment extends AbstractCommand
{
	public void updateBeanShellScript(BeanShellScript script)
	{
		script.add("// " + commandLine);
	}

	@Override
	public void execute(RuntimeConnector connector)
	{
	}


}
