package com.technozor.ouertanitee;
/**
 * @author slim ouertani
 */
public interface ExceptionUtils{
  static <T extends Exception>  void  handle(Exception ex) throws T {
       throw (T) ex;
   } 
}
