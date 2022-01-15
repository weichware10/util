package github.weichware10.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

/**
 * Anlegen und Löschen von temporären Verzeichnissen und Dateien.
 */
public class Files {
    public static String tmpdir = null;

    /**
     * erstellt einen temporären Ordner.
     */
    public static String createTempDir() {
        String tmpdir = null;
        try {
            tmpdir = java.nio.file.Files.createTempDirectory("weichware")
                    .toFile().getAbsolutePath();
            Logger.info("created tempdir: " + tmpdir + "...");
        } catch (Exception e) {
            Logger.error("Error while creating a tmpdir", e, true);
        }
        return tmpdir;
    }

    /**
     * speichert ein Bild von der URL im temp Ordner.
     *
     * @param imageUrl - URL vom Bild
     * @return Pfad zum Bild
     * @throws MalformedURLException
     * @throws IllegalArgumentException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String saveImage(String imageUrl) throws MalformedURLException,
            IllegalArgumentException, FileNotFoundException, IOException {
        if (tmpdir == null) {
            tmpdir = createTempDir();
        }
        URL url = null;
        url = new URL(imageUrl);

        String fileName = url.getFile();
        String destName = tmpdir + fileName.substring(fileName.lastIndexOf("/"));
        Logger.info("Saved file: " + tmpdir);

        String fileType = fileName.substring(fileName.lastIndexOf("."));

        List<String> supportedFileTypes = Arrays.asList(".jpg", ".png", ".jpeg");

        if (!supportedFileTypes.contains(fileType)) {
            throw new IllegalArgumentException("Filetype of given image is not supported");
        }

        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destName);
        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();

        return destName;
    }

    public static String saveGeneratedImage(BufferedImage buffImage, String fileName)
            throws IOException, IllegalArgumentException {
        if (tmpdir == null) {
            tmpdir = createTempDir();
        }
        RenderedImage rendImage = buffImage;

        String destName = tmpdir + "/" + fileName;
        File file = new File(destName);
        ImageIO.write(rendImage, "png", file);
        
        return destName;
    }

    /**
     * Löscht angelegten temporären Ordner beim Beenden der App.
     */
    public static Thread deleteTempDir() {
        return new Thread(() -> {
            if (tmpdir == null) {
                return;
            }
            try {
                Logger.info("deleting tmp folder...");
                FileUtils.deleteDirectory(new File(tmpdir));
                tmpdir = null;
            } catch (IOException e) {
                Logger.error("IOException while deleting tmpdir", e, true);
            } catch (IllegalArgumentException e) {
                Logger.error("IllegalArgumentException while deleting tmpdir", e, true);
            }
        });
    }
}
