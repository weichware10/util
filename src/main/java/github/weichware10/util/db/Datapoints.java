package github.weichware10.util.db;

import java.util.Properties;

class Datapoints {

    private final String url;
    private final Properties props;

    protected Datapoints(String url, Properties props) {
        this.url = url;
        this.props = props;
    }
}
