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

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class NumberParsingTests extends BaseTermTestCase
{
	@Test
	public void testNumber() throws Exception
	{
		// integer
		assertValid("32");

		// decimal
		assertValid("32.2");

		// decimal
		assertValid("32.23453453");

		assertValid("0000002");

		// failure cases
		assertFails("32.22342342.43");

	}

	private void assertValid(String term) throws Exception
	{
		Assert.assertEquals(new BigDecimal(term), parseTerm(term));
	}
}
