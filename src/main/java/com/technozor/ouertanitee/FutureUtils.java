package com.technozor.ouertanitee;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author slim ouertani
 */
public interface FutureUtils {

    static <T> CompletableFuture<T> toFuture(T t) {
        return CompletableFuture.supplyAsync(() -> t);
    }

    static <T> T fetch(CompletableFuture<T> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException ex) {
            ExceptionUtils.<RuntimeException>handle(ex);
            throw new IllegalStateException("cannot be here");
        }
    }
    static<T> CompletableFuture<T> flatMap(CompletableFuture<CompletableFuture<T>> f){
        return f.thenApplyAsync(t -> t.join());
    }
}
