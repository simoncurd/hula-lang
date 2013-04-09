package com.hula.lang.commands.core;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.util.ScriptWrapper;

public class ElseIfTests extends BaseHulaTestCase
{
	@Test
	public void testMissingTestFailsParserValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("ElseIf");
		wrapper.addLine("End");

		try
		{
			parseAndAssert(wrapper.toString());
			Assert.fail("script should fail validation");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			Assert.assertEquals("wrong number of errors : " + e.getErrors(), 1, e.getErrors().size());
			assertError(e, 0, 2, "ElseIf", "Test must be specified");
		}
	}

	@Test
	public void testInvalidTestFailsParserValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("ElseIf =");
		wrapper.addLine("End");

		try
		{
			parseAndAssert(wrapper.toString());
			Assert.fail("script should fail validation");
		}
		catch (HulaParserException e)
		{
			// expected behaviour
			Assert.assertEquals("wrong number of errors", 1, e.getErrors().size());
			assertError(e, 0, 2, "ElseIf =", "Test cannot be evaluated");
		}
	}
}
