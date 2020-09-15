package org.apache.commons.cli;

import org.evosuite.annotations.EvoSuiteTest;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.cli.Option;
import org.junit.runner.RunWith;

public class OptionTest {
/*
* the method hasArgName() return true if the option is a not null AND the lenght is graten or equal to 0.
* in this case if we put an empty string the test will pass. Probably the best solution is to change from
* assertTrue() to assertFalse().
*
* line fixed: 22
 */
  @Test
  public void test01()  throws Throwable  {
      Option option0 = new Option("", "");
      option0.setArgName("");
      boolean boolean0 = option0.hasArgName();
      assertFalse(boolean0);
  }
/*
*the method getId() return the id of the option, but it returns the second character (in the getId() function
* there is getKey().charAt(1) ). I think that it's not correct. Other assertion work as expected.
*
* A good practice during the test case development is to have single assertion for single test case.
* However, it's also correct, because of smell. (in my opinion).
*
* line fixed: 41
*
 */
  @Test
  public void test02()  throws Throwable  {
      Option option0 = new Option("arg", "arg");
      int int0 = option0.getId();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
      assertEquals('a', int0);
  }
/*
*Ok, probably the assertions on getArgName() and hasArgs() works correctly, but we want to test option0.getKey()
* more specific, we want to understand if opt is a null value or not. Probably we have to check with another assertion (i think that both
* assertEquals and assertNull works) if opt is null.
* N.B: both lines 54 and 55 have the same behavior. These are 2 possible solution.
* lines fixed: 55, 56.
 */
  @Test
  public void test03()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasArgs());
      assertEquals((String) null ,option0.getKey() );
      assertNull(option0.getKey());
  }
  /*
  *all the assertion works fine. for the last assertion, is equal to one because in the costructor with 4 parameter there's
  * a statement in which: if hasArgs is true, so put in the variable "numberOfArg = 1".
   */
  @Test
  public void test04()  throws Throwable  {
      Option option0 = new Option("NXb", true, "NXb");
      //it's true, see the costruction above.
      boolean boolean0 = option0.hasArgs();
      //it's equals because the initialization og argName = "arg"
      assertEquals("arg", option0.getArgName());
      //It's true because longOpt == null
      assertFalse(option0.hasLongOpt());
      //It's true, see the costructor above.
      assertTrue(boolean0);
      //It's equal to one because
      assertEquals(1, option0.getArgs());
  }
/*
*we want to test equal method. The first option is istantiate with only 2 parameters (opt and description)
* longOpt is null and hasArgs is false. In the second one option1 is istantiate with another costructor with
* 4 parameter, so opt is "", longOpt is "", hasArg is false and description is "". The object seems to be equal,
* but they are not. in option0 longOpt is null and in option1 is "".
*
* line fixed: 95
 */
  @Test
  public void test05()  throws Throwable  {
      Option option0 = new Option("", "");
      Option option1 = new Option("", "", false, "");
      boolean boolean0 = option0.equals(option1);
      //Works fine, because it's always initialize with "arg"
      assertEquals("arg", option1.getArgName());
      //It's true (that is false) because the value is null.
      assertFalse(option0.hasLongOpt());
      //It's true (that is false) because in the costructor we pass false
      assertFalse(option1.hasArgs());
      assertFalse(boolean0);
  }
/*
*also in this case the two object are not equal. in option0 longOpt is "", in option1 is null.
* line fixed: 108
 */
  @Test
  public void test06()  throws Throwable  {
      Option option0 = new Option((String) null, "", false, "");
      Option option1 = new Option((String) null, "");
      boolean boolean0 = option0.equals(option1);
      //It's true, see the first costructor
      assertTrue(option0.hasLongOpt());

      assertFalse(boolean0);
      //It's true, always initializate as "arg"
      assertEquals("arg", option1.getArgName());
      //It's true (that is false).
      assertFalse(option1.hasLongOpt());
      //It's true (that is false)
      assertFalse(option1.hasArgs());
  }
/*
*works fine.
 */
  @Test
  public void test07()  throws Throwable  {
      Option option0 = new Option("", "Pz");
      Option option1 = new Option("Pz", "");
      boolean boolean0 = option0.equals(option1);
      assertFalse(boolean0);
      assertEquals((-1), option1.getArgs());
      assertFalse(option1.hasLongOpt());
      assertEquals("", option1.getDescription());
      assertEquals("Pz", option1.getOpt());
      assertEquals("arg", option1.getArgName());
  }
/*
*Works fine
 */
  @Test
  public void test08()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      boolean boolean0 = option0.hasArg();
      //Works fine
      assertFalse(boolean0);
      //Works fine.
      assertEquals((-1), option0.getArgs());
      assertEquals("arg", option0.getArgName());
  }
/*
*Works fine.
 */
  @Test
  public void test09()  throws Throwable  {
      Option option0 = new Option("", "", true, "");
      Option option1 = (Option)option0.clone();
      boolean boolean0 = option0.equals(option1);
      assertEquals("arg", option1.getArgName());
      assertTrue(boolean0);
      assertTrue(option1.hasArg());
      assertNotSame(option1, option0);
  }
/*
* ok, here the assertions works fine, but actually, the aim of this TC is to test the getOpt() method.
* There is no assertion on this method
 */
  @Test
  public void test10()  throws Throwable  {
      Option option0 = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
      String opt = option0.getOpt();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
      assertEquals("Expectation: \"NO_ARGS_ALLOWED\"", "NO_ARGS_ALLOWED", opt);
  }
/* TIME TO ADD SOME NEW TEST IN ORDER TO COMPLETE THE TEST SUITE.
I'm trying to stay in the same order of the method declaration in the class Option.java
*/

/* AT THE END OF THE SENTENCE THERE'S A SPOILER :)
*In this case we have to test different type of input value. So the opt (getId method, call a getKey method that use opt variable and finally use charAt(index))
* is a String value, so we have to test different character. Also because charAt method, want an index, so we have to control that the index was right and not OutOfBound.
 */
    @Test
    public void clearTest(){
        Option option0 = new Option("","");
        option0.clearValues();
        assertNull(option0.getValues());
    }

  @Test
  public void getIdTestSingleChar(){
      Option option0 = new Option("d", "");
      int n = option0.getId();
      assertEquals("Expected value: \"d\" ", "d", n);
  }

  @Test
    public void getIdTestTwoOrMoreChar(){
      Option option0 = new Option("dario","");
      int n = option0.getId();
      assertEquals("Expected value: \"d\" ", "d", n);
  }

  /*during the comprension of the test results, I've notice that equals() doesn't work as expected. Simply because
  return true even when the object are different. (see TC5 and explanation above the method).
   */
  @Test
  public void equalTest(){
      Option option0 = new Option("d","short",false,"");
      Option option1 = new Option("d", "longOptionInsert", false, "");
      boolean result = option0.equals(option1);
      assertFalse(result);
  }

  /*I need to write another test on equals method, because there are 3 different costructor in the Option class, and one of them
  * take the longOpt as null, so I want to test also di case.*/
  @Test
  public void equalsTestWithNull(){
      Option option0 = new Option("d", "short", false, "");
      Option option1 = new Option("d",(String) null, false, "");
      boolean result = option0.equals(option1);
      assertFalse(result);
  }
/*The method addValueForProcessing() it's not a tested method. Thus, here is the test.
This method and test method is different from the other, because we have to manage the exception in the case in which
the numberOfArgs is still UNINITIALIZED. Starting from that:
 */
  @Test
  public void addValueForProcessingTest(){
      Option option0 = new Option("d", "");
      assertThrows(RuntimeException.class, () ->{ option0.addValueForProcessing("");});
  }

  @Test
    public void addValueForProcessingOneValue(){
      //with this: hasArg = true, the numberOfArgs is default 1.
      Option option0 = new Option("prova","", true, "" );
      option0.addValueForProcessing("something");
      assertTrue(option0.getValue().length() > 0);
  }
/*
* Here the problem is not the addValueProcessing() method, because there are no match for switch(numberOfArgs),
* so in this case the selected statement is the default in which calls the processValue() method and in the end try to
* add the value passed as parameter. So in my opinion the real bug is in the add() method because add() accept every value
* even when numberOfArgs is 0.
 */
  @Test
    public void addValueForProcessingZeroArg(){
      Option option0 = new Option("","");
      option0.setArgs(0);
      assertThrows(RuntimeException.class, () -> {option0.addValueForProcessing("");});
  }
}
