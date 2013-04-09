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
package com.hula.lang.parser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.hula.lang.model.Command;

/**
 * A data structure used during the parsing of a Hula script. 
 * Responsible for containing an object-based model of the script.
 */
public class CommandModel
{
	private List<Command> rootCommand = new ArrayList<Command>();
	private Stack<List<Command>> stk = new Stack<List<Command>>();

	/**
	 * Create a new CommandModel
	 */
	public CommandModel()
	{
		stk.add(rootCommand);
	}

	/**
	 * Add a Command to the CommandModel
	 * 
	 * @param command the Command to add to the model
	 */
	public void addCommand(Command command)
	{
		stk.peek().add(command);
	}

	/**
	 * Nest all additional commands added to the model under this parent command
	 * 
	 * @param command The command to nest further commands under 
	 */
	public void startNesting(Command command)
	{
		// add a reference to the child command list from this command to the stack
		stk.add(command.getCommands());
	}

	/**
	 * Stop nesting additional commands added to the model under the current 
	 * parent command
	 */
	public void stopNesting()
	{
		// remove the reference to the child command list from the stack
		stk.pop();
	}

	/**
	 * Checks whether the CommandModel is in a state where Commands added
	 * to the model will be nested beneath a parent Command
	 * 
	 * @return boolean indicating the nesting state of the command model
	 */
	public boolean isNesting()
	{
		return stk.size() > 1;
	}

	/**
	 * Get the parent command the model is currently nesting under
	 * 
	 * @return The Command the model is currently nesting under
	 */
	public Command getOpenCommand()
	{

		// get the second-from-last list on the stack
		if (stk.size() >= 2)
		{
			List<Command> list = stk.get(stk.size() - 2);

			// get the last command in the list
			if (list.size() >= 1)
			{
				return list.get(list.size() - 1);
			}
		}
		return null;
	}

	/**
	 * Get a list of parent Commands in the stack. Commands which are nested beneath
	 * these can be retrieved by calling getCommands() from the parent Command. 
	 * 
	 * @return a list of commands
	 */
	public List<Command> getCommands()
	{
		return rootCommand;
	}

	/**
	 * A helper method to find the last instance of a Command type at the current
	 * nesting level
	 * 
	 * @param clazz The class type of the Command to match 
	 * @return the index of the Command instance, or -1
	 */
	public int lastIndexOf(Class clazz)
	{
		// get the last Command list on the stack
		List<Command> thisLevel = this.stk.peek();

		// count backwards through this Command list 
		for (int index = thisLevel.size() - 1; index != -1; index--)
		{
			// check for a Command of the specified type
			Command c = thisLevel.get(index);
			if (c.getClass().equals(clazz))
			{
				// return it's position
				return index;
			}
		}
		
		// no match found
		return -1;
	}

	/**
	 * A helper method to check if the Command model is nesting under a
	 * Command of the specified type. For example, could be used to check if the
	 * Loop Command would be a parent of the next Command added to the model 
	 *  
	 * @param clazz The class type of the Command to match 
	 * @return boolean indicating if the class type is a parent
	 */
	public boolean nestedUnder(Class clazz)
	{
		// check we are nesting
		if (isNesting())
		{
			// reverse through the stack
			for (int index = stk.size() - 2; index != -1; index--)
			{
				// get the Command list
				List<Command> list = stk.get(index);

				// get the last Command in the list
				Command c = list.get(list.size() - 1);

				// check for a Command of the specified type
				if (c.getClass().equals(clazz))
				{
					return true;
				}
			}
		}
		
		// no match found
		return false;

	}

	/**
	 * Insert a Command at a specific index in the current Command list
	 * 
	 * @param command the command to insert
	 * @param index the position to insert the command
	 */
	public void insertCommand(Command command, int index)
	{
		List<Command> thisLevel = this.stk.peek();
		thisLevel.add(index, command);
	}

}
