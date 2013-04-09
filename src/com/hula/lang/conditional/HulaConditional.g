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
grammar HulaConditional;

options {
	language=Java;
	output=AST;
	ASTLabelType=CommonTree;
}

@header {
	package com.hula.lang.conditional;
}

@lexer::header {
	package com.hula.lang.conditional;
}

@parser::members {
	@Override
	protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException
	{
		throw new MismatchedTokenException(ttype, input);
	}
	@Override
	public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException
	{
		throw e;
	}

}

@rulecatch {
	catch (RecognitionException e) 
	{
		throw e;
	}
}

@lexer::members {
	@Override
	public void reportError(RecognitionException e) 
	{
		throw new RuntimeException(e);
	}
}

parse
	: expression EOF!;
	

term
	:	IDENT
	|	'('! expression ')'!
	|	STRING
	|	NUMBER
	|	BOOLEAN
	;

relation
	:	term ( ('='^ | '>'^ | '<'^ | '>='^ | '<='^ | '!='^ ) term)*
	;

expression
	:	relation  (('AND'^ | 'OR'^) relation)*
	;
	
BOOLEAN
	:	'true'
	|	'false'
	;


STRING	: '"' .* '"';


	
NUMBER
	: '0'..'9'+('.' '0'..'9'+)?
	;

IDENT
	: '\$' PROPERTYNAME ('.' PROPERTYNAME)*
	;
	
PROPERTYNAME
	:	('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*
	;

WS	:	(' ' | '\t' | '\n' | '\r' | '\f')+ { $channel = HIDDEN; };
