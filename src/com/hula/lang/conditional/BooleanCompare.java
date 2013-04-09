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
package com.hula.lang.conditional;

import com.hula.lang.runtime.RuntimeConnector;

/**
 * A utility class for comparing boolean values
 */
public class BooleanCompare
{
	// package protected
	boolean left;
	boolean right;
	
	public BooleanCompare(Object op1, Object op2, RuntimeConnector rc)
	{
		op1 = OperatorUtil.resolveAlign(op1, rc);
		op2 = OperatorUtil.resolveAlign(op2, rc);

		if (!(op1 instanceof Boolean) || !(op2 instanceof Boolean))
		{
			throw new RuntimeException("Cannot compare non-boolean values");
		}
		
		left = (Boolean)op1;
		right = (Boolean)op2;
	}
}
