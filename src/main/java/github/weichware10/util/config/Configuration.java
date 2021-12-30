package github.weichware10.util.config;

import github.weichware10.util.ToolType;

/**
 * Speichert allgemeine Konfiguration sowie Tool-Konfigurationen.
 */
public class Configuration {
    protected ToolType toolType;
    protected String trialId = "trialId";
    protected String configId = "configId";
    protected String question = "Is this a question?";

    protected CodeChartsConfiguration codeChartsConfiguration;
    protected ZoomMapsConfiguration zoomMapsConfiguration;

    /**
     * Konstruktor ohne Initialisierungen,
     * sodass eine leere Konfiguration erstellt werden kann.
     * auch wichtig für Michael JSON Jackson.
     */
    protected Configuration() {
        ; // ohne Initialisierungen
    }

    /**
     * Konstruktor für Configuration vom Typ CodeChartsConfiguration.
     *
     * @param configId - configId der übergebenen Konfiguration
     * @param question - Fragestellung zum Versuch
     * @param codeChartsConfiguration - Konfiguration von CodeCharts
     */
    public Configuration(String configId, String question,
            CodeChartsConfiguration codeChartsConfiguration) {
        this.toolType = ToolType.CODECHARTS;
        this.question = question;
        this.configId = configId;
        this.codeChartsConfiguration = codeChartsConfiguration;
    }

    /**
     * Konstruktor für Configuration vom Typ ZoomMapsConfiguration.
     *
     * @param configId - configId der übergebenen Konfiguration
     * @param question - Fragestellung zum Versuch
     * @param zoomMapsConfiguration - Konfiguration von ZoomMaps
     */
    public Configuration(String configId, String question,
            ZoomMapsConfiguration zoomMapsConfiguration) {
        this.toolType = ToolType.ZOOMMAPS;
        this.configId = configId;
        this.question = question;
        this.zoomMapsConfiguration = zoomMapsConfiguration;
    }

    // --- GETTERS ---

    public ToolType getToolType() {
        return toolType;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    public String getQuestion() {
        return question;
    }

    public String getTrialId() {
        return trialId;
    }

    public CodeChartsConfiguration getCodeChartsConfiguration() {
        return codeChartsConfiguration;
    }

    public ZoomMapsConfiguration getZoomMapsConfiguration() {
        return zoomMapsConfiguration;
    }

    public String getConfigId() {
        return configId;
    }

    // --- OVERRIDES ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Configuration that = (Configuration) (obj);
        return trialId.equals(that.trialId)
                && toolType == that.toolType
                && ((codeChartsConfiguration == null && that.codeChartsConfiguration == null)
                        || codeChartsConfiguration.equals(that.codeChartsConfiguration))
                // && ((eyeTrackingConfiguration == null && that.eyeTrackingConfiguration == null)
                //         || eyeTrackingConfiguration.equals(that.eyeTrackingConfiguration))
                && ((zoomMapsConfiguration == null && that.zoomMapsConfiguration == null)
                        || zoomMapsConfiguration.equals(that.zoomMapsConfiguration));
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Configuration: {
                            toolType: %s
                            configId: %s
                            trialId: %s
                            question: %s
                            %s
                            %s
                        }
                        """,
                toolType,
                configId,
                trialId,
                question,
                codeChartsConfiguration,
                // eyeTrackingConfiguration,
                zoomMapsConfiguration);
    }
}
