package github.weichware10.util.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Test;

/**
 * Testet {@link Permissions}.
 */
public class PermissionsTest {
    @Test
    public void rolesAreCorrect() {
        Dotenv dotenv = Dotenv.load();
        DataBaseClient adminClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        assertTrue("test_admin should be admin", adminClient.permissions.isAdmin);
        assertTrue("test_admin should be author", adminClient.permissions.isAuthor);
        assertTrue("test_admin should be subject", adminClient.permissions.isSubject);
        assertTrue("test_admin should be spectator", adminClient.permissions.isSpectator);

        DataBaseClient authorClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_author",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        assertFalse("test_author should not be admin", authorClient.permissions.isAdmin);
        assertTrue("test_author should be author", authorClient.permissions.isAuthor);
        assertTrue("test_author should be subject", authorClient.permissions.isSubject);
        assertTrue("test_author should be spectator", authorClient.permissions.isSpectator);

        DataBaseClient subjectClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_subject",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        assertFalse("test_subject should not be admin", subjectClient.permissions.isAdmin);
        assertFalse("test_subject should not be author", subjectClient.permissions.isAuthor);
        assertTrue("test_subject should be subject", subjectClient.permissions.isSubject);
        assertFalse("test_subject should not be spectator", subjectClient.permissions.isSpectator);

        DataBaseClient spectatorClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_spectator",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        assertFalse("test_spectator should not be admin", spectatorClient.permissions.isAdmin);
        assertFalse("test_spectator should not be author", spectatorClient.permissions.isAuthor);
        assertFalse("test_spectator should not be subject", spectatorClient.permissions.isSubject);
        assertTrue("test_spectator should be spectator", spectatorClient.permissions.isSpectator);
    }

    @Test
    public void testName() {
        Dotenv dotenv = Dotenv.load();
        DataBaseClient spectatorClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_spectator",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        assertTrue("satisfies() should not care about null values",
                spectatorClient.permissions.satisfies(null, null, null, null));
    }
}
