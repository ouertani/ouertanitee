package com.technozor.ouertanitee;


import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author slim ouertani
 */
public interface CollectionUtils {

    static <A, B> B leftFold(List<A> l, B initialValue,  Function<Tuple<A, B>, B> folder) {
        B acc = initialValue;
        for (A a : l) {
            acc = folder.apply(Tuple.of(a, acc));
        }
        return acc;
    }

    static <A, B> B leftFoldM(List<A> l, B initialValue,  Function<Tuple<A, B>, Future<B>> folder) {
        B acc = initialValue;
        for (A a : l) {
            acc = FutureUtils.fetch(folder.apply(Tuple.of(a, acc)));
        }
        return acc;
    }
}
