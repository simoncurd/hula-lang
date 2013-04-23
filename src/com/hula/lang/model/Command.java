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
package com.hula.lang.model;

import java.util.List;
import java.util.Map;

import com.hula.lang.parser.exception.ParseError;
import com.hula.lang.parser.model.BeanShellScript;
import com.hula.lang.parser.model.CommandModel;
import com.hula.lang.runtime.RuntimeConnector;

/**
 * An interface to define the methods required by Commands in the
 * Hula language.
 * 
 * Command instances are used to model Hula command lines at parse-time, and
 * optionally also at run-time (depending on implementation).
 */
public interface Command
{
	/**
	 * Set the signature parameters for this Command
	 * 
	 * @param parameters a Map of signature parameters
	 */
	void setSignatureParameters(Map<String, String> parameters);

	/**
	 * Set a signature parameter for this Command
	 * 
	 * @param name The name of the parameter
	 * @param value The value of the parameter
	 */
	void setSignatureParameter(String name, String value);

	/**
	 * Get the signature parameters from the Command
	 * 
	 * @return a Map of signature parameters
	 */
	Map<String, String> getSignatureParameters();

	/**
	 * Get a signature parameter value by name
	 * 
	 * @param name The name of the parameter to return
	 * @return the value of the signature parameter
	 */
	String getSignatureParameter(String name);

	/**
	 * Set the name of the return parameter for the Command. Called by BeanShell
	 * when configuring an executable command
	 * 
	 * @param returnValue The name of the return parameter
	 */
	void setReturnParameter(String returnValue);

	/**
	 * Get the name of the return parameter for the command.
	 * 
	 * @return The name of the return parameter
	 */
	String getReturnParameter();

	/**
	 * Get the original Hula script command line for this Command
	 * 
	 * @return The original Hula script command line for this Command
	 */
	String getCommandLine();

	/**
	 * Set the original Hula script command line for this Command
	 * 
	 * @param commandLine The original Hula script command line for this Command
	 */
	void setCommandLine(String commandLine);

	/**
	 * Set the original Hula script line number
	 * 
	 * @param lineNumber The original Hula script line number
	 */
	void setLineNumber(int lineNumber);

	/**
	 * Get the original Hula script line number
	 * 
	 * @return The original Hula script line number
	 */
	int getLineNumber();

	/**
	 * Get the Commands nested underneath this Command
	 * 
	 * @return The Commands nested underneath this Command
	 */
	List<Command> getCommands();

	/**
	 * Set the Commands nested underneath this Command
	 * 
	 * @param commands The Commands nested underneath this Command
	 */
	void setCommands(List<Command> commands);

	/**
	 * Execute this Command using the specified RuntimeConnector
	 * 
	 * @param connector The connector to run this Command with
	 */
	void execute(RuntimeConnector connector);

	/**
	 * Update the Command Model to include a reference to this Command
	 * 
	 * @param commandModel The Command Model to update
	 * @throws ParseError errors during the update process
	 */
	void updateCommandModel(CommandModel commandModel) throws ParseError;

	/**
	 * Set the identifier of this instance of the Command
	 * 
	 * @param instanceId The identifier of this instance of the Command
	 */
	void setInstanceId(String instanceId);

	/**
	 * Update the BeanShell script to include this Command
	 * 
	 * @param script The BeanShell script to update
	 */
	void updateBeanShellScript(BeanShellScript script);

	/**
	 * Run any Command-specific validation on the configuration of this Command
	 * 
	 * @param errors A list of errors to update based on the validation
	 */
	void validate(List<ParseError> errors);
}
