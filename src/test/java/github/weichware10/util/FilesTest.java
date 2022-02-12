package github.weichware10.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * Testet {@link Files}.
 */
public class FilesTest {

    @Test
    public void shouldSaveImageFromUrl() {
        String location1 = null;
        try {
            location1 = Files.saveImage(
                    "https://www.planet-wissen.de/natur/voegel/eulen/eulen-uhu-kopf-100~_v-ARDFotogalerie.jpg");
        } catch (Exception e) {
            ;
        }
        assertTrue(location1.contains("eulen-uhu-kopf-100~_v-ARDFotogalerie.jpg"));

        String location2 = null;
        try {
            location2 = Files.saveImage("https://http.cat/200.jpg");
        } catch (Exception e) {
            Logger.error("Exception while saving image", e);
        }
        assertTrue(location2.contains("200.jpg"));

        boolean thrown1 = false;
        try {
            Files.saveImage("weirdlocation");
        } catch (Exception e) {
            thrown1 = true;
            Logger.info(e.toString());
        }
        assertTrue(thrown1);

        boolean thrown2 = false;
        try {
            Files.saveImage("https://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif");
        } catch (Exception e) {
            thrown2 = true;
            Logger.info(e.toString());
        }
        assertTrue(thrown2);

        assertTrue(Files.tmpdir.contains("weichware"));

        Path tmpdir = Paths.get(Files.tmpdir);
        assertTrue(java.nio.file.Files.exists(tmpdir));

        Thread deleteThread1 = Files.deleteTempDir();
        deleteThread1.start();
        try {
            deleteThread1.join();
        } catch (InterruptedException e) {
            Logger.error("InterruptedException while deleting tempDir", e, true);
        }
        assertFalse(java.nio.file.Files.exists(tmpdir));

        // nochmal löschen, sollte keine Fehler throwen
        Thread deleteThread2 = Files.deleteTempDir();
        deleteThread2.start();
        try {
            deleteThread2.join();
        } catch (InterruptedException e) {
            Logger.error("InterruptedException while deleting tempDir", e, true);
        }
        assertFalse(java.nio.file.Files.exists(tmpdir));
    }
}
