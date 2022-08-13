package util;

@FunctionalInterface
public interface CheckedConsumer<T, E extends Throwable> {
    void supply(T value) throws E;
}
