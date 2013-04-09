package com.hula.lang.commands.core;

import org.junit.Assert;
import org.junit.Test;

import com.hula.lang.BaseHulaTestCase;
import com.hula.lang.parser.HulaExecutable;
import com.hula.lang.runtime.HulaContext;
import com.hula.lang.util.ScriptWrapper;

public class ConditionalLogicTests extends BaseHulaTestCase
{
	@Test
	public void testIfHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If true");
		wrapper.addLine("   Set value=yes");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		evaluateBeanShell(parserResult, hctx);

		Assert.assertEquals("missing value", "yes", hctx.getParameter("value"));
	}

	@Test
	public void testIf_ElseHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If $in = 1 ");
		wrapper.addLine("   Set value=a");
		wrapper.addLine("Else");
		wrapper.addLine("   Set value=b");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("in", 1);

		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "a", hctx.getParameters().get("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", "");
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "b", hctx.getParameters().get("value"));
	}

	@Test
	public void testIf_ElseIfHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If $in = 1");
		wrapper.addLine("   Set value=a");
		wrapper.addLine("ElseIf $in = 2");
		wrapper.addLine("   Set value=b");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("in", 1);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "a", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", 2);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "b", hctx.getParameter("value"));
	}

	@Test
	public void testIf_ElseIf_ElseHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If $in = 1");
		wrapper.addLine("   Set value=a");
		wrapper.addLine("ElseIf $in = 2");
		wrapper.addLine("   Set value=b");
		wrapper.addLine("Else");
		wrapper.addLine("   Set value=c");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("in", 1);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "a", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", 2);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "b", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", "");
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "c", hctx.getParameter("value"));
	}

	@Test
	public void testIf_ElseIf_ElseIf_ElseHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If $in = 1");
		wrapper.addLine("   Set value=a");
		wrapper.addLine("ElseIf $in = 2");
		wrapper.addLine("   Set value=b");
		wrapper.addLine("ElseIf $in = 3");
		wrapper.addLine("   Set value=c");
		wrapper.addLine("Else");
		wrapper.addLine("   Set value=d");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// run
		HulaContext hctx = new HulaContext();
		hctx.setParameter("in", 1);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "a", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", 2);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "b", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", 3);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "c", hctx.getParameter("value"));

		hctx = new HulaContext();
		hctx.setParameter("in", "");
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("missing value", "d", hctx.getParameter("value"));
	}

	@Test
	public void testNestedIf_ElseIf_ElseIf_ElseHandling() throws Exception
	{
		ScriptWrapper wrapper = new ScriptWrapper();
		wrapper.addLine("If $start = 1");
		wrapper.addLine("   If $in = 1");
		wrapper.addLine("      Set value=a");
		wrapper.addLine("   ElseIf $in = 2");
		wrapper.addLine("      Set value=b");
		wrapper.addLine("   ElseIf $in = 3");
		wrapper.addLine("      Set value=c");
		wrapper.addLine("   Else");
		wrapper.addLine("      Set value=d");
		wrapper.addLine("   End");
		wrapper.addLine("ElseIf $start = 2");
		wrapper.addLine("   If $in = 5");
		wrapper.addLine("      Set value=e");
		wrapper.addLine("   ElseIf $in = 6");
		wrapper.addLine("      Set value=f");
		wrapper.addLine("   ElseIf $in = 7");
		wrapper.addLine("      Set value=g");
		wrapper.addLine("   Else");
		wrapper.addLine("      Set value=h");
		wrapper.addLine("   End");
		wrapper.addLine("ElseIf $start = 3");
		wrapper.addLine("   If $in = 9");
		wrapper.addLine("      Set value=i");
		wrapper.addLine("   ElseIf $in = 10");
		wrapper.addLine("      Set value=j");
		wrapper.addLine("   ElseIf $in = 11");
		wrapper.addLine("      Set value=k");
		wrapper.addLine("   Else");
		wrapper.addLine("      Set value=l");
		wrapper.addLine("   End");
		wrapper.addLine("Else");
		wrapper.addLine("   If $in = 13");
		wrapper.addLine("      Set value=m");
		wrapper.addLine("   ElseIf $in = 14");
		wrapper.addLine("      Set value=n");
		wrapper.addLine("   ElseIf $in = 15");
		wrapper.addLine("      Set value=o");
		wrapper.addLine("   Else");
		wrapper.addLine("      Set value=p");
		wrapper.addLine("   End");
		wrapper.addLine("End");

		// parse
		HulaExecutable parserResult = parseAndAssert(wrapper.toString());

		// test
		testNestedIf(parserResult, 1, 1, "a");
		testNestedIf(parserResult, 1, 2, "b");
		testNestedIf(parserResult, 1, 3, "c");
		testNestedIf(parserResult, 1, 4, "d");
		testNestedIf(parserResult, 2, 5, "e");
		testNestedIf(parserResult, 2, 6, "f");
		testNestedIf(parserResult, 2, 7, "g");
		testNestedIf(parserResult, 2, 8, "h");
		testNestedIf(parserResult, 3, 9, "i");
		testNestedIf(parserResult, 3, 10, "j");
		testNestedIf(parserResult, 3, 11, "k");
		testNestedIf(parserResult, 3, 12, "l");
		testNestedIf(parserResult, 4, 13, "m");
		testNestedIf(parserResult, 4, 14, "n");
		testNestedIf(parserResult, 4, 15, "o");
		testNestedIf(parserResult, 4, 16, "p");
	}

	private void testNestedIf(HulaExecutable parserResult, Object start, Object in, String value) throws Exception
	{
		HulaContext hctx = new HulaContext();
		hctx.setParameter("start", start);
		hctx.setParameter("in", in);
		evaluateBeanShell(parserResult, hctx);
		Assert.assertEquals("incorrect value", value, hctx.getParameter("value"));
	}

}
