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

import com.hula.lang.commands.core.OnFail;
import com.hula.lang.runtime.exception.HulaRuntimeException;

/**
 * A context object for exchanging data and state with the HulaExecutable
 */
public class HulaContext
{
	private Map<String, Object> parameters = new HashMap<String, Object>();
	private HulaRuntimeException exception = null;

	public void setLastException(HulaRuntimeException exception)
	{
		this.exception = exception;
	}

	/**
	 * Contains the last exception caught during the execution of the HulaExecutable
	 * by the {@link OnFail} command
	 * 
	 * @return HulaRuntimeException or null
	 */
	public HulaRuntimeException getLastException()
	{
		return this.exception;
	}

	public void setParameter(String name, Object value)
	{
		this.parameters.put(name, value);
	}

	public Map<String, Object> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	public Object getParameter(String key)
	{
		return this.parameters.get(key);
	}

}
