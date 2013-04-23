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
package com.hula.lang.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.HulaParser;
import com.hula.lang.parser.exception.HulaParserException;

public class ScriptReaderImpl implements ScriptReader
{
	private static final String NEWLINE = "\n";

	private HulaParser hulaParser = null;
	private String path = null;
	private String scriptExtension = ".txt";

	/**
	 * Create a new ScriptReader
	 * 
	 * @param hulaParser The HulaParser to parse scripts with
	 * @param path The file system path to load scripts from
	 */
	public ScriptReaderImpl(HulaParser hulaParser, String path)
	{
		this.hulaParser = hulaParser;

		if (!path.endsWith("/"))
		{
			path += "/";
		}
		this.path = path;
	}

	@Override
	public HulaExecutable read(String scriptName) throws IOException, HulaParserException
	{
		// load the file
		BufferedReader in = new BufferedReader(new FileReader(path + scriptName + scriptExtension));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null)
		{
			sb.append(line);
			sb.append(NEWLINE);
		}
		in.close();

		// parse script
		HulaExecutable exec = hulaParser.parse(sb.toString());
		return exec;
	}

	/**
	 * Set the file system extension for scripts
	 * 
	 * @param scriptExtension The file system extension token (default ".txt")
	 */
	public void setScriptExtension(String scriptExtension)
	{
		this.scriptExtension = scriptExtension;
	}

}
