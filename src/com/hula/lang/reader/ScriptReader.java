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

import java.io.IOException;

import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.parser.exception.HulaParserException;

/**
 * Responsible for reading and parsing Hula scripts.
 */
public interface ScriptReader
{
	/**
	 * Read a Hula script and return a parsed version
	 * 
	 * @param scriptName The name of the script to load
	 * @return An executable version of the script
	 * @throws IOException
	 * @throws HulaParserException
	 */
	HulaExecutable read(String scriptName) throws IOException, HulaParserException;
}
