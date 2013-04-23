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
package com.hula.lang;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hula.lang.factory.CommandFactory;
import com.hula.lang.factory.CommandFactoryImpl;
import com.hula.lang.parser.HulaParser;
import com.hula.lang.parser.HulaParserImpl;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.reader.ScriptReader;
import com.hula.lang.reader.ScriptReaderImpl;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.runtime.HulaPlayer;
import com.hula.lang.runtime.HulaPlayerImpl;
import com.hula.lang.runtime.exception.HulaPlayerException;

public class ScriptRunner
{
	private static Logger logger = LoggerFactory.getLogger(ScriptRunner.class);
	protected HulaParser parser = null;

	public static void main(String[] args)
	{
		new ScriptRunner(args);
	}

	public ScriptRunner(String[] args)
	{
		PropertyConfigurator.configure("conf/log4j.properties");
		for (String arg : args)
		{
			logger.info("parameter: " + arg);
		}
		String scriptName = args[0];
		String scriptPath = "test/scripts";
		if (scriptName.indexOf('/') != -1)
		{
			scriptPath = scriptName.substring(0, scriptName.lastIndexOf('/'));
			scriptName = scriptName.substring(scriptName.lastIndexOf('/') + 1);
		}
		try
		{
			CommandFactory commandFactory = new CommandFactoryImpl();
			commandFactory.loadCommands("conf/lang.commands.properties");
			parser = new HulaParserImpl(commandFactory);

			ScriptReader scriptReader = new ScriptReaderImpl(parser, scriptPath);

			HulaPlayer player = new HulaPlayerImpl(scriptReader);
			player.run(scriptName, new HulaContext());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (HulaParserException e)
		{
			e.printStackTrace();
		}
		catch (HulaPlayerException e)
		{
			System.out.println("error at " + e.getLineNumber());
			e.printStackTrace();
		}

	}

}
