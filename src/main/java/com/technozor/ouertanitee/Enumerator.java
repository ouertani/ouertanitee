package com.technozor.ouertanitee;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * @author slim ouertani
 */
@FunctionalInterface
public interface Enumerator<E> {

    <A> Future<Iteratee<E, A>> apply(Iteratee<E, A> it);

    static <E> Enumerator<E> empty() {
        return new Enumerator<E>() {
            @Override
            public <A> Future<Iteratee<E, A>> apply(Iteratee<E, A> it) {
                return CompletableFuture.supplyAsync(() -> it);
            }
        };
    }
    
    default <B> Input<B> run(Iteratee<E, B> it) {
      
        switch (it.onState()) {
            case CONT:
                return run(apply(it));
            case ERROR:
                Iteratee.Error e = (Iteratee.Error) it;
                throw new RuntimeException(e.getMsg());
            case Done:
                Iteratee.Done<E, B> d = (Iteratee.Done) it;
                return Input.el(d.getA());
            default:
                throw new IllegalStateException();
        }
    }

    default <B> Input<B> run(Future<Iteratee<E, B>> fit) {
        return run(FutureUtils.fetch(fit));
    }

    static <B> Enumerator<B> enumInput(Input<B> input) {
        return new Enumerator<B>() {

            @Override
            public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> it) {
                Function<Iteratee<B, A>, Future<Iteratee<B, A>>> step = (Iteratee<B, A> t) -> {
                    switch (t.onState()) {
                        case CONT:
                            
                            return CompletableFuture.supplyAsync(() -> {
                                Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                return c.getK().apply(input);
                            });
                            
                        default:
                            return CompletableFuture.supplyAsync(() -> t);
                    }
                };
                return it.handle(step);
            }
        };
    }

    /**
     *
     * @param <B>
     * @param input
     * @return
     */
    static <B> Enumerator<B> enumInput(final Input<B>... input) {

        switch (input.length) {
            case 0:
                return Enumerator.empty();
            case 1:
                return new Enumerator<B>() {
                @Override
                public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> i) {

                    return i.handle((Iteratee<B, A> t) -> {
                        switch (t.onState()) {
                            case CONT:

                                return CompletableFuture.supplyAsync(() -> {
                                Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                return c.getK().apply(input[0]);
                            });
                            default:
                                return CompletableFuture.supplyAsync(() -> t);
                        }
                    });
                }
            };

            default:

                List<Input<B>> of = Arrays.asList(input);
                return new Enumerator<B>() {
                    @Override
                    public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> it) {
                        return enumSeq(of, it);
                    }
                };
        }

    }
    
    static <B> Enumerator<B> enumInput(final B... input) {
        return enumInput(Stream.of(input));
    }
    /**
     * Need to add Eof at the end
     * @param <B>
     * @param input
     * @return 
     */
     static <B> Enumerator<B> enumInput(Stream<B> input) {
        Stream<Input<B>> map = input.map(t -> Input.el(t));
        Enumerator<B> empty = Enumerator.empty();
        throw new RuntimeException("Not implemented yet");
       
    }
     
      static <E, A> Future<Iteratee<E, A>> enumStream(Stream<Input<E>>  l, Iteratee<E, A> i) {
          BiFunction<Input<E>, Iteratee<E, A>, Future<Iteratee<E, A>>> f = (Input<E> t, Iteratee<E, A> u) -> {
              switch(u.onState()) {
                  case CONT :
                      return CompletableFuture.supplyAsync(() -> u.handler().apply(t));
                   default:
                    return CompletableFuture.supplyAsync(() -> u);    
              }
          };
          ;
          return CompletableFuture.supplyAsync(() -> CollectionUtils.leftFoldM(i,l, f));
      }

    static <E, A> Future<Iteratee<E, A>> enumSeq(List<Input<E>> l, Iteratee<E, A> i) {
        BiFunction<Input<E>, Iteratee<E, A>, Future<Iteratee<E, A>>> f = (Input<E> a, Iteratee<E, A> it) -> {
         
            switch (it.onState()) {
                case CONT:
                    return CompletableFuture.supplyAsync(() -> it.handler().apply(a));

                default:
                    return CompletableFuture.supplyAsync(() -> it);
            }
        };

        return CompletableFuture.supplyAsync(() -> CollectionUtils.leftFoldM(l, i, f));
    }

}
