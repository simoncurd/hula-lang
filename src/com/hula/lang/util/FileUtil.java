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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A utility class with helper methods for working with files
 */
public class FileUtil
{
	/**
	 * This provides platform support for paths which are relative (part of the
	 * classpath) or absolute disk references. It attempts relative first, then
	 * defers to an absolute lookup
	 * 
	 * @param filename The filename to search for. Relative filenames must start with '/'
	 * @return InputStream to the resource
	 */
	public static InputStream getFileInputStream(String filename)
	{
		InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(filename);
		if (in == null)
		{
			File f = null;
			try
			{
				f = new File(filename);

				in = new FileInputStream(f);
			}
			catch (FileNotFoundException e)
			{
				throw new RuntimeException("Unable to find file [" + filename + "] as relative or absolute path - using [" + f.getAbsolutePath() + "]");
			}
		}
		return in;
	}
	
	/**
	 * Utility method for loading a properties file from the classpath
	 * @param filename The name of the properties file
	 * @return The loaded Properties file
	 */
	public static Properties loadProperties(String filename)
	{
		Properties properties = new Properties();
		InputStream in = FileUtil.getFileInputStream(filename);
		try
		{
			properties.load(in);
		}
		catch (IOException e)
		{
			throw new RuntimeException("error loading config properties", e);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				// swallow - nothing we can do about this
			}
		}
		return properties;
	}
}
