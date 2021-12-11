package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;

/**
 * Speichert allgemeine Konfiguration sowie Tool-Konfigurationen.
 */
public class Configuration {
    protected ToolType toolType;
    protected String trialId = "trialId";
    protected String configId = "configId";
    protected String question = "Is this a question?";

    protected CodeChartsConfiguration codeChartsConfiguration;
    // protected EyeTrackingConfiguration eyeTrackingConfiguration;
    protected ZoomMapsConfiguration zoomMapsConfiguration;

    /**
     * Konstruktor ohne Initialisierungen,
     * sodass eine leere Konfiguration erstellt werden kann.
     */
    protected Configuration() {
        ; // ohne Initialisierungen
    }

    /**
     * Konstruktor für Configuration vom Typ CodeChartsConfiguration.
     *
     * @param configId - configId der übergebenen Konfiguration
     * @param codeChartsConfiguration - Konfiguration von CodeCharts
     */
    public Configuration(String configId, CodeChartsConfiguration codeChartsConfiguration) {
        this.toolType = ToolType.CODECHARTS;
        this.configId = configId;
        this.codeChartsConfiguration = codeChartsConfiguration;
    }

    // /**
    //  * Konstruktor für Configuration vom Typ EyeTrackingConfiguration.
    //  *
    //  * @param configId - configId der übergebenen Konfiguration
    //  * @param eyeTrackingConfiguration - Konfiguration von EyeTracking
    //  */
    // public Configuration(String configId, EyeTrackingConfiguration eyeTrackingConfiguration) {
    //     this.toolType = ToolType.EYETRACKING;
    //     this.configId = configId;
    //     this.eyeTrackingConfiguration = eyeTrackingConfiguration;
    // }

    /**
     * Konstruktor für Configuration vom Typ ZoomMapsConfiguration.
     *
     * @param configId - configId der übergebenen Konfiguration
     * @param zoomMapsConfiguration - Konfiguration von ZoomMaps
     */
    public Configuration(String configId, ZoomMapsConfiguration zoomMapsConfiguration) {
        this.toolType = ToolType.ZOOMMAPS;
        this.configId = configId;
        this.zoomMapsConfiguration = zoomMapsConfiguration;
    }

    // /**
    //  * allgemeiner Speicher-Ort für Einstellungen.
    //  * (Constructor mit Übergabe von non-default-Tools)
    //  */
    // public Configuration(ToolType toolType) {

    //     this.toolType = toolType;
    //     switch (toolType) {
    //         case CODECHARTS:
    //             codeChartsConfiguration = new CodeChartsConfiguration();
    //             break;
    //         case EYETRACKING:
    //             eyeTrackingConfiguration = new EyeTrackingConfiguration();
    //             break;
    //         default: // ZOOMMAPS
    //             zoomMapsConfiguration = new ZoomMapsConfiguration();
    //             break;
    //     }
    // }

    // GETTERS

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

    // public EyeTrackingConfiguration getEyeTrackingConfiguration() {
    //     return eyeTrackingConfiguration;
    // }

    public ZoomMapsConfiguration getZoomMapsConfiguration() {
        return zoomMapsConfiguration;
    }

    public String getConfigId() {
        return configId;
    }

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
                            saveLocation: %s
                            %s,
                            %s
                        }
                        """,
                toolType,
                trialId,
                codeChartsConfiguration,
                // eyeTrackingConfiguration,
                zoomMapsConfiguration);
    }
}
