package github.weichware10.util;

/**
 * Bild-Klasse, die grundlegende Funktionalität implementiert.
 * Von dieser Klasse erben alle anderen Bild-Klassen.
 */
public abstract class Bild {
    protected final String location;

    public Bild(String location) {
        this.location = location;
    }

    public void show() {
        ;
    }

    public void hide() {
        ;
    }
}
