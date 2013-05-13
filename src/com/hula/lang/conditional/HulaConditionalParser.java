// $ANTLR 3.5 com/hula/lang/conditional/HulaConditional.g 2013-05-13 17:03:04

	package com.hula.lang.conditional;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


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
@SuppressWarnings("all")
public class HulaConditionalParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "BOOLEAN", "IDENT", "NULL", "NUMBER", 
		"PROPERTYNAME", "STRING", "WS", "'!='", "'('", "')'", "'<'", "'<='", "'='", 
		"'>'", "'>='", "'AND'", "'OR'"
	};
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int BOOLEAN=4;
	public static final int IDENT=5;
	public static final int NULL=6;
	public static final int NUMBER=7;
	public static final int PROPERTYNAME=8;
	public static final int STRING=9;
	public static final int WS=10;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public HulaConditionalParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public HulaConditionalParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return HulaConditionalParser.tokenNames; }
	@Override public String getGrammarFileName() { return "com/hula/lang/conditional/HulaConditional.g"; }


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



	public static class parse_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "parse"
	// com/hula/lang/conditional/HulaConditional.g:61:1: parse : expression EOF !;
	public final HulaConditionalParser.parse_return parse() throws RecognitionException {
		HulaConditionalParser.parse_return retval = new HulaConditionalParser.parse_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token EOF2=null;
		ParserRuleReturnScope expression1 =null;

		CommonTree EOF2_tree=null;

		try {
			// com/hula/lang/conditional/HulaConditional.g:62:2: ( expression EOF !)
			// com/hula/lang/conditional/HulaConditional.g:62:4: expression EOF !
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_expression_in_parse75);
			expression1=expression();
			state._fsp--;

			adaptor.addChild(root_0, expression1.getTree());

			EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_parse77); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

			catch (RecognitionException e) 
			{
				throw e;
			}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "parse"


	public static class term_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "term"
	// com/hula/lang/conditional/HulaConditional.g:65:1: term : ( IDENT | '(' ! expression ')' !| STRING | NUMBER | BOOLEAN | NULL );
	public final HulaConditionalParser.term_return term() throws RecognitionException {
		HulaConditionalParser.term_return retval = new HulaConditionalParser.term_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token IDENT3=null;
		Token char_literal4=null;
		Token char_literal6=null;
		Token STRING7=null;
		Token NUMBER8=null;
		Token BOOLEAN9=null;
		Token NULL10=null;
		ParserRuleReturnScope expression5 =null;

		CommonTree IDENT3_tree=null;
		CommonTree char_literal4_tree=null;
		CommonTree char_literal6_tree=null;
		CommonTree STRING7_tree=null;
		CommonTree NUMBER8_tree=null;
		CommonTree BOOLEAN9_tree=null;
		CommonTree NULL10_tree=null;

		try {
			// com/hula/lang/conditional/HulaConditional.g:66:2: ( IDENT | '(' ! expression ')' !| STRING | NUMBER | BOOLEAN | NULL )
			int alt1=6;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt1=1;
				}
				break;
			case 12:
				{
				alt1=2;
				}
				break;
			case STRING:
				{
				alt1=3;
				}
				break;
			case NUMBER:
				{
				alt1=4;
				}
				break;
			case BOOLEAN:
				{
				alt1=5;
				}
				break;
			case NULL:
				{
				alt1=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// com/hula/lang/conditional/HulaConditional.g:66:4: IDENT
					{
					root_0 = (CommonTree)adaptor.nil();


					IDENT3=(Token)match(input,IDENT,FOLLOW_IDENT_in_term89); 
					IDENT3_tree = (CommonTree)adaptor.create(IDENT3);
					adaptor.addChild(root_0, IDENT3_tree);

					}
					break;
				case 2 :
					// com/hula/lang/conditional/HulaConditional.g:67:4: '(' ! expression ')' !
					{
					root_0 = (CommonTree)adaptor.nil();


					char_literal4=(Token)match(input,12,FOLLOW_12_in_term94); 
					pushFollow(FOLLOW_expression_in_term97);
					expression5=expression();
					state._fsp--;

					adaptor.addChild(root_0, expression5.getTree());

					char_literal6=(Token)match(input,13,FOLLOW_13_in_term99); 
					}
					break;
				case 3 :
					// com/hula/lang/conditional/HulaConditional.g:68:4: STRING
					{
					root_0 = (CommonTree)adaptor.nil();


					STRING7=(Token)match(input,STRING,FOLLOW_STRING_in_term105); 
					STRING7_tree = (CommonTree)adaptor.create(STRING7);
					adaptor.addChild(root_0, STRING7_tree);

					}
					break;
				case 4 :
					// com/hula/lang/conditional/HulaConditional.g:69:4: NUMBER
					{
					root_0 = (CommonTree)adaptor.nil();


					NUMBER8=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_term110); 
					NUMBER8_tree = (CommonTree)adaptor.create(NUMBER8);
					adaptor.addChild(root_0, NUMBER8_tree);

					}
					break;
				case 5 :
					// com/hula/lang/conditional/HulaConditional.g:70:4: BOOLEAN
					{
					root_0 = (CommonTree)adaptor.nil();


					BOOLEAN9=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_term115); 
					BOOLEAN9_tree = (CommonTree)adaptor.create(BOOLEAN9);
					adaptor.addChild(root_0, BOOLEAN9_tree);

					}
					break;
				case 6 :
					// com/hula/lang/conditional/HulaConditional.g:71:4: NULL
					{
					root_0 = (CommonTree)adaptor.nil();


					NULL10=(Token)match(input,NULL,FOLLOW_NULL_in_term120); 
					NULL10_tree = (CommonTree)adaptor.create(NULL10);
					adaptor.addChild(root_0, NULL10_tree);

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

			catch (RecognitionException e) 
			{
				throw e;
			}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "term"


	public static class relation_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relation"
	// com/hula/lang/conditional/HulaConditional.g:74:1: relation : term ( ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^) term )* ;
	public final HulaConditionalParser.relation_return relation() throws RecognitionException {
		HulaConditionalParser.relation_return retval = new HulaConditionalParser.relation_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal12=null;
		Token char_literal13=null;
		Token char_literal14=null;
		Token string_literal15=null;
		Token string_literal16=null;
		Token string_literal17=null;
		ParserRuleReturnScope term11 =null;
		ParserRuleReturnScope term18 =null;

		CommonTree char_literal12_tree=null;
		CommonTree char_literal13_tree=null;
		CommonTree char_literal14_tree=null;
		CommonTree string_literal15_tree=null;
		CommonTree string_literal16_tree=null;
		CommonTree string_literal17_tree=null;

		try {
			// com/hula/lang/conditional/HulaConditional.g:75:2: ( term ( ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^) term )* )
			// com/hula/lang/conditional/HulaConditional.g:75:4: term ( ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^) term )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_term_in_relation131);
			term11=term();
			state._fsp--;

			adaptor.addChild(root_0, term11.getTree());

			// com/hula/lang/conditional/HulaConditional.g:75:9: ( ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^) term )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==11||(LA3_0 >= 14 && LA3_0 <= 18)) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// com/hula/lang/conditional/HulaConditional.g:75:11: ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^) term
					{
					// com/hula/lang/conditional/HulaConditional.g:75:11: ( '=' ^| '>' ^| '<' ^| '>=' ^| '<=' ^| '!=' ^)
					int alt2=6;
					switch ( input.LA(1) ) {
					case 16:
						{
						alt2=1;
						}
						break;
					case 17:
						{
						alt2=2;
						}
						break;
					case 14:
						{
						alt2=3;
						}
						break;
					case 18:
						{
						alt2=4;
						}
						break;
					case 15:
						{
						alt2=5;
						}
						break;
					case 11:
						{
						alt2=6;
						}
						break;
					default:
						NoViableAltException nvae =
							new NoViableAltException("", 2, 0, input);
						throw nvae;
					}
					switch (alt2) {
						case 1 :
							// com/hula/lang/conditional/HulaConditional.g:75:12: '=' ^
							{
							char_literal12=(Token)match(input,16,FOLLOW_16_in_relation136); 
							char_literal12_tree = (CommonTree)adaptor.create(char_literal12);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal12_tree, root_0);

							}
							break;
						case 2 :
							// com/hula/lang/conditional/HulaConditional.g:75:19: '>' ^
							{
							char_literal13=(Token)match(input,17,FOLLOW_17_in_relation141); 
							char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal13_tree, root_0);

							}
							break;
						case 3 :
							// com/hula/lang/conditional/HulaConditional.g:75:26: '<' ^
							{
							char_literal14=(Token)match(input,14,FOLLOW_14_in_relation146); 
							char_literal14_tree = (CommonTree)adaptor.create(char_literal14);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal14_tree, root_0);

							}
							break;
						case 4 :
							// com/hula/lang/conditional/HulaConditional.g:75:33: '>=' ^
							{
							string_literal15=(Token)match(input,18,FOLLOW_18_in_relation151); 
							string_literal15_tree = (CommonTree)adaptor.create(string_literal15);
							root_0 = (CommonTree)adaptor.becomeRoot(string_literal15_tree, root_0);

							}
							break;
						case 5 :
							// com/hula/lang/conditional/HulaConditional.g:75:41: '<=' ^
							{
							string_literal16=(Token)match(input,15,FOLLOW_15_in_relation156); 
							string_literal16_tree = (CommonTree)adaptor.create(string_literal16);
							root_0 = (CommonTree)adaptor.becomeRoot(string_literal16_tree, root_0);

							}
							break;
						case 6 :
							// com/hula/lang/conditional/HulaConditional.g:75:49: '!=' ^
							{
							string_literal17=(Token)match(input,11,FOLLOW_11_in_relation161); 
							string_literal17_tree = (CommonTree)adaptor.create(string_literal17);
							root_0 = (CommonTree)adaptor.becomeRoot(string_literal17_tree, root_0);

							}
							break;

					}

					pushFollow(FOLLOW_term_in_relation166);
					term18=term();
					state._fsp--;

					adaptor.addChild(root_0, term18.getTree());

					}
					break;

				default :
					break loop3;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

			catch (RecognitionException e) 
			{
				throw e;
			}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relation"


	public static class expression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// com/hula/lang/conditional/HulaConditional.g:78:1: expression : relation ( ( 'AND' ^| 'OR' ^) relation )* ;
	public final HulaConditionalParser.expression_return expression() throws RecognitionException {
		HulaConditionalParser.expression_return retval = new HulaConditionalParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token string_literal20=null;
		Token string_literal21=null;
		ParserRuleReturnScope relation19 =null;
		ParserRuleReturnScope relation22 =null;

		CommonTree string_literal20_tree=null;
		CommonTree string_literal21_tree=null;

		try {
			// com/hula/lang/conditional/HulaConditional.g:79:2: ( relation ( ( 'AND' ^| 'OR' ^) relation )* )
			// com/hula/lang/conditional/HulaConditional.g:79:4: relation ( ( 'AND' ^| 'OR' ^) relation )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_relation_in_expression179);
			relation19=relation();
			state._fsp--;

			adaptor.addChild(root_0, relation19.getTree());

			// com/hula/lang/conditional/HulaConditional.g:79:14: ( ( 'AND' ^| 'OR' ^) relation )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= 19 && LA5_0 <= 20)) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// com/hula/lang/conditional/HulaConditional.g:79:15: ( 'AND' ^| 'OR' ^) relation
					{
					// com/hula/lang/conditional/HulaConditional.g:79:15: ( 'AND' ^| 'OR' ^)
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==19) ) {
						alt4=1;
					}
					else if ( (LA4_0==20) ) {
						alt4=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 4, 0, input);
						throw nvae;
					}

					switch (alt4) {
						case 1 :
							// com/hula/lang/conditional/HulaConditional.g:79:16: 'AND' ^
							{
							string_literal20=(Token)match(input,19,FOLLOW_19_in_expression184); 
							string_literal20_tree = (CommonTree)adaptor.create(string_literal20);
							root_0 = (CommonTree)adaptor.becomeRoot(string_literal20_tree, root_0);

							}
							break;
						case 2 :
							// com/hula/lang/conditional/HulaConditional.g:79:25: 'OR' ^
							{
							string_literal21=(Token)match(input,20,FOLLOW_20_in_expression189); 
							string_literal21_tree = (CommonTree)adaptor.create(string_literal21);
							root_0 = (CommonTree)adaptor.becomeRoot(string_literal21_tree, root_0);

							}
							break;

					}

					pushFollow(FOLLOW_relation_in_expression193);
					relation22=relation();
					state._fsp--;

					adaptor.addChild(root_0, relation22.getTree());

					}
					break;

				default :
					break loop5;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

			catch (RecognitionException e) 
			{
				throw e;
			}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"

	// Delegated rules



	public static final BitSet FOLLOW_expression_in_parse75 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_parse77 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_term89 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_term94 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_expression_in_term97 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_term99 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_term105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NUMBER_in_term110 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOLEAN_in_term115 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NULL_in_term120 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_term_in_relation131 = new BitSet(new long[]{0x000000000007C802L});
	public static final BitSet FOLLOW_16_in_relation136 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_17_in_relation141 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_14_in_relation146 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_18_in_relation151 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_15_in_relation156 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_11_in_relation161 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_term_in_relation166 = new BitSet(new long[]{0x000000000007C802L});
	public static final BitSet FOLLOW_relation_in_expression179 = new BitSet(new long[]{0x0000000000180002L});
	public static final BitSet FOLLOW_19_in_expression184 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_20_in_expression189 = new BitSet(new long[]{0x00000000000012F0L});
	public static final BitSet FOLLOW_relation_in_expression193 = new BitSet(new long[]{0x0000000000180002L});
}
