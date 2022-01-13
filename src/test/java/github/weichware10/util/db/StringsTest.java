package github.weichware10.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testet {@link Strings}.
 */
public class StringsTest {
    static DataBaseClient dbClient;

    /**
     * Konstruktor.
     */
    @BeforeClass
    public static void setUpDataBaseClient() {
        Dotenv dotenv = Dotenv.load();
        dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));
    }

    @Test
    public void setAndGetStringShouldWork() {
        String stringId = Util.generateId("TEST_", 10);
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            strings.add(Util.generateId("STRING_", 3));
        }
        dbClient.strings.set(stringId, strings);

        List<String> dbStrings1 = dbClient.strings.get(stringId);
        assertEquals("List should contain 4 Strings", 4, dbStrings1.size());
        assertEquals(String.format("Second String from list should be %s", strings.get(1)),
                strings.get(1), dbStrings1.get(1));
        assertEquals(String.format("Fourth String from list should be %s", strings.get(3)),
                strings.get(3), dbStrings1.get(3));

        List<String> dbStrings2 = dbClient.strings.get("unknownId");
        assertNull("List should be null", dbStrings2);
    }

    @Test
    public void sizeShouldBeCorrect() {
        String stringId = Util.generateId("TEST_", 10);
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            strings.add(Util.generateId("STRING_", 3));
        }

        assertTrue("Strings should not yet be in the database",
                dbClient.strings.sizeOf(stringId) == 0);

        dbClient.strings.set(stringId, strings);
        assertTrue("Strings should have same size as local list",
                dbClient.strings.sizeOf(stringId) == strings.size());
    }

}
