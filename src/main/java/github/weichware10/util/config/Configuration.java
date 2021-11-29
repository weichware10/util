package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;

/**
 * Speichert allgemeine Konfiguration sowie Tool-Konfigurationen.
 */
public class Configuration {
    protected ToolType[] tools;
    protected String saveLocation;
    public CodeChartsConfiguration codeChartsConfiguration;
    public EyeTrackingConfiguration eyeTrackingConfiguration;
    public ZoomMapsConfiguration zoomMapsConfiguration;

    /**
     * allgemeiner Speicher-Ort für Einstellungen.
     */
    public Configuration(ToolType[] tools) throws IllegalArgumentException {
        if (!checkToolInput(tools)) {
            throw new IllegalArgumentException("Faulty tool input.");
        }

        this.tools = tools;

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
}
