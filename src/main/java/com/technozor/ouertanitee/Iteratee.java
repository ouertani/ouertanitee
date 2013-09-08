package com.technozor.ouertanitee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author slim ouertani
 * @param <E>
 * @param <A>
 */
public interface Iteratee<E, A> {

    <B> CompletableFuture<B> handle(Function<Iteratee<E, A>, CompletableFuture<B>> step);

    default Function<Input<E>, Iteratee<E, A>> handler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    enum StepState {

        Done, CONT, ERROR
    }

    default StepState onState() {
        return StepState.CONT;
    }

    static <E> Error<E> Error(String msg, Input<E> input) {
        return new Error(msg, input);
    }

    static <E, A> Cont<E, A> Cont(Function<Input<E>, Iteratee<E, A>> k) {
        return new Cont(k);
    }

    static <E, A> Done<E, A> Done(A a, Input<E> e) {
        return new Done(a, e);
    }

    static <E, A> Done<E, A> Done(A a) {
        return new Done(a);
    }

    class Done<E, A> implements Iteratee<E, A> {

        private final A a;
        private final Input<E> input;

        @Override
        public StepState onState() {
            return StepState.Done;
        }

        public A getA() {
            return a;
        }

        public Input<E> getInput() {
            return input;
        }

        public Done(A a, Input<E> e) {
            this.a = a;
            this.input = e;
        }

        public Done(A a) {
            this(a, Input.EMPTY);
        }

        @Override
        public <B> CompletableFuture<B> handle(Function<Iteratee<E, A>, CompletableFuture<B>> folder) {
            return folder.apply(Iteratee.Done(a, input));
        }

        @Override
        public String toString() {
            return "Done{" + "a=" + a + ", input=" + input + '}';
        }

        @Override
        public Function<Input<E>, Iteratee<E, A>> handler() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class Cont<E, A> implements Iteratee<E, A> {

        private final Function<Input<E>, Iteratee<E, A>> k;

        public Cont(Function<Input<E>, Iteratee<E, A>> k) {
            this.k = k;
        }

        public Function<Input<E>, Iteratee<E, A>> getK() {
            return k;
        }

        @Override
        public StepState onState() {
            return StepState.CONT;
        }

        @Override
        public String toString() {
            return "Cont{" + "k=" + k + '}';
        }

        @Override
        public <B> CompletableFuture<B> handle(Function<Iteratee<E, A>, CompletableFuture<B>> folder) {
            return folder.apply(Iteratee.Cont(k));
        }

    }

    class Error<E> implements Iteratee<E, Object> {

        private final String msg;
        private final Input<E> input;

        public Error(String msg, Input<E> input) {
            this.msg = msg;
            this.input = input;
        }

        @Override
        public StepState onState() {
            return StepState.ERROR;
        }

        public String getMsg() {
            return msg;
        }

        public Input<E> getInput() {
            return input;
        }

        @Override
        public <B> CompletableFuture<B> handle(Function<Iteratee<E, Object>, CompletableFuture<B>> folder) {
            return folder.apply(Iteratee.Error(msg, input));
        }

        @Override
        public String toString() {
            return "Error{" + "msg=" + msg + ", input=" + input + '}';
        }
    }
}
