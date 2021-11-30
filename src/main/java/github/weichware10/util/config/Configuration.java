package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;
import java.util.Arrays;

/**
 * Speichert allgemeine Konfiguration sowie Tool-Konfigurationen.
 */
public class Configuration {
    private ToolType[] tools = {};
    private String saveLocation = "saveLocation";
    private CodeChartsConfiguration codeChartsConfiguration = null;
    private EyeTrackingConfiguration eyeTrackingConfiguration = null;
    private ZoomMapsConfiguration zoomMapsConfiguration = null;

    /**
     * allgemeiner Speicher-Ort für Einstellungen.
     * (Constructor mit default-Tools)
     */
    public Configuration() {
        initializeToolConfigs();
    }

    /**
     * allgemeiner Speicher-Ort für Einstellungen.
     * (Constructor mit Übergabe von non-default-Tools)
     */
    public Configuration(ToolType[] tools) throws IllegalArgumentException {
        if (!checkToolInput(tools)) {
            throw new IllegalArgumentException("Faulty tool input.");
        }

        this.tools = tools;
        initializeToolConfigs();
    }

    /**
     * Initialisiert die Tool-Configs, die in {@link #tools} angegeben sind.
     */
    private void initializeToolConfigs() {
        for (ToolType toolType : tools) {
            switch (toolType) {
                case CODECHARTS:
                    codeChartsConfiguration = new CodeChartsConfiguration();
                    break;
                case EYETRACKING:
                    eyeTrackingConfiguration = new EyeTrackingConfiguration();
                    break;
                default: // ZOOMMAPS
                    zoomMapsConfiguration = new ZoomMapsConfiguration();
                    break;
            }
        }
    }

    // GETTERS

    public ToolType[] getTools() {
        return tools;
    }

    public String getSaveLocation() {
        return saveLocation;
    }

    public CodeChartsConfiguration getCodeChartsConfiguration() {
        return codeChartsConfiguration;
    }

    public EyeTrackingConfiguration getEyeTrackingConfiguration() {
        return eyeTrackingConfiguration;
    }

    public ZoomMapsConfiguration getZoomMapsConfiguration() {
        return zoomMapsConfiguration;
    }

    /**
     * Überprüft, ob das dem Konstruktor übergebene tools-Array korrekt geformt ist.
     *
     * @param tools - das tools-Array
     * @return true, falls es richtig geformt ist, sonst false.
     */
    private boolean checkToolInput(ToolType[] tools) {
        if (tools.length > ToolType.values().length) {
            return false;
        }
        for (int i = 0; i < tools.length; i++) {
            for (int j = 0; j < tools.length; j++) {
                if (i != j && tools[i] == tools[j]) {
                    return false;
                }
            }
        }
        return true;
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
        return saveLocation.equals(that.saveLocation)
                && Arrays.equals(tools, that.tools)
                && ((codeChartsConfiguration == null && that.codeChartsConfiguration == null)
                        || codeChartsConfiguration.equals(that.codeChartsConfiguration))
                && ((eyeTrackingConfiguration == null && that.eyeTrackingConfiguration == null)
                        || eyeTrackingConfiguration.equals(that.eyeTrackingConfiguration))
                && ((zoomMapsConfiguration == null && that.zoomMapsConfiguration == null)
                        || zoomMapsConfiguration.equals(that.zoomMapsConfiguration));
    }

    @Override
    public String toString() {
        return String.format(
                """
                        Configuration: {
                            tools: %s
                            saveLocation: %s
                            %s,
                            %s,
                            %s
                        }
                        """,
                Arrays.toString(tools),
                saveLocation,
                codeChartsConfiguration,
                eyeTrackingConfiguration,
                zoomMapsConfiguration);
    }
}
