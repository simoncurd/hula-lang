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
package com.hula.lang.conditional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.hula.lang.conditional.expression.AndTests;
import com.hula.lang.conditional.expression.EqualsTests;
import com.hula.lang.conditional.expression.ExpressionTests;
import com.hula.lang.conditional.expression.GreaterThanEqualToTests;
import com.hula.lang.conditional.expression.GreaterThanTests;
import com.hula.lang.conditional.expression.LessThanEqualToTests;
import com.hula.lang.conditional.expression.LessThanTests;
import com.hula.lang.conditional.expression.NotEqualsTests;
import com.hula.lang.conditional.expression.OrTests;
import com.hula.lang.conditional.term.BooleanParsingTests;
import com.hula.lang.conditional.term.NumberParsingTests;
import com.hula.lang.conditional.term.StringParsingTests;
import com.hula.lang.conditional.term.VariableReferenceParsingTests;

@RunWith(Suite.class)
@SuiteClasses({
// Conditional
AndTests.class, //
OrTests.class,

// Equality
EqualsTests.class, //
NotEqualsTests.class,

// Relational
GreaterThanEqualToTests.class, //
GreaterThanTests.class, //
LessThanTests.class, //
LessThanEqualToTests.class,

// Expressions
ExpressionTests.class,

// Terms
BooleanParsingTests.class, //
StringParsingTests.class, //
VariableReferenceParsingTests.class, //
NumberParsingTests.class })
public class AllConditionalTests
{

}
