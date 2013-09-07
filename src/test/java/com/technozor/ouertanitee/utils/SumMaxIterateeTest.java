package com.technozor.ouertanitee.utils;


import com.technozor.ouertanitee.Input;
import com.technozor.ouertanitee.Enumerator;
import com.technozor.ouertanitee.FutureUtils;
import com.technozor.ouertanitee.samples.MaxIteratee;
import com.technozor.ouertanitee.samples.SumIteratee;
import com.technozor.ouertanitee.Iteratee;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author ouertani
 */
@RunWith(JUnit4.class)
public class SumMaxIterateeTest  {
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void basicMaxSumer(){
        Input[] in1 =  {Input.el(5),Input.el(2),Input.EOF };
        Enumerator enumerator = Enumerator.enumInput(in1);
        Iteratee<Integer,Integer> maxer = new MaxIteratee(); 
        Iteratee<Integer,Integer>  summer = new SumIteratee(); 
        Input result1 = enumerator.run(summer);
        Input result2 = enumerator.run(maxer);
        org.junit.Assert.assertSame(result1.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result2.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result1).getE(), 7);
        org.junit.Assert.assertSame("should be same",((Input.El) result2).getE(), 5);
    }
 
    
       
    @Test
    public void complexeSumMaxer(){
        Input[] in1 =  {Input.el(5),Input.EMPTY ,Input.el(2),Input.EOF };
        Input[] in2 =  {Input.el(1),Input.EMPTY ,Input.el(4), Input.EMPTY , Input.el(3) , Input.EOF };
        Enumerator enumerator1 = Enumerator.enumInput(in1);
        Enumerator enumerator2 = Enumerator.enumInput(in2);
        Iteratee<Integer,Integer> maxer = new MaxIteratee(); 
        Iteratee<Integer,Integer> summer = new SumIteratee(); 
      
        
        Input result1 = enumerator1.run(summer);
        Input result2 = enumerator2.run(maxer);
         Input result3 = enumerator1.run(maxer);
        Input result4 = enumerator2.run(summer);
      
        org.junit.Assert.assertSame(result1.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result2.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result3.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result4.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result1).getE(), 7);
        org.junit.Assert.assertSame("should be same",((Input.El) result2).getE(), 4);
        org.junit.Assert.assertSame("should be same",((Input.El) result3).getE(), 5);
        org.junit.Assert.assertSame("should be same",((Input.El) result4).getE(), 8);
    }
    
}
