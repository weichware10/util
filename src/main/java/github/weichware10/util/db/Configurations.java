package github.weichware10.util.db;

import java.util.Properties;

class Configurations {

    private final String url;
    private final Properties props;

    protected Configurations(String url, Properties props) {
        this.url = url;
        this.props = props;
    }
}
