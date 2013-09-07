package com.technozor.ouertanitee;

/**
 * @author slim ouertani
 * @param <E>
 */
public interface Input<E> {

    enum InputState {

        EOF, Empty, EL
    }

    InputState onState();

    Input EOF = () -> InputState.EOF;
    
    Input EMPTY = () -> InputState.Empty;
    
    static <E> Input<E> el(E e ) {
        return new El(e);
    };

    class El<E> implements Input<E> {

        private final E e;

        public El(E e) {
            this.e = e;
        }

        @Override
        public InputState onState() {
            return InputState.EL;
        }

        public E getE() {
            return e;
        }

        @Override
        public String toString() {
            return "El{" + "e=" + e + '}';
        }
    };
}
