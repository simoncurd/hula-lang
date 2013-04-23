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
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Skip command is used as part of the {@link Loop} command to
 * move immediately to the next list item. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Don't print a certain name<br/>
 * 
 * <pre>
 * Loop $nameList as $name
 *    If $name="Jeff"
 *       Skip
 *    End
 *    Echo "name is $name"
 * End
 * </pre>
 */
public class Skip extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{
		script.add("continue;");
	}

	public void updateCommandModel(CommandModel cm) throws ParseError
	{
		if (cm.nestedUnder(Loop.class))
		{
			super.updateCommandModel(cm);
		}
		else
		{
			throw new ParseError("skip.not.nested.by.loop", getLineNumber(), getCommandLine(), null);
		}
	}

}
