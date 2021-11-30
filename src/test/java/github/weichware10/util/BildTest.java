package github.weichware10.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testet die abstrakte Klasse {@link Bild}.
 * Deshalb muss die Klasse {@link TestBild} erstellt werden.
 */
public class BildTest {

    /**
     * TestBild-Klasse, welche von Bild erbt.
     */
    class TestBild extends Bild {
        /**
         * Bild zum Testen.
         *
         * @param location - Ort des Bildes
         */
        public TestBild(String location) {
            super(location);
        }
    }

    /**
     * Testet, ob eine Exception gEwOrFeN wird, wenn eine falsche Bild-URL angegeben wird.
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptIllegalArguments() {
        new TestBild("www.thisisnotapicture.com/owl.html");
    }

    /**
     * Testet, ob keine Exception gEwOrFeN wird, wenn eine richtige Bild-URL angegeben wird.
     */
    @Test
    public void shouldAcceptLlegalArguments() {
        new TestBild("www.thisisapicture.com/running-owl.png");
    }

    /**
     * Testet, ob die location korrekt gesetzt wurde.
     */
    @Test
    public void locationShouldBeSetCorrectly() {
        String location = "www.weichware10.com/running-owl.png";
        TestBild bild = new TestBild(location);
        assertEquals("The location should be the same", location, bild.location);
    }
}
