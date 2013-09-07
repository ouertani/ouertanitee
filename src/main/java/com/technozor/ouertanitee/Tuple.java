package com.technozor.ouertanitee;

/**
 * @author slim ouertani
 * @param <A>
 * @param <B>
 */
public class Tuple<A,B> {
    private final A a;
    private final B b;

    static <A,B> Tuple<A,B> of(A a, B b) {
       return new Tuple(a,b);
    }
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A _1() {
        return a;
    }

    public B _2() {
        return b;
    }
}
