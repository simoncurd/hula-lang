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
package com.hula.lang.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotNotationUtil
{
	private static Logger logger = LoggerFactory.getLogger(DotNotationUtil.class);

	/**
	 * Checks if a String is a property path. Returns true if at least
	 * one dot (.) is present
	 * 
	 * @param propertyPath the String to inspect
	 * @return boolean indicating whether this is a property path
	 */
	public static boolean isPath(String propertyPath)
	{
		return propertyPath.indexOf(".") != -1;
	}

	/**
	 * Get the first property in a property path
	 * 
	 * @param propertyPath the property path to inspect
	 * @return the first property in the property path
	 */
	public static String getFirstItem(String propertyPath)
	{
		if (isPath(propertyPath))
		{
			String[] path = getPath(propertyPath);
			return path[0];
		}
		return propertyPath;
	}

	/**
	 * Split a dot notation property path into it's constitute parts
	 * 
	 * @param propertyPath The property path to inspect
	 * @return A String[] of nested properties
	 */
	public static String[] getPath(String propertyPath)
	{
		String[] path = new String[] { propertyPath };
		if (isPath(propertyPath))
		{
			path = propertyPath.split("\\.");
		}
		return path;
	}

	/**
	 * Get the last item in a property path
	 * 
	 * @param propertyPath the property path to inspect
	 * @return the last property in the property path
	 */
	public static String getLastItem(String propertyPath)
	{
		if (isPath(propertyPath))
		{
			String[] path = getPath(propertyPath);
			return path[path.length - 1];
		}
		return propertyPath;
	}



	public static void setProperty(String name, Object source, Object value, int index)
	{
		// last item in the path is the target property name
		//
		// i.e. script.description
		//
		// we're looking up to script, then to description
		String targetProperty = getLastItem(name);

		String[] path = getPath(name);

		// refine the source down to the correct object
		for (int i = index; index != path.length - 1; index++)
		{
			String item = path[i];

			source = reflect(source, item);

		}

		// now do the set
		try
		{
			PropertyUtils.setProperty(source, targetProperty, value);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}

	}

	/**
	 * This inspects the property path of an object graph to return the value
	 * of a property. Uses a dot notation based path. 
	 * 
	 * @param propertyPath The path to the property
	 * @param root The root of the object graph
	 * @return The property value
	 */
	public static Object getProperty(String propertyPath, Object root)
	{
		return getProperty(propertyPath, root, 0);
	}

	/**
	 * This inspects the property path of an object graph to return the value
	 * of a property. Uses a dot notation based path. 
	 * 
	 * @param propertyPath The path to the property
	 * @param root The root of the object graph
	 * @param index The path item to start from
	 * @return The property result
	 */
	public static Object getProperty(String propertyPath, Object root, int index)
	{
		Object value = root;
		if (value == null)
		{
			// no point in traversing any further
			return null;
		}
		String[] path = getPath(propertyPath);

		// traverse path items
		for (int i = index; i != path.length; i++)
		{
			String par = path[i];

			value = reflect(value, par);

			if (value == null)
			{
				// no point in traversing any further
				return null;
			}
		}
		return value;

	}

	/**
	 * Uses beanutils to reflect the value of a property
	 * 
	 * @param obj the object to inspect
	 * @param property the property to reflect
	 * @return the value of the property
	 */
	private static Object reflect(Object obj, String property)
	{
		try
		{
			return PropertyUtils.getProperty(obj, property);
		}
		catch (Throwable t)
		{
			logger.error("error getting property ["+t.getClass().getSimpleName() + "]", t);
			return null;
		}
	}

}
