package github.weichware10.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testet {@link Logger}.
 */
public class LoggerTest {

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static PrintStream originalOut = System.out;
    private static PrintStream originalErr = System.err;

    /**
     * Setzt output stream auf neuen Stream, um diesen Stream zu testen.
     * https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @BeforeClass
    public static void setUpStreams() {
        Logger.setSysOut(new PrintStream(outContent));
        Logger.setSysErr(new PrintStream(errContent));
    }

    /**
     * Setzt Output Stream zurück.
     * https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @AfterClass
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldLogCorrectTypeAndMessage() throws IOException {
        Logger.debug("debugmessage");
        assertTrue(outContent.toString().contains("[DEBUG]"));
        assertTrue(outContent.toString().contains("debugmessage"));
        outContent.flush();
        Logger.info("infomessage");
        assertTrue(outContent.toString().contains("[INFO]"));
        assertTrue(outContent.toString().contains("infomessage"));
        outContent.flush();
        Logger.warn("warnmessage");
        assertTrue(outContent.toString().contains("[WARN]"));
        assertTrue(outContent.toString().contains("warnmessage"));
        outContent.flush();
        Logger.error("errormessage");
        assertTrue(outContent.toString().contains("[ERROR]"));
        assertTrue(outContent.toString().contains("errormessage"));
        outContent.flush();
    }

    @Test
    public void shouldLogCorrectContext() throws IOException {
        Logger.debug("debugmessage", new Exception("debugcontext"));
        assertTrue(outContent.toString().contains("debugcontext"));
        outContent.flush();
        Logger.info("infomessage", new Exception("infocontext"));
        assertTrue(outContent.toString().contains("infocontext"));
        outContent.flush();
        Logger.warn("warnmessage", new Exception("debugcontext"));
        assertTrue(outContent.toString().contains("debugcontext"));
        outContent.flush();
        Logger.error("errormessage", new Exception("debugcontext"));
        assertTrue(outContent.toString().contains("debugcontext"));
        outContent.flush();
    }

    @Test
    public void shouldLogStackTrace() throws IOException {
        Logger.debug("debugmessage", new Exception("debugcontext"), true);
        assertFalse("errContent should not be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.info("infomessage", new Exception("infocontext"), true);
        assertFalse("errContent should not be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.warn("warnmessage", new Exception("warncontext"), true);
        assertFalse("errContent should not be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.error("errormessage", new Exception("errorcontext"), true);
        assertFalse("errContent should not be empty", errContent.toString().isEmpty());
        outContent.flush();
    }

    @Test
    public void shouldNotLogStackTrace() throws IOException {
        Logger.debug("debugmessage", new Exception("debugcontext"), false);
        Logger.debug("debugmessage", new Exception("debugcontext"));
        Logger.debug("debugmessage");
        assertTrue("errContent should be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.info("infomessage", new Exception("infocontext"), false);
        Logger.info("infomessage", new Exception("infocontext"));
        Logger.info("infomessage");
        assertTrue("errContent should be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.warn("warnmessage", new Exception("warncontext"), false);
        Logger.warn("warnmessage", new Exception("warncontext"));
        Logger.warn("warnmessage");
        assertTrue("errContent should be empty", errContent.toString().isEmpty());
        outContent.flush();
        Logger.error("errormessage", new Exception("errorcontext"), false);
        Logger.error("errormessage", new Exception("errorcontext"));
        Logger.error("errormessage");
        assertTrue("errContent should be empty", errContent.toString().isEmpty());
        outContent.flush();
    }

    @Test
    public void shouldLogToFile() throws IOException {
        String filename = String.format("target/%s.log", DateTime.now().toString("yMMdd-HHmmss"));
        Logger.setLogfile(filename);
        Logger.debug("testdebug");
        Logger.info("testinfo");
        Logger.warn("testinfo");
        Logger.error("testerror");
        File debugFile = new File(filename);
        List<String> expectedContent = Arrays.asList(
            "[DEBUG] testdebug",
            "[INFO] testinfo",
            "[WARN] testinfo",
            "[ERROR] testerror");
        List<String> actualContent = Files.readAllLines(debugFile.toPath());
        assertArrayEquals(expectedContent.toArray(), actualContent.toArray());
    }
}
