package github.weichware10.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet {@link Logger}.
 */
public class LoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    /**
     * Setzt output stream auf neuen Stream, um diesen Stream zu testen.
     * https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Setzt Output Stream zurück.
     * https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @After
    public void restoreStreams() {
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
}
