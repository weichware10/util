package github.weichware10.util.db;

import static org.junit.Assert.assertThrows;

import io.github.cdimascio.dotenv.Dotenv;
import java.security.InvalidParameterException;
import org.junit.Test;

/**
 * Testet DataBaseClient.
 */
public class DataBaseClientTest {

    @Test
    public void shouldNotThrowWithCorrectDataBase() {
        Dotenv dotenv = Dotenv.load();
        new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));
    }

    @Test
    public void shouldThrowWithIncorrectProps() {
        Dotenv dotenv = Dotenv.load();
        assertThrows(InvalidParameterException.class, () -> new DataBaseClient(
                "DB_URL",
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA")));
        assertThrows(InvalidParameterException.class, () -> new DataBaseClient(
                dotenv.get("DB_URL"),
                "DB_USERNAME",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA")));
        assertThrows(InvalidParameterException.class, () -> new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                "DB_PASSWORD",
                dotenv.get("DB_SCHEMA")));
        assertThrows(InvalidParameterException.class, () -> new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                "DB_SCHEMA"));
    }
}
