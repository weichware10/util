package github.weichware10.util;

/**
 * Klasse, um Inhalte schön zu loggen. Hat verschiedene Log-Stufen:
 *
 * <p>- INFO
 *
 * <p>- WARN
 *
 * <p>- ERROR
 *
 * <p>- DEBUG
 *
 * <p>Jede Funktion hat 3 verschiedene Versionen,
 * ohne Kontext, mit Kontext, und mit Kontext und Stack Trace.
 *
 * @since v0.2
 */
public class Logger {

    // Zum Farbigen Printen. Falls neue Farben hinzugefügt werden sollen:
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    /**
     * Cannot be instantiated.
     */
    private Logger() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    // === DEBUG ===

    /**
     * Loggt eine debug-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void debug(String message) {
        System.out.println(PURPLE + "[DEBUG] " + RESET + message);
    }

    /**
     * Loggt eine debug-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void debug(String message, Exception ctx) {
        debug(message, ctx, false);
    }

    /**
     * Loggt eine debug-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @param stacktrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void debug(String message, Exception ctx, boolean stacktrace) {
        System.out.println(PURPLE + "[DEBUG] " + RESET + message + ": " + ctx.getMessage());
        if (stacktrace) {
            ctx.printStackTrace();
        }
    }

    /**
     * Loggt eine error-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void error(String message) {
        System.out.println(RED + "[ERROR] " + RESET + message);
    }

    /**
     * Loggt eine error-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void error(String message, Exception ctx) {
        error(message, ctx, false);
    }

    /**
     * Loggt eine error-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @param stacktrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void error(String message, Exception ctx, boolean stacktrace) {
        System.out.println(RED + "[ERROR] " + RESET + message + ": " + ctx.getMessage());
        if (stacktrace) {
            ctx.printStackTrace();
        }
    }

    /**
     * Loggt eine warn-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void warn(String message) {
        System.out.println(YELLOW + "[WARN] " + RESET + message);
    }

    /**
     * Loggt eine warn-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void warn(String message, Exception ctx) {
        warn(message, ctx, false);
    }

    /**
     * Loggt eine warn-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @param stacktrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void warn(String message, Exception ctx, boolean stacktrace) {
        System.out.println(YELLOW + "[WARN] " + RESET + message + ": " + ctx.getMessage());
        if (stacktrace) {
            ctx.printStackTrace();
        }
    }

    /**
     * Loggt eine info-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void info(String message) {
        System.out.println(CYAN + "[INFO] " + RESET + message);
    }

    /**
     * Loggt eine info-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void info(String message, Exception ctx) {
        info(message, ctx, false);
    }

    /**
     * Loggt eine info-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx - Kontext, d.h. eine Exception
     * @param stacktrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void info(String message, Exception ctx, boolean stacktrace) {
        System.out.println(CYAN + "[INFO] " + RESET + message + ": " + ctx.getMessage());
        if (stacktrace) {
            ctx.printStackTrace();
        }
    }
}
