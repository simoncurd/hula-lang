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
package com.hula.lang.parser.util;

import junit.framework.TestCase;

import org.junit.Test;

public class ParserUtilTest extends TestCase
{
	@Test
	public void testHasReturnParameter() throws Exception
	{
		assertTrue("incorrect result", ParserUtil.hasReturnParameter("Command as result"));
		assertFalse("incorrect result", ParserUtil.hasReturnParameter("Command result"));
		assertFalse("incorrect result", ParserUtil.hasReturnParameter("Command "));
		assertTrue("incorrect result", ParserUtil.hasReturnParameter("Command param as result"));
		assertTrue("incorrect result", ParserUtil.hasReturnParameter("Command param, param1=param2 as result"));
	}

	@Test
	public void testHasParameters() throws Exception
	{
		assertTrue("incorrect result", ParserUtil.hasParameters("Command param"));
		assertTrue("incorrect result", ParserUtil.hasParameters("Command param, param"));
		assertFalse("incorrect result", ParserUtil.hasParameters("Command "));
	}
}
