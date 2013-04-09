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

import com.hula.lang.HulaConstants;
import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.conditional.OperatorUtil;
import com.hula.lang.conditional.ExpressionUtil;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.parser.util.ValidationUtil;
import com.hula.lang.parser.util.ParserUtil;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The If command evaluates a boolean expression to decide whether its body
 * will be executed. <br/><br/>
 * 
 * See also {@link ElseIf}, {@link Else}<br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Output message if the name parameter is correct<br/>
 * <pre>
 * If $name="Jeff"
 *    Echo "My name is Jeff"
 * End
 * </pre>
 * 
 * Output message if the name and age parameters are correct<br/>
 * <pre>
 * If $name="Jeff" AND $age=32
 * Echo "My name is Jeff, and I'm 32"
 * End
 * </pre>
 * 
 * Output message if the name parameter and age range are correct<br/>
 * <pre>
 * If $name="Jeff" AND ( $age >= 32 OR $age < 35 ) 
 *    Echo "My name is Jeff, and I'm at least 32 but definitely not quite 35"
 * End
 * </pre>
 * 
 */
@NoReturnParam
public class If extends AbstractCommand
{
	@Override
	public void execute(RuntimeConnector connector)
	{
	}

	public void updateBeanShellScript(BeanShellScript script)
	{
		addBSHLineNumber(script);
		String eval = ParserUtil.stripCommandDeclaration(commandLine);
		
		eval = eval.replaceAll("\"", "\\\\\"");
		String conditionalClassName = ExpressionUtil.class.getName();
		script.add("if (" + conditionalClassName + ".evaluate(\""+ eval + "\", "+ HulaConstants.runtimeConnector +"))");
		script.add("{");

	}

	public void updateCommandModel(CommandModel cm)
	{
		cm.addCommand(this);
		cm.startNesting(this);
	}


	public void validate(List<ParseError> errors)
	{
		ValidationUtil.assertBeanShellSignature(this, errors);
	}
}
