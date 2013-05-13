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

import java.math.BigDecimal;

import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.util.CommandUtil;

/**
 * A utility class to the {@link HulaConditionalTree} providing methods
 * implementing the conditional, relational & equality operations required for
 * Hula commands supporting conditional logic.
 */
public class OperatorUtil
{

	/**
	 * Processes equals operations (=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating equality
	 */
	public static boolean testEquals(Object op1, Object op2, RuntimeConnector rc)
	{
		op1 = resolveAlign(op1, rc);
		op2 = resolveAlign(op2, rc);

		if (op1 == null && op2 == null)
		{
			return true;
		}
		else if (op1 == null || op2 == null)
		{
			return false;
		}
		
		// if the class types don't match, compare as strings
		else if (!op1.getClass().equals(op2.getClass()))
		{
			op1 = op1.toString();
			op2 = op2.toString();
		}
		boolean result = op1.equals(op2);
		return result;
	}

	/**
	 * Processes not-equals operations (!=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating non-equality
	 */
	public static boolean testNotEquals(Object op1, Object op2, RuntimeConnector rc)
	{
		return !testEquals(op1, op2, rc);
	}

	/**
	 * Processes greater-than operations (>).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testGreaterThan(Object op1, Object op2, RuntimeConnector rc)
	{
		NumericCompare nc = new NumericCompare(op1, op2, rc);
		return nc.left.compareTo(nc.right) == 1 ? true : false;
	}

	/**
	 * Processes greater-than-equal-to operations (>=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testGreaterThanEqualTo(Object op1, Object op2, RuntimeConnector rc)
	{
		NumericCompare nc = new NumericCompare(op1, op2, rc);
		return nc.left.compareTo(nc.right) >= 0 ? true : false;
	}

	/**
	 * Processes less-than operations (<).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testLessThan(Object op1, Object op2, RuntimeConnector rc)
	{
		NumericCompare nc = new NumericCompare(op1, op2, rc);
		return nc.left.compareTo(nc.right) == -1 ? true : false;

	}

	/**
	 * Processes less-than-equal-to operations (<=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testLessThanEqualTo(Object op1, Object op2, RuntimeConnector rc)
	{
		NumericCompare nc = new NumericCompare(op1, op2, rc);
		return nc.left.compareTo(nc.right) <= 0 ? true : false;
	}

	/**
	 * Processes conditional AND operations (<=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testAND(Object op1, Object op2, RuntimeConnector rc)
	{
		BooleanCompare bc = new BooleanCompare(op1, op2, rc);
		return bc.left && bc.right;
	}

	/**
	 * Processes conditional OR operations (<=).
	 * 
	 * @param op1 left operand
	 * @param op2 right operand
	 * @param rc RuntimeConnector
	 * @return boolean indicating result
	 */
	public static boolean testOR(Object op1, Object op2, RuntimeConnector rc)
	{
		BooleanCompare bc = new BooleanCompare(op1, op2, rc);
		return bc.left || bc.right;
	}

	/**
	 * A utility enumeration which specifies the preferred types for
	 * resolved Object to be cast to
	 */
	enum PreferredType
	{
		NUMBER;
	}

	/**
	 * Resolve an object and align to a comparable type.
	 * 
	 * @param object The object to resolve
	 * @param rc The {@link RuntimeConnector} for runtime variables
	 * @return The resolved Object.
	 */
	public static Object resolveAlign(Object object, RuntimeConnector rc)
	{
		return resolveAlign(object, rc, null);
	}

	/**
	 * Resolve variable references and align to a comparable type.
	 * 
	 * @param object The object to resolve/align
	 * @param rc The runtime connector for runtime variables
	 * @param pt The preferred type for return values
	 * @return The resolved object.
	 */
	public static Object resolveAlign(Object object, RuntimeConnector rc, PreferredType pt)
	{
		// resolve variable references
		if (object instanceof VariableReference)
		{
			object = resolveVariable((VariableReference) object, rc);
		}

		// remove leading/trailing quotes from strings if necessary
		if (object instanceof String && object.toString().indexOf('"') != -1)
		{
			String value = object.toString();

			// strip quotes
			if (value.startsWith("\"") && value.endsWith("\""))
			{
				object = value.substring(1, value.length() - 1);
			}
		}

		// if we have a preferred type, lets convert to that now
		if (pt != null)
		{
			if (pt == PreferredType.NUMBER)
			{
				object = new BigDecimal(object.toString());
			}
		}

		// align numerical types as BigDecimal
		if (object instanceof Integer)
		{
			object = new BigDecimal(object.toString());
		}
		else if (object instanceof Float)
		{
			object = new BigDecimal(object.toString());
		}
		else if (object instanceof Long)
		{
			object = new BigDecimal(object.toString());
		}
		return object;
	}

	/**
	 * Given a {@link VariableReference}, get the name of the variable and look this
	 * up from the {@link RuntimeConnector}
	 * 
	 * @param reference The variable reference to resolve
	 * @param rc The runtime connector holding runtime variables
	 * @return The resolved Object
	 */
	private static Object resolveVariable(VariableReference reference, RuntimeConnector rc)
	{
		return CommandUtil.getReferencedVariableValue(reference.getName(), rc);
	}
}
