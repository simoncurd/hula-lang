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

public class StringParsingTests extends BaseTermTestCase
{
	@Test
	public void testString() throws Exception
	{

		assertValidTerm("Jeff");

		// whitespace
		assertValidTerm(" Je ff ");

		// symbols
		assertValidTerm("Jeff_H on-&^%$£)(*&£$&^$%");

		// quote encoding
		// parseExpression("\"s\"s\"");
		parseTerm("\"s\"s\"");

		// newlines
		assertValidTerm("\n");

		assertValidTerm("start\u0000end");

		// missing trailing quote
		assertFails("\"hello world");

	}

	private void assertValidTerm(String term) throws Exception
	{
		term = quotes(term);
		Assert.assertEquals(new String(term), parseTerm(term));
	}

}
