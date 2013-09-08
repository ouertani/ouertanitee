package com.technozor.ouertanitee;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @author slim ouertani
 */
public interface CollectionUtils {

    static <A, B> B leftFold(List<A> l, B initialValue,  BiFunction<A, B, B> folder) {
        B acc = initialValue;
        for (A a : l) {
            acc = folder.apply(a, acc);
        }
        return acc;
    }

    static <A, B> B leftFoldM(List<A> l, B initialValue,  BiFunction<A, B, Future<B>> folder) {
        B acc = initialValue;
        for (A a : l) {
            acc = FutureUtils.fetch(folder.apply(a, acc));
        }
        return acc;
    }
    
    static <A, B> B leftFoldM( B initialValue, Stream<A> l, BiFunction< A,B, Future<B>> f) {
        B acc = initialValue;
        Iterator<A> iterator = l.iterator();
        while(iterator.hasNext()){
            acc = FutureUtils.fetch(f.apply(iterator.next(), acc));
        }
        return acc;
    }
}
