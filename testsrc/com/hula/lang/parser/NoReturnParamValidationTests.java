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
package com.hula.lang.parser;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.util.ScriptWrapper;

/**
 * 
 * For commands that explicitly declare they have no return parameters
 * (using the @NoReturnParam annotation), the parser validates that
 * no return parameter is provided
 * 
 */
public class NoReturnParamValidationTests extends BaseHulaTestCase
{
	@Test
	public void testParsingWithoutReturnParameter() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("NoParamsAllowedCommand");
		parser.parse(wrapper.toString());
	}

	@Test
	public void testInvalidReturnParameterFailsParsing() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("NoParamsAllowedCommand as txt");

		try
		{
			parser.parse(wrapper.toString());
			Assert.fail("return parameter should result in ParserException when unsupported");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			Assert.assertEquals("wrong number of errors", 1, e.getErrors().size());
			assertError(e, 0, 1, "NoParamsAllowedCommand as txt", "return.parameter.not.supported");
		}
	}

}
