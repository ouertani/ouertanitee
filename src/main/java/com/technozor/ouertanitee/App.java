package com.technozor.ouertanitee;


import static com.technozor.ouertanitee.Input.InputState.EL;
import com.technozor.ouertanitee.samples.MaxIteratee;
import com.technozor.ouertanitee.samples.SumIteratee;
import java.util.concurrent.Future;


public class App {

    public static void main(String[] args) {
  
        Input[] in1 =  {Input.el(5),Input.el(2),Input.EOF };
        Input[] in2 =  {Input.el(1),Input.el(4), Input.el(3) , Input.EOF };
         Input[] in3 =  {Input.el(1),Input.EMPTY , Input.el(4), Input.el(3) , Input.EMPTY, Input.EOF, Input.el(100) };
  
        Enumerator lit1 = Enumerator.enumInput(in1);
        Enumerator lit2 = Enumerator.enumInput(in2);
        Enumerator lit3 = Enumerator.enumInput(in3);
        
        Future<Iteratee<Integer,Integer>> summer = FutureUtils.toFuture(new SumIteratee()); 
       Future<Iteratee<Integer,Integer>> maxer = FutureUtils.toFuture(new MaxIteratee()); 
        Input run = lit1.run(summer);
        lit1.run(summer);
        lit1.run(summer);
        lit1.run(summer);
//        
       Input run2 = lit2.run(summer);
//        
         Input run3 = lit1.run(maxer);
         
         
         Input run4 = lit2.run(maxer); 
//        
        System.out.println("run" + run);
        switch(run.onState()){
            case EL : Input.El<Integer> el = (Input.El) run;
                System.out.println(""+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run.onState());
                
        } 
       //   lit1.run(maxer);
        
//        
//        switch(run2.state()){
//            case EL : Input.El<Integer> el = (Input.El) run2;
//                System.out.println(""+el.getE());
//                break;
//            
//            default:
//                System.out.println("---------------"+run2.state());
//                
//        }
         switch(run3.onState()){
            case EL : Input.El<Integer> el = (Input.El) run3;
                System.out.println("::::"+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run3.onState());
                
        }
          switch(run4.onState()){
            case EL : Input.El<Integer> el = (Input.El) run4;
                System.out.println(""+el.getE());
                break;
            
            default:
                System.out.println("---------------"+run4.onState());
                
        }
        Input runis = lit3.run(summer);
        Input runi = lit3.run(maxer);
        
         System.out.println("runi" + runi);
        switch(run.onState()){
            case EL : Input.El<Integer> el = (Input.El) runi;
                System.out.println(""+el.getE());
                break;
            
            default:
                System.out.println("---------------"+runi.onState());
                
        } 
    }

  
}
