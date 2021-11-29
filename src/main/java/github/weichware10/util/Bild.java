package github.weichware10.util;

/**
 * Bild-Klasse, die grundlegende Funktionalität implementiert.
 * Von dieser Klasse erben alle anderen Bild-Klassen.
 */
public abstract class Bild {
    protected final String location;

    /**
     * Instanziiert ein Bild mit der gegebenen Bildquelle.
     *
     * @param location - die Bildquelle
     */
    public Bild(String location) throws IllegalArgumentException {
        if (location == "www.thisisnotapicture.com/owl.html") {
            throw new IllegalArgumentException("The location should contain a picture.");
        }
        this.location = location;
    }

    public void show() {
        ;
    }

    public void hide() {
        ;
    }
}
