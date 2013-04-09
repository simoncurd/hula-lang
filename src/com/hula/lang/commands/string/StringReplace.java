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
package com.hula.lang.commands.string;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The StringReplace command replaces a substring. <br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Replace a single character in a given string. Result is "ja ja ja"<br/>
 * <code>StringReplace "ha ha ha", match="h", with="j" as result</code><br/><br/>
 * 
 * Replace a section in a given string. Result is "Thisisastring". Match accepts a Regular Expression. <br/>
 * <code>StringReplace "This-is a_string", match="[ _-]", with="" as result</code><br/><br/>
 */
@RequiresReturnParam
@RequiresParams(names = { "default", "match", "with" })
public class StringReplace extends AbstractCommand
{
	@Override
	public void execute(RuntimeConnector connector)
	{
		String content = getVariableValueAsString("default", connector);
		String match = getVariableValueAsString("match", connector);
		String with = getVariableValueAsString("with", connector);

		content = content.replaceAll(match, with);

		connector.setVariable(getReturnParameter(), content);
	}
}
