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
package com.hula.lang.runtime;

import java.util.HashMap;
import java.util.Map;

import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.UtilEvalError;

import com.hula.lang.HulaConstants;
import com.hula.lang.reader.ScriptReader;

/**
 * This provides access to the variables at runtime, and holds no state itself
 */
public class RuntimeConnector
{
	// a reference to the interpreter we're using
	private Interpreter interpreter = null;

	// a reference to the script reader we're using
	private ScriptReader scriptReader = null;

	/**
	 * Create a new RuntimeConnector
	 * 
	 * @param interpreter The interpreter we're parsing with
	 * @param scriptReader The script reader to use if loading additional scripts
	 */
	public RuntimeConnector(Interpreter interpreter, ScriptReader scriptReader)
	{
		this.interpreter = interpreter;
		this.scriptReader = scriptReader;
	}

	/**
	 * Get the interpreter in use
	 * 
	 * @return the interpreter in use
	 */
	public Interpreter getInterpreter()
	{
		return interpreter;
	}

	/**
	 * Get the script reader we're using
	 * 
	 * @return the script reader we're using
	 */
	public ScriptReader getScriptReader()
	{
		return scriptReader;
	}

	/**
	 * Helper method for getting a reference to the HulaContext
	 * 
	 * @return the HulaContext
	 */
	public HulaContext getHulaContext()
	{
		return (HulaContext) getVariable(HulaConstants.hulaContext);
	}

	/**
	 * Get a variable
	 * 
	 * @param name the name of the variable
	 * @return the value of the variable
	 */
	public Object getVariable(String name)
	{
		try
		{
			Object result = getNameSpace().getVariable(name);
			if (result == Primitive.VOID)
			{
				result = null;
			}
			return result;
		}
		catch (UtilEvalError e)
		{
			throw new RuntimeException("Error finding variable [" + name + "]", e);
		}
	}

	private NameSpace getNameSpace()
	{
		return interpreter.getNameSpace();
	}

	/**
	 * Set a variable
	 * 
	 * @param name the name of the variable
	 * @param value the value of the variable
	 */
	public void setVariable(String name, Object value)
	{
		if (value == null)
		{
			value = Primitive.VOID;
		}
		try
		{
			getNameSpace().setVariable(name, value, false);
		}
		catch (UtilEvalError e)
		{
			throw new RuntimeException("Error setting variable [" + name + "]", e);
		}
	}

	/**
	 * Get all variables
	 * 
	 * @return all variables
	 */
	public Map<String, Object> getVariables()
	{
		String[] variableNames = getNameSpace().getVariableNames();
		Map<String, Object> results = new HashMap<String, Object>(variableNames.length);
		for (String key : variableNames)
		{
			Object value;
			try
			{
				value = getNameSpace().getVariable(key);
				results.put(key, value);
			}
			catch (UtilEvalError e)
			{
				throw new RuntimeException("error extracting variables", e);
			}

		}
		return results;
	}
}
