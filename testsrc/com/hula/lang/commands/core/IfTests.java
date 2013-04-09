package com.hula.lang.commands.core;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.util.ScriptWrapper;

public class IfTests extends BaseHulaTestCase
{
	@Test
	public void testMissingTestFailsParserValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If");
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
			assertError(e, 0, 1, "If", "Test must be specified");
		}
	}

	@Test
	public void testInvalidTestFailsParserValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If =");
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
			assertError(e, 0, 1, "If =", "Test cannot be evaluated");
		}
	}

	@Test
	public void testIf() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("End");

		try
		{
			parseAndAssert(wrapper.toString());
		}
		catch (HulaParserException e)
		{
			System.out.println(e.getErrors());
			Assert.fail("error parsing script: " + e.getErrors());
		}

	}
}
