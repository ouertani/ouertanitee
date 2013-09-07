package com.technozor.ouertanitee;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author slim ouertani
 */
public interface FutureUtils {

    static <T> Future<T> toFuture(T t) {
        return CompletableFuture.supplyAsync(() -> t);
    }

    static <T> T fetch(Future<T> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException ex) {
            ExceptionUtils.<RuntimeException>handle(ex);
            throw new IllegalStateException("cannot be here");
        }
    }
}
