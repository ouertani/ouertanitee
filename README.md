ouertanitee
===========
#Introduction

This is  a basic implementation of iteratee using jdk8. 

Some features are not full complete, but it's the first version.
#Usage

- Implement an Iteratee 


		public interface Iteratee<E, A> {

    		<B> CompletableFuture<B> handle(Function<Iteratee<E, A>, CompletableFuture<B>> step);
    	}	

- Run it using Enumerator (optional)
		
		default <B> Input<B> run(Iteratee<E, B> it) 


#Examples 

*[SumIteratee](https://github.com/ouertani/ouertanitee/blob/master/src/main/java/com/technozor/ouertanitee/samples/SumIteratee.java)

*[MaxIteratee](https://github.com/ouertani/ouertanitee/blob/master/src/main/java/com/technozor/ouertanitee/samples/MaxIteratee.java)

Please refer to test cases for more details.

# Roadmap

Enumeratee,Stream, Generics... 
