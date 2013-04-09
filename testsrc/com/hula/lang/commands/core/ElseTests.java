package com.hula.lang.commands.core;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.exception.HulaParserException;
import com.hula.lang.util.ScriptWrapper;

public class ElseTests extends BaseHulaTestCase
{
	@Test
	public void testInvalidParametersFailsParserValidation() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("Else false");
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
			assertError(e, 0, 2, "Else false", "No valid parameters for command");
		}
	}
}
