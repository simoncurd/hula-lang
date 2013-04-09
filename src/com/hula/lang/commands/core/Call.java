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

import java.io.IOException;

import bsh.EvalError;
import bsh.TargetError;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.reader.ScriptReader;
import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;
import com.hula.lang.runtime.exception.NestedQuitException;

/**
 * Allows a Hula script to call another script. After the called script
 * is completed, the calling script will resume. <br/><br/> 
 * 
 * Example Usage:<br/><br/>
 * 
 * Call another script called 'StoreData'
 * <pre>
 * Call StoreData
 * </pre>
 */
@RequiresParams(names = { "default" })
public class Call extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		// the name of the script to call
		String scriptName = getVariableValueAsString("default", connector);
		ScriptReader reader = connector.getScriptReader();
		HulaExecutable exec = null;
		
		// load the script
		try
		{
			exec = reader.read(scriptName);
		}
		catch (IOException e)
		{
			throw new HulaRuntimeException("error.reading.script", "error reading script [" + scriptName + "]", e);
		}
		catch (HulaParserException e)
		{
			throw new HulaRuntimeException("error.parsing.script", "error parsing script [" + scriptName + "]", e);
		}

		// run the script
		try
		{
			connector.getInterpreter().eval(exec.getContent());
		}
		catch (TargetError e)
		{
			if (e.getTarget() instanceof NestedQuitException)
			{
				throw (NestedQuitException)e.getTarget();
			}
			throw new HulaRuntimeException("error.running.script", "error running script [" + scriptName + "]", e);			
		}
		catch (EvalError e)
		{
			throw new HulaRuntimeException("error.running.script", "error running script [" + scriptName + "]", e);
		}
	}

}
