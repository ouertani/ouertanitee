ouertanitee
===========
#Introduction

This is  a basic implementation of iteratee using jdk8. 

Due to non monadic java future support some features are not complete, but it's first version.
#Usage

- Implement an Iteratee 


		public interface Iteratee<E, A> {

    		<B> Future<B> handle(Function<Iteratee<E, A>, Future<B>> step);
    	}	

- Run it using Enumerator (optional)
		
		default <B> Input<B> run(Iteratee<E, B> it) 


#Examples 

*[SumIteratee](https://github.com/ouertani/ouertanitee/blob/master/src/main/java/com/technozor/ouertanitee/samples/SumIteratee.java)

*[MaxIteratee](https://github.com/ouertani/ouertanitee/blob/master/src/main/java/com/technozor/ouertanitee/samples/MaxIteratee.java)

Please refer to test cases for more details.

# Roadmap

Enumeratee,Stream, Generics... 
