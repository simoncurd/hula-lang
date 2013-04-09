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
package com.hula.lang.conditional.term;


import org.junit.Assert;
import org.junit.Test;

public class BooleanParsingTests extends BaseTermTestCase
{

	@Test
	public void testBoolean() throws Exception
	{
		assertValid("true");
		assertValid("false");

		assertFails("False");
		assertFails("True");
		assertFails("FALSE");
		assertFails("TRUE");
	}

	private void assertValid(String term) throws Exception
	{
		Assert.assertEquals(new Boolean(term), parseTerm(term));
	}
}
