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

import com.hula.lang.conditional.VariableReference;

public class VariableReferenceParsingTests extends BaseTermTestCase
{

	@Test
	public void testVariableNaming() throws Exception
	{
		assertValid("$n");
		assertValid("$name");
		assertValid("$NAME");
		assertValid("$name1");
		assertValid("$name1here");

		assertValid("$name.address");

		assertValid("$n.a.b");
		assertValid("$n_.a_.b_");
		
		
		assertValid("$list");

		// need at least one trailing char
		assertFails("$name.");

		// must start with a character
		assertFails("$1");
		assertFails("$a.1");

		assertFails("$_");
		assertFails("$a._");
	}

	protected void assertValid(String term) throws Exception
	{
		Assert.assertEquals(new VariableReference(term), parseTerm(term));
	}
}
