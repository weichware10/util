package github.weichware10.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * TODO: JUSTIN.
 */
public class SpeicherUtilitiesTest {
    @Test
    public void locationShouldBeSetCorrectly() {
        SpeicherUtilities instance = new SpeicherUtilities("7");
        assertEquals("7", instance.getLocation());
    }
}
