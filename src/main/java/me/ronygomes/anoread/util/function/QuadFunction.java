package me.ronygomes.anoread.util.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<T, U, V, W, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param v the third function argument
     * @param w the third function argument
     * @return the function result
     */
    R apply(T t, U u, V v, W w);

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <S>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <S> QuadFunction<T, U, V, W, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v, W w) -> after.apply(apply(t, u, v, w));
    }
}
