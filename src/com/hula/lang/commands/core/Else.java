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

import java.util.List;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.parser.util.ValidationUtil;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Else command is used to nest Commands which will be executed when no
 * other conditional command has evaluated true. 
 *  It always follows the {@link If} or {@link ElseIf} commands. <br/><br/>
 * 
 * See also {@link If}, {@link ElseIf}<br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Output message if the name parameter is not matched<br/>
 * <pre>
 * If $name="Jeff"
 *    Echo "My name is Jeff"
 * Else
 *    Echo "I give up. Your name is $name"
 * End
 * </pre>
 * 
 * 
 */
@NoReturnParam
public class Else extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{
		script.add("}");
		script.add("else");
		script.add("{");
	}

	public void updateCommandModel(CommandModel cm)
	{
		cm.stopNesting();
		cm.addCommand(this);
		cm.startNesting(this);
	}


	public void validate(List<ParseError> errors)
	{
		ValidationUtil.assertNoParameters(this, errors);
	}
}
