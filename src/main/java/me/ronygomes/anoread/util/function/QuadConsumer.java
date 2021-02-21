package me.ronygomes.anoread.util.function;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param v the third input argument
     * @param w the third input argument
     */
    void accept(T t, U u, V v, W w);

    /**
     * Returns a composed {@code QuadConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code QuadConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default QuadConsumer<T, U, V, W> andThen(QuadConsumer<? super T, ? super U,
            ? super V, ? super W> after) {
        Objects.requireNonNull(after);

        return (l, r, v, w) -> {
            accept(l, r, v, w);
            after.accept(l, r, v, w);
        };
    }
}
