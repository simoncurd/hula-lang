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
package com.hula.lang.commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hula.lang.commands.core.CommentTests;
import com.hula.lang.commands.core.ConditionalLogicTests;
import com.hula.lang.commands.core.EchoTests;
import com.hula.lang.commands.core.ElseIfTests;
import com.hula.lang.commands.core.ElseTests;
import com.hula.lang.commands.core.IfTests;
import com.hula.lang.commands.core.LoopTests;
import com.hula.lang.commands.core.OnFailTests;
import com.hula.lang.commands.core.SetTests;
import com.hula.lang.commands.data.AddJsonMapValueTests;
import com.hula.lang.commands.data.GetVariableNamesTests;
import com.hula.lang.commands.data.JsonToMapTests;
import com.hula.lang.commands.data.ListContainsTests;
import com.hula.lang.commands.data.MapToJsonTests;
import com.hula.lang.commands.data.NewListTests;
import com.hula.lang.commands.data.RemoveJsonMapValueTests;
import com.hula.lang.commands.data.SortTests;
import com.hula.lang.commands.date.DateToStringTests;
import com.hula.lang.commands.date.StringToDateTests;
import com.hula.lang.commands.string.AppendTests;
import com.hula.lang.commands.string.StringReplaceTests;
import com.hula.lang.commands.string.TokeniseTests;

@RunWith(Suite.class)
@SuiteClasses({
// core
CommentTests.class, ConditionalLogicTests.class, EchoTests.class, LoopTests.class,
//
OnFailTests.class, SetTests.class,

// core-conditional
IfTests.class, ElseIfTests.class, ElseTests.class,

// data
AddJsonMapValueTests.class, GetVariableNamesTests.class, JsonToMapTests.class,
//
ListContainsTests.class, MapToJsonTests.class, NewListTests.class,
//
RemoveJsonMapValueTests.class, SortTests.class,

// date
DateToStringTests.class, StringToDateTests.class,

// string
AppendTests.class, StringReplaceTests.class, TokeniseTests.class

})
public class AllCommandTests
{

}
