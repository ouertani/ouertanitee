package com.technozor.ouertanitee.utils;


import com.technozor.ouertanitee.Input;
import com.technozor.ouertanitee.Enumerator;
import com.technozor.ouertanitee.FutureUtils;
import com.technozor.ouertanitee.samples.MaxIteratee;
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
public class MaxIterateeTest  {
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
    public void basicMaxer(){
        Input[] in1 =  {Input.el(5),Input.el(2),Input.EOF };
        Enumerator enumerator = Enumerator.enumInput(in1);
        Iteratee<Integer,Integer>summer = new MaxIteratee(); 
        Input result = enumerator.run(summer);
        org.junit.Assert.assertSame(result.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result).getE(), 5);
    }
    
     @Test
    public void mutiRun(){
        Input[] in1 =  {Input.el(5),Input.el(2),Input.EOF };
        
        Enumerator enumerator = Enumerator.enumInput(in1);
        Iteratee<Integer,Integer> summer = new MaxIteratee(); 
        enumerator.run(summer);
        Input result = enumerator.run(summer);
        enumerator.run(summer);
        org.junit.Assert.assertSame(result.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result).getE(), 5);
    }
     
    @Test
    public void biEnumerator(){
        Input[] in1 =  {Input.el(5),Input.el(2),Input.EOF };
        Input[] in2 =  {Input.el(1),Input.el(4), Input.el(3) , Input.EOF };
        Enumerator enumerator1 = Enumerator.enumInput(in1);
        Enumerator enumerator2 = Enumerator.enumInput(in2);
        Iteratee<Integer,Integer> summer1 = new MaxIteratee(); 
        
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        
        Input result1 = enumerator1.run(summer1);
        Input result2 = enumerator2.run(summer1);
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        org.junit.Assert.assertSame(result1.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result2.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result1).getE(), 5);
        org.junit.Assert.assertSame("should be same",((Input.El) result2).getE(), 4);
    }
    
    @Test
    public void skipEmpty(){
        Input[] in1 =  {Input.el(5),Input.EMPTY ,Input.el(2),Input.EOF, Input.el(100)  };
        Input[] in2 =  {Input.el(1),Input.EMPTY ,Input.el(4), Input.EMPTY , Input.el(3) , Input.EOF, Input.el(100)  };
        Enumerator enumerator1 = Enumerator.enumInput(in1);
        Enumerator enumerator2 = Enumerator.enumInput(in2);
        Iteratee<Integer,Integer> summer1 = new MaxIteratee(); 
        
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        
        Input result1 = enumerator1.run(summer1);
        Input result2 = enumerator2.run(summer1);
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        org.junit.Assert.assertSame(result1.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result2.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result1).getE(), 5);
        org.junit.Assert.assertSame("should be same",((Input.El) result2).getE(), 4);
    }
    
       
    @Test
    public void stopEof(){
        Input[] in1 =  {Input.el(5),Input.EMPTY ,Input.el(2),Input.EOF };
        Input[] in2 =  {Input.el(1),Input.EMPTY ,Input.el(4), Input.EMPTY , Input.el(3) , Input.EOF };
        Enumerator enumerator1 = Enumerator.enumInput(in1);
        Enumerator enumerator2 = Enumerator.enumInput(in2);
        Iteratee<Integer,Integer> summer1 = new MaxIteratee(); 
        
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        
        Input result1 = enumerator1.run(summer1);
        Input result2 = enumerator2.run(summer1);
        enumerator1.run(summer1);
        enumerator2.run(summer1);
        org.junit.Assert.assertSame(result1.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame(result2.onState(), Input.InputState.EL);
        org.junit.Assert.assertSame("should be same",((Input.El) result1).getE(), 5);
        org.junit.Assert.assertSame("should be same",((Input.El) result2).getE(), 4);
    }
    
}
