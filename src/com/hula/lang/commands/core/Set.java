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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.NoReturnParam;
import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.util.ValidationUtil;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Set command assigns a variable value. <br/>
 * <br/>
 * 
 * Example Usage:<br/>
 * <br/>
 * 
 * Set a variable to a string</br/> <code>Set name="Jeff"</code><br/>
 * <br/>
 * 
 * Multiple assignments can be made in one statement, which are processed
 * from left to right. As shown below, age is set in parameter 2, then used
 * in parameter 3.<br/>
 * <code>Set name=$person.name, age=32, $person.age=$age</code>
 */
@NoReturnParam
public class Set extends AbstractCommand
{
	private Logger logger = LoggerFactory.getLogger(Set.class);

	@Override
	public void execute(RuntimeConnector connector)
	{
		for (String key : this.signatureParameters.keySet())
		{
			Object value = getVariableValue(key, connector);
			if (key.equals("default"))
			{
				continue;
			}
			logger.info("mapping [{}] to [{}]", key, value);

			assignVariable(key, value, connector);
		}

	}

	public void validate(List<ParseError> errors)
	{
		ValidationUtil.assertAtLeastOneParameter(this, errors);
	}
}
