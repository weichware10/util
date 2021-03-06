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
    protected String intro = "hello there";
    protected String outro = "bye bye general kenobi";
    protected String imageUrl = "url";
    protected boolean tutorial = true;

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
     * @param configId                - configId der übergebenen Konfiguration
     * @param imageUrl                - die URL des Bildes
     * @param intro                   - das Intro
     * @param outro                   - das Outro
     * @param tutorial                - ob das Tutorial angezeigt werden soll
     * @param codeChartsConfiguration - Konfiguration von CodeCharts
     */
    public Configuration(String configId, String imageUrl,
            String intro, String outro, boolean tutorial,
            CodeChartsConfiguration codeChartsConfiguration) {
        this.toolType = ToolType.CODECHARTS;
        this.configId = configId;
        this.imageUrl = imageUrl;
        this.intro = intro;
        this.outro = outro;
        this.tutorial = tutorial;
        this.codeChartsConfiguration = codeChartsConfiguration;
        this.question = null;
    }

    /**
     * Konstruktor für Configuration vom Typ ZoomMapsConfiguration.
     *
     * @param configId              - configId der übergebenen Konfiguration
     * @param question              - Fragestellung zum Versuch
     * @param zoomMapsConfiguration - Konfiguration von ZoomMaps
     */
    public Configuration(String configId, String question, String imageUrl,
            String intro, String outro, boolean tutorial,
            ZoomMapsConfiguration zoomMapsConfiguration) {
        this.toolType = ToolType.ZOOMMAPS;
        this.configId = configId;
        this.question = question;
        this.imageUrl = imageUrl;
        this.intro = intro;
        this.outro = outro;
        this.tutorial = tutorial;
        this.zoomMapsConfiguration = zoomMapsConfiguration;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getQuestion() {
        return question;
    }

    public String getIntro() {
        return intro;
    }

    public String getOutro() {
        return outro;
    }

    public boolean getTutorial() {
        return tutorial;
    }

    public String getConfigId() {
        return configId;
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
                && ((codeChartsConfiguration == that.codeChartsConfiguration)
                        || codeChartsConfiguration.equals(that.codeChartsConfiguration))
                && ((zoomMapsConfiguration == that.zoomMapsConfiguration)
                        || zoomMapsConfiguration.equals(that.zoomMapsConfiguration))
                && ((imageUrl == that.imageUrl)
                        || imageUrl.equals(that.imageUrl))
                && ((question == that.question)
                        || question.equals(that.question))
                && ((intro == that.intro)
                        || intro.equals(that.intro))
                && ((outro == that.outro)
                        || outro.equals(that.outro))
                && tutorial == that.tutorial;
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
                            intro: %s
                            outro: %s
                            imageUrl: %s
                            tutorial: %b
                            %s
                            %s
                        }
                        """,
                toolType,
                configId,
                trialId,
                question,
                intro,
                outro,
                imageUrl,
                tutorial,
                codeChartsConfiguration,
                zoomMapsConfiguration);
    }
}
