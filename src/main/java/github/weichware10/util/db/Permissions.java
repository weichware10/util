package github.weichware10.util.db;

import github.weichware10.util.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse zum Abfragen von Permissions.
 */
public class Permissions {

    // enum Werte
    public static final Permission SELECT = Permission.SELECT;
    public static final Permission UPDATE = Permission.UPDATE;
    public static final Permission INSERT = Permission.INSERT;
    public static final Permission DELETE = Permission.DELETE;

    // einzelne Berechtigungen
    public final Set<Permission> configurations;
    public final Set<Permission> trials;
    public final Set<Permission> datapoints;
    public final Set<Permission> strings;

    // vorgefertigte Abfragen
    public final boolean isSubject;
    public final boolean isSpectator;
    public final boolean isAuthor;
    public final boolean isAdmin;

    private final DataBaseClient dataBaseClient;

    /**
     * Erstellt ein neues Permissions Objekt
     * und überprüft die Permissions für den Nutzer des {@link DataBaseClient}s.
     *
     * @param dataBaseClient - der verknüpfte {@link DataBaseClient}
     */
    public Permissions(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
        configurations = Collections.unmodifiableSet(getPermissions("configurations"));
        trials = Collections.unmodifiableSet(getPermissions("trials"));
        datapoints = Collections.unmodifiableSet(getPermissions("datapoints"));
        strings = Collections.unmodifiableSet(getPermissions("strings"));

        isSubject = satisfiesSubject();
        isSpectator = satisfiesSpectator();
        isAuthor = satisfiesAuthor();
        isAdmin = satisfiesAdmin();

    }

    /**
     * Fragt Berechtigungen für eine Tabelle ab.
     *
     * @param table - Tabelle die abgefragt werden soll.
     * @return Berechtigungs-Liste
     */
    private Set<Permission> getPermissions(String table) {
        // Abfrage
        // Beschränkung auf interessante Werte
        final String queryF = """
                SELECT DISTINCT privilege_type
                FROM information_schema.role_table_grants
                WHERE table_name='%s' AND table_schema='%s'
                AND privilege_type SIMILAR TO '%s';
                """;

        final String typesRegex = Arrays.toString(Permission.values())
                .replace("[", "(")
                .replace("]", ")")
                .replace(", ", "|");

        final String query = String.format(queryF, table, dataBaseClient.schema, typesRegex);

        Set<Permission> result = new HashSet<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                result.add(Permission.valueOf(rs.getString(1)));
            }
        } catch (SQLException e) {
            Logger.error("SQLException when getting permissions", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return result;
    }

    /**
     * Überprüft, ob der angemeldete Nutzer die angegebenen Berechtigungen hat.
     *
     * <p>Jeder Parameter kann {@code null} sein,
     * falls die Berechtigungen für diese Tabelle nicht beachtet werden sollen
     *
     * @param configurationsPermissions - Berechtigungen für configurations-Tabelle
     * @param trialsPermissions         - Berechtigungen für trials-Tabelle
     * @param datapointsPermissions     - Berechtigungen für datapoints-Tabelle
     * @return Berechtigungserfüllungsboolean
     */
    public boolean satisfies(Collection<Permission> configurationsPermissions,
            Collection<Permission> trialsPermissions,
            Collection<Permission> datapointsPermissions,
            Collection<Permission> stringsPermissions) {
        return permissionsSatisfies(configurationsPermissions, configurations)
                && permissionsSatisfies(trialsPermissions, trials)
                && permissionsSatisfies(datapointsPermissions, datapoints)
                && permissionsSatisfies(stringsPermissions, strings);
    }

    private boolean permissionsSatisfies(Collection<Permission> desired,
            Collection<Permission> actual) {
        return (desired != null) ? actual.containsAll(desired) : true;
    }

    private boolean satisfiesAdmin() {
        // hat alle Rechte
        return satisfies(
                Arrays.asList(Permission.values()),
                Arrays.asList(Permission.values()),
                Arrays.asList(Permission.values()),
                Arrays.asList(Permission.values()));
    }

    private boolean satisfiesAuthor() {
        return satisfies(
                Set.of(SELECT, INSERT),
                Set.of(SELECT, UPDATE, INSERT),
                Set.of(SELECT, INSERT),
                Set.of(SELECT, INSERT));
    }

    private boolean satisfiesSpectator() {
        return satisfies(
                Set.of(SELECT),
                Set.of(SELECT),
                Set.of(SELECT),
                Set.of(SELECT));
    }

    private boolean satisfiesSubject() {
        return satisfies(
                Set.of(SELECT),
                Set.of(SELECT, UPDATE),
                Set.of(INSERT),
                Set.of(SELECT));
    }
}
