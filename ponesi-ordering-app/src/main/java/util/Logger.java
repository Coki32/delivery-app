package util;

public class Logger {
    private static final boolean very_verbose = true;

    public static void log(String msg, String source) {
        if (very_verbose) {
            System.out.println("[" + source + "]: " + msg);
        }
    }

    public static void log(String msg, Object any) {
        log(msg, any.getClass().getName());
    }

}
