package com.technozor.ouertanitee;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @author slim ouertani
 */
public interface CollectionUtils {

    static <A, B> B leftFold(B initialValue, List<A> l,  BiFunction<A, B, B> folder) {
        B acc = initialValue;
        for (A a : l) {
            acc = folder.apply(a, acc);
        }
        return acc;
    }
    
    static <A, B> CompletableFuture<B> leftFoldM(B initialValue, List<A> l,  BiFunction< B,A, CompletableFuture<B>> folder) {
        switch(l.size()){
            case 0 : return CompletableFuture.completedFuture(initialValue);
            default : 
            A head = l.get(0);
            List<A> tail = l.subList(1, l.size());
            CompletableFuture<B> apply = folder.apply(initialValue, head);
            return FutureUtils.flatMap(apply.thenApplyAsync(in -> leftFoldM(in, tail,folder)));
        }
    }
    /**
     * using reduce in progress
     * @param <A>
     * @param <B>
     * @param initialValue
     * @param l
     * @param f
     * @return 
     */
    static <A, B> CompletableFuture<B>  leftFoldM( B initialValue, Stream<A> l, BiFunction< B,A, CompletableFuture<B>> f) {
        B acc = initialValue;
        Iterator<A> iterator = l.iterator();
        while(iterator.hasNext()){
            acc = f.apply( acc,iterator.next()).join();
        }
        return CompletableFuture.completedFuture(acc);
    }
}
