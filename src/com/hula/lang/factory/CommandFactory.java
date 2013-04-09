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
package com.hula.lang.factory;

import java.io.IOException;

import com.hula.lang.factory.exception.CommandFactoryException;
import com.hula.lang.model.Command;

/**
 * A factory interface for creating {@link Command} instances
 */
public interface CommandFactory
{
	/**
	 * Instantiates a new instance of a command
	 * 
	 * @param id The id of the command to instantiate
	 * @return The Command instance
	 */
	Command getCommandInstance(String id) throws CommandFactoryException;
	
	/**
	 * Loads a property file containing Commands. Commands are defined
	 * in the following format:<br/><br/>
	 * 
	 * <code>Comment=com.hula.lang.commands.core.Comment</code><br/><br/>
	 * 
	 * Multiple command files can be loaded
	 * 
	 * @param filename The filename of the properties file
	 * @throws IOException 
	 */
	void loadCommands(String filename) throws IOException;
}
