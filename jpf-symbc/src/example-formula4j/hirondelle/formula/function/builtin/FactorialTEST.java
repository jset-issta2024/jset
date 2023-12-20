package hirondelle.formula.function.builtin;

import hirondelle.formula.function.Function;

public final class FactorialTEST extends BaseTEST {
  
  public static void main(String args[]) {
    junit.textui.TestRunner.run(FactorialTEST.class);
  }

  public void testCases(){
    Function func = new Factorial();
    
    testWith(func, "1", "0");
    testWith(func, "1", "1");
    testWith(func, "2", "2");
    testWith(func, "24", "4");
    testWith(func, "306057512216440636035370461297268629388588804173576999416776741259476533176716867465515291422477573349939147888701726368864263907759003154226842927906974559841225476930271954604008012215776252176854255965356903506788725264321896264299365204576448830388909753943489625436053225980776521270822437639449120128678675368305712293681943649956460498166450227716500185176546469340112226034729724066333258583506870150169794168850353752137554910289126407157154830282284937952636580145235233156936482233436799254594095276820608062232812387383880817049600000000000000000000000000000000000000000000000000000000000000000000000000", "300");
    
  }
  

}