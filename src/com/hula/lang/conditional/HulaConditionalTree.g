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
 tree grammar HulaConditionalTree;

options {
	language=Java;
	tokenVocab=HulaConditional;
	ASTLabelType=CommonTree;
}

@header {
	package com.hula.lang.conditional;
	
	import com.hula.lang.runtime.RuntimeConnector;
	import java.math.BigDecimal;
}

@members {

	private ThreadLocal<RuntimeConnector> rcContainer = new ThreadLocal<RuntimeConnector>();
	public void setRuntimeConnector(RuntimeConnector connector)
	{
		rcContainer.set(connector);
	}
	private RuntimeConnector getRuntimeConnector()
	{
		return rcContainer.get();
	}
	
}


test returns [Object result]
	:	e=expression EOF { result = e; } 
	;

expression returns [Object result]
	:	^('AND' op1=expression op2=expression) { result = OperatorUtil.testAND(op1, op2, getRuntimeConnector()); }
	|	^('OR' op1=expression op2=expression) { result = OperatorUtil.testOR(op1, op2, getRuntimeConnector()); }
	|	^('=' op1=expression op2=expression) { result = OperatorUtil.testEquals(op1, op2, getRuntimeConnector()); }
	|	^('>' op1=expression op2=expression) { result = OperatorUtil.testGreaterThan(op1, op2, getRuntimeConnector()); }
	|	^('<' op1=expression op2=expression) { result = OperatorUtil.testLessThan(op1, op2, getRuntimeConnector()); }
	|	^('>=' op1=expression op2=expression) { result = OperatorUtil.testGreaterThanEqualTo(op1, op2, getRuntimeConnector()); }
	|	^('<=' op1=expression op2=expression) { result = OperatorUtil.testLessThanEqualTo(op1, op2, getRuntimeConnector()); }
	|	^('!=' op1=expression op2=expression) { result = OperatorUtil.testNotEquals(op1, op2, getRuntimeConnector()); }
	|	IDENT { result = new VariableReference($IDENT.text); }
	|	STRING { result = new String($STRING.text); }
	|	NUMBER { result = new BigDecimal($NUMBER.text); }
	|	BOOLEAN { result = new Boolean($BOOLEAN.text); }
	|	NULL { result = null; }
	;
	
	
	
	
	