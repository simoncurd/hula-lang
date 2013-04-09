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
package com.hula.lang.runtime;

import java.io.IOException;

import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.runtime.exception.HulaPlayerException;

/**
 * Responsible for running a Hula Executable
 */
public interface HulaPlayer
{
	/**
	 * Runs a Hula script 
	 * 
	 * @param scriptName The name of the script to run
	 * @param hctx The context to use during execution
	 * @throws HulaPlayerException
	 */
	void run(String scriptName, HulaContext hctx) throws HulaPlayerException, HulaParserException, IOException;
}
