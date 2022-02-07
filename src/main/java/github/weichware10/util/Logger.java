package github.weichware10.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.TextArea;

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

    private static PrintStream logfile;
    private static PrintStream sysOut = System.out;
    private static PrintStream sysErr = System.err;
    private static TextArea logArea;
    private static LogStream logStream = new LogStream();
    public static final PrintStream PRINSTREAM = new PrintStream(logStream, true);

    /**
     * Kann nicht instanziiert werden.
     */
    private Logger() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     * setzt log-Datei.
     *
     * @param filename - die Datei
     */
    public static void setLogfile(String filename) {
        if (filename == null) {
            return;
        }
        try {
            File file = new File(filename);
            file.createNewFile();
            logfile = new PrintStream(file);
        } catch (IOException e) {
            logfile = null;
        }
    }

    public static void setLogArea(TextArea logArea) {
        Logger.logArea = logArea;
    }

    public static void setSysOut(PrintStream out) {
        Logger.sysOut = out;
    }

    public static void setSysErr(PrintStream err) {
        Logger.sysErr = err;
    }

    // === DEBUG ===

    /**
     * Loggt eine debug-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void debug(String message) {
        logStream.consoleLog(PURPLE + "[DEBUG] " + RESET + message + "\n");
        logStream.plainLog("[DEBUG] " + message + "\n");
    }

    /**
     * Loggt eine debug-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx     - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void debug(String message, Exception ctx) {
        debug(message, ctx, false);
    }

    /**
     * Loggt eine debug-Nachricht mit Kontext.
     *
     * @param message    - Inhalt
     * @param ctx        - Kontext, d.h. eine Exception
     * @param stackTrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void debug(String message, Exception ctx, boolean stackTrace) {
        logStream.consoleLog(PURPLE + "[DEBUG] " + RESET + message + ": "
                + ctx.getMessage() + "\n");
        logStream.plainLog("[DEBUG] " + message + ": " + ctx.getMessage() + "\n");
        if (stackTrace) {
            logStream.printStackTrace(ctx);
        }
    }

    /**
     * Loggt eine error-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void error(String message) {
        logStream.consoleLog(RED + "[ERROR] " + RESET + message + "\n");
        logStream.plainLog("[ERROR] " + message + "\n");
    }

    /**
     * Loggt eine error-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx     - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void error(String message, Exception ctx) {
        error(message, ctx, false);
    }

    /**
     * Loggt eine error-Nachricht mit Kontext.
     *
     * @param message    - Inhalt
     * @param ctx        - Kontext, d.h. eine Exception
     * @param stackTrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void error(String message, Exception ctx, boolean stackTrace) {
        logStream.consoleLog(RED + "[ERROR] " + RESET + message + ": " + ctx.getMessage() + "\n");
        logStream.plainLog("[ERROR] " + message + ": " + ctx.getMessage() + "\n");
        if (stackTrace) {
            logStream.printStackTrace(ctx);
        }
    }

    /**
     * Loggt eine warn-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void warn(String message) {
        logStream.consoleLog(YELLOW + "[WARN] " + RESET + message + "\n");
        logStream.plainLog("[WARN] " + message + "\n");
    }

    /**
     * Loggt eine warn-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx     - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void warn(String message, Exception ctx) {
        warn(message, ctx, false);
    }

    /**
     * Loggt eine warn-Nachricht mit Kontext.
     *
     * @param message    - Inhalt
     * @param ctx        - Kontext, d.h. eine Exception
     * @param stackTrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void warn(String message, Exception ctx, boolean stackTrace) {
        logStream.consoleLog(YELLOW + "[WARN] " + RESET + message + ": " + ctx.getMessage() + "\n");
        logStream.plainLog("[WARN] " + message + ": " + ctx.getMessage() + "\n");
        if (stackTrace) {
            logStream.printStackTrace(ctx);
        }
    }

    /**
     * Loggt eine info-Nachricht ohne Kontext.
     *
     * @param message - Inhalt
     * @since v0.2
     */
    public static void info(String message) {
        logStream.consoleLog(CYAN + "[INFO] " + RESET + message + "\n");
        logStream.plainLog("[INFO] " + message + "\n");
    }

    /**
     * Loggt eine info-Nachricht mit Kontext.
     *
     * @param message - Inhalt
     * @param ctx     - Kontext, d.h. eine Exception
     * @since v0.2
     */
    public static void info(String message, Exception ctx) {
        info(message, ctx, false);
    }

    /**
     * Loggt eine info-Nachricht mit Kontext.
     *
     * @param message    - Inhalt
     * @param ctx        - Kontext, d.h. eine Exception
     * @param stackTrace - ob ein StackTrace angezeigt werden soll
     * @since v0.2
     */
    public static void info(String message, Exception ctx, boolean stackTrace) {
        logStream.consoleLog(CYAN + "[INFO] " + RESET + message + ": " + ctx.getMessage() + "\n");
        logStream.plainLog("[INFO] " + message + ": " + ctx.getMessage() + "\n");
        if (stackTrace) {
            logStream.printStackTrace(ctx);
        }
    }

    private static class LogStream extends OutputStream {
        @Override
        public void write(int i) {
            if (logArea != null) {
                logArea.appendText(String.valueOf((char) i));
            }
            if (sysOut != null) {
                sysOut.append((char) i);
            }
            if (logfile != null) {
                logfile.append((char) i);
            }
        }

        public void consoleLog(String content) {
            if (sysOut != null) {
                for (int i = 0; i < content.length(); i++) {
                    sysOut.append(content.charAt(i));
                }
            }
        }

        public void plainLog(String content) {
            if (logArea != null) {
                logArea.appendText(content);
            }
            if (logfile != null) {
                logfile.append(content);
            }
        }

        public void printStackTrace(Exception ctx) {
            // stacktrace als string
            String stackTraceString = null;
            if (logArea != null | logfile != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ctx.printStackTrace(pw);
                stackTraceString = sw.toString();
            }
            if (logArea != null) {
                logArea.appendText(stackTraceString);
            }
            if (logfile != null) {
                logfile.append(stackTraceString);
            }
            if (sysErr != null) {
                ctx.printStackTrace(sysErr);
            } else if (sysOut != null) {
                ctx.printStackTrace(sysOut);
            }
        }
    }
}
