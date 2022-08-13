package util;

@FunctionalInterface
public interface CheckedFunction <T, R, E extends Throwable>{
    R apply(T param) throws E;
}
