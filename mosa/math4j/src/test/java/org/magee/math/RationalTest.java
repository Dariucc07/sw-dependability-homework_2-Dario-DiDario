package org.magee.math;

import org.evosuite.runtime.System;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.junit.runner.RunWith;
import org.magee.math.Rational;

public class RationalTest {
/*
*Ok, the assertEquals (with expected) value is wrong. Because:
* rational0 = -1 / -1
* rational1 = (rational0 - 1) = (-1 / -1 -1) = -2 / -1 = 2
* rational2 = -(rational1) = -2 this is the correct expectation value
*
* line fixed: 23
 */
  @Test
  public void test01()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.subtract((-1L));
      Rational rational2 = rational1.negate();
      assertEquals(-2.0, rational2.doubleValue(), 0.01);
  }
/*
*In this test there's a very big mathematical problem. abs() is a function that associates the same number with
* a positive sign to a negative number, it associates zero with zero and leaves the positive numbers unchanged.
* The absolute value of a number is therefore always positive or possibly null. Second problem is in the multiply function.
* the multiplication between two fractions is done by multiplying (numerator 0 * numerator 1 / denominator0 by denominator1)
* So the final result is:
* rational0 = -1/-1
* rational1 = -(-1)/-(-1) = 1/1
* rational2 = rational1 * rational0 = 1/1 * -1/-1 = 1/1 = 1
* The final assertion is wrong! expected value is = 1 and not -1
*
* line fixed: 44
 * */
  @Test
  public void test02()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.abs();
      Rational rational2 = rational1.multiply(rational0);
      assertEquals((-1L), rational2.numerator);
      assertEquals((byte) 1, rational1.byteValue());
  }
/*
*inverse() function and negate() function works good.Let's introduce step by step the execution with test value:
* rational0 = -2685 / -2685
* rational1 = -(-2685) / -2685
* rational2 = -2685 / -(-2685) = -2685 / 2685 = -1
* expected: numerator = -2685;
* denominator = 2685
* shortValue = -1
 */
  @Test
  public void test03()  throws Throwable  {
      Rational rational0 = new Rational((-2685L), (-2685L));
      Rational rational1 = rational0.negate();
      Rational rational2 = rational1.inverse();
      assertEquals(2685L, rational2.denominator);
      assertEquals((-2685L), rational0.numerator);
      assertEquals((short) (-1), rational2.shortValue());
  }
/*
*Let's follow the execution of the test: fist of all, create a Rational -1432 / 24840256
* change the denominator in 0, so : -1432 / 0 (already here, something wrong...)
* last operation is (-1432 / 0 ) * 1 .
 */
  @Test
  public void test04()  throws Throwable  {
      Rational rational0 = new Rational((-1432L), 24840256L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.multiply(1L);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }
/*
Automatically I can say that this test is wrong, because it use the divide method which call the multiply method (that
is absolutely wrong - as I said above). Let's define the real behavior of this test:
first create a rational0 = 667 / 1415
rational1 = rational0 / rational0 = (667/1415) / (667 / 1415)
(667 / 1415) * (1415 / 667) = 943.80 / 943.80 = 1, so the assertEquals on float0 should expect 1.0 not 0.0
 line fixed: 98
 */
  @Test
  public void test05()  throws Throwable  {
      Rational rational0 = new Rational(667L, 1415L);
      Rational rational1 = rational0.divide(rational0);
      float float0 = rational1.floatValue();
      assertEquals(1.0F, float0, 0.01F);
      assertEquals(0.4713781F, rational0.floatValue(), 0.01F);
      assertEquals(943805L, rational1.denominator);
  }
/*
*divide function is not completely wrong. Inside that we have the multiply that is wrong.
* The assertion works good. (it's just luck) because you can try with an example in which:
* numerator != 1 && (denominator != 1 && denominator != numerator) for example 2 / 3
* and we can asses that the result with this (wrong) divide function is not the same of the right one, as this test show.
*
 */
  @Test
  public void test06()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.divide(rational0);
      assertEquals((-1L), rational0.denominator);
      assertEquals(1L, rational1.longValue());
  }
/*
*pow method works good. Let's follow the execution:
* rational0 = 2 / 2
* rational1 = 2^0 / 2^0 = 1 / 1 = 1
 */
  @Test
  public void test07()  throws Throwable  {
      Rational rational0 = new Rational(2L, 2L);
      Rational rational1 = rational0.pow(0);
      assertEquals(1L, rational1.numerator);
      assertEquals((short)1, rational0.shortValue());
      assertEquals(1.0, rational1.doubleValue(), 0.01);
  }
/*
*There's an error in the add method because when you want to pass an integer to add at the fraction, the add
* method put as denominator always 0, instead i suggest to put 1 (because every number without explicit denominator has denominator with value 1)
*
 */
  @Test
  public void test08()  throws Throwable  {
      Rational rational0 = new Rational(1493L, 1493L);
      // Undeclared exception!
      try { 
        rational0.add(0L);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }
/*
*Let's follow the test'execution:
* rational0 = 0 / 2503
* rational0 = 0 / 0 (put 0 as denominator)
* substract(-4002) = it put -1 as denominator so: -4002 / -1 and negate all so: -(-4002 / -1)
* so finally is = 0 / 0 -(-4002 / -1)
* the method substract( long integer) has a bug.
 */
  @Test
  public void test09()  throws Throwable  {
      Rational rational0 = new Rational(0L, 2503L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.subtract((-4002L));
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }
/*
*let's follow the test's execution:
* ration0 = 1 / 1
* the test try to add a null value to 1 / 1
 */
  @Test
  public void test10()  throws Throwable  {
      Rational rational0 = new Rational(1L, 1L);
      // Undeclared exception!
      try { 
        rational0.add((Rational) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

  /* Some additional test case: I have notice that the abs() function was calculated only for one kind
  * of input. More in details, the input is (at Test02) with a Rational -1 / -1. We have studied that
  * it's really important to test different input, because (for example) an entire positive input could work
  * fine, but other input could fail the test. */
    @Test
    public void absTestWithOneNegativeAndOnePositive(){
        Rational r0 = new Rational(-3, 5);
        Rational r1 = r0.abs();
        assertEquals(3, r1.numerator);
        assertEquals(5, r1.denominator);
    }
    /*
    * As I said above, some function could work fine with a (for example) positive number but
    * could crash with a negative one. this test confirm that abs() calculation is not good for the denominator.
    * The test result says:" Expected : 5, Actual: -5
    */
    @Test
    public void absTestWithOnePositiveAndOneNegative(){
        Rational r0 = new Rational(3, -5);
        Rational r1 = r0.abs();
        assertEquals(3, r1.numerator);
        assertEquals(5, r1.denominator);
    }

    @Test
    public void absWithPositiveAndPositive(){
        Rational r0 = new Rational(3,5);
        Rational r1 = r0.abs();
        assertEquals(3, r1.numerator);
        assertEquals(5, r1.denominator);
    }
}
