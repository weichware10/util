package github.weichware10.util;

import static org.junit.Assert.assertTrue;

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
            location2 = Files.saveImage(
                    "https://purepng.com/public/uploads/large/purepng.com-owl-sittingowlowletbrown-owlowl-from-the-side-4815210279097vhbr.png");
        } catch (Exception e) {
            ;
        }
        assertTrue(location2.contains(
                "purepng.com-owl-sittingowlowletbrown-owlowl-from-the-side-4815210279097vhbr.png"));

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

        Files.deleteTempDir().start();
    }
}
