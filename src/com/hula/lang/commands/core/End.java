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
 * The End command is used to explicitly terminate a nested Command section. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * An example using the {@link If} command
 * 
 * <pre>
 * If $value=1
 *    # do something
 * End
 * </pre>
 * 
 * Multiple nesting using the {@link If} command
 * 
 * <pre>
 * If $value > 1
 *    # do something
 *    If $value = 1
 *       # do something else
 *    End
 * End
 * </pre>
 */
@NoReturnParam
public class End extends AbstractCommand
{
	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{
		script.add("}");
	}

	public void updateCommandModel(CommandModel cm) throws ParseError
	{
		cm.addCommand(this);
		if (!cm.isNesting())
		{
			throw new ParseError("unnecessary.end.statement", this.lineNumber, this.commandLine, null);
		}
		cm.stopNesting();
	}

	public void validate(List<ParseError> errors)
	{
		ValidationUtil.assertNoParameters(this, errors);
	}

}
