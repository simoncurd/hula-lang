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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hula.lang.commands.AbstractCommand;
import com.hula.lang.model.RequiresParams;
import com.hula.lang.model.RequiresReturnParam;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * The Tokenise command splits a value containing delimiter-separated tokens
 * into a list of separate values. <br/><br/>
 * The default delimiter is comma.<br/><br/>
 * 
 * Example Usage:<br/><br/>
 * 
 * Split a string of comma-separated tokens into a list.<br/>
 * <code>Tokenise "Jeff,John,Sam" as namelist</code><br/><br/>
 * 
 * Split a string of colon-separated tokens into a list.<br/>
 * <code>Tokenise "Jeff:John:Sam", delimiter=":" as namelist</code><br/><br/>
 * 
 * Split a string of tokens into a list using a regex delimiter.<br/>
 * <code>Tokenise "Jeff_John Sam", delimiter="[_ ]" as namelist</code><br/><br/>
 * 
 */
@RequiresReturnParam
@RequiresParams(names={"default"})
public class Tokenise extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{

		String value = getVariableValueAsString("default", connector);
		if (value == null)
		{
			//throw new HulaRuntimeException("no.value", "no value for parameter [" + getSignatureParameter("default") + "]");
			connector.setVariable(getReturnParameter(), new ArrayList<String>(0));
		}
		
		String delimiter = getVariableValueAsString("delimiter", connector);
		
		if (StringUtils.isEmpty(delimiter))
		{
			delimiter = ",";
		}
		
		String[] values = value.split(delimiter);
		List<String> results = Arrays.asList(values);
		
		connector.setVariable(getReturnParameter(), results);
		
	}

}
