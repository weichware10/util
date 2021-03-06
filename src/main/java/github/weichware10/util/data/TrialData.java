package github.weichware10.util.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import github.weichware10.util.Logger;
import github.weichware10.util.ToolType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import org.joda.time.DateTime;

/**
 * Speichert die TrialData für die verschiedenen Tools intern.
 *
 * <p>auch für die Datenübertragung genutzt
 *
 * @since v0.2
 */
public class TrialData {

    public final ToolType toolType;
    public final String trialId;
    public final String configId;
    public final DateTime startTime;
    private String answer;
    private List<DataPoint> dataPoints;

    /**
     * Konstruktor für Jackson.
     *
     * @param toolType  - Tool-Typ des Versuchs
     * @param trialId   - ID des Versuchs
     * @param configId  - Konfiguration des Versuchs
     * @param startTime - Startzeitpunkt des Versuchs
     *
     * @since v1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TrialData(@JsonProperty("toolType") ToolType toolType,
            @JsonProperty("trialId") String trialId, @JsonProperty("configId") String configId,
            @JsonProperty("startTime") DateTime startTime, @JsonProperty("answer") String answer,
            @JsonProperty("data") List<DataPoint> dataPoints) {
        this.toolType = toolType;
        this.trialId = trialId;
        this.configId = configId;
        this.startTime = startTime;
        this.answer = answer;
        this.dataPoints = dataPoints;
    }

    /**
     * Speichert die TrialData für die verschiedenen Tools intern.
     *
     * @param toolType  - Tool-Typ des Versuchs
     * @param trialId   - ID des Versuchs
     * @param configId  - Konfiguration des Versuchs
     *
     * @since v0.2
     */
    public TrialData(ToolType toolType, String trialId, String configId) {
        this.toolType = toolType;
        this.trialId = trialId;
        this.configId = configId;
        this.startTime = DateTime.now();
        this.dataPoints = new ArrayList<DataPoint>();
    }

    public List<DataPoint> getData() {
        return dataPoints;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public String getTrialId() {
        return trialId;
    }

    public String getConfigId() {
        return configId;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Fügt ein Datenpunkt hinzu (CodeCharts).
     *
     * @since v0.2
     */
    public void addDataPoint(Rectangle2D viewport, int depth) {

        if (toolType != ToolType.CODECHARTS) {
            throw new IllegalArgumentException("Can only add CODECHARTS DataPoints.");
        }

        // kann "ohne" Bedenken gecastet werden,
        // damit Overflow auftritt, müsste zwischen Anfang und jetzt ca 25 Tage liegen.
        int timeOffset = (int) (DateTime.now().getMillis() - startTime.getMillis());

        dataPoints.add(new DataPoint(dataPoints.size(), timeOffset, viewport, depth));
    }

    /**
     * Fügt ein Datenpunkt hinzu (ZoomMaps).
     *
     * @param viewport - aktueller Ausschnitt beim ZoomBild
     *
     * @since v1.2
     */
    public void addDataPoint(Rectangle2D viewport) {

        if (toolType != ToolType.ZOOMMAPS) {
            throw new IllegalArgumentException("Can only add ZOOMMAPS DataPoints.");
        }

        // kann "ohne" Bedenken gecastet werden,
        // damit Overflow auftritt, müsste zwischen Anfang und jetzt ca 25 Tage liegen.
        int timeOffset = (int) (DateTime.now().getMillis() - startTime.getMillis());

        dataPoints.add(new DataPoint(dataPoints.size(), timeOffset, viewport));
    }

    /**
     * Lädt eine Versuch/Trial aus einer JSON-Datei.
     *
     * @param location - Speicherort der Datei.
     *
     * @since v1.0
     */
    public static TrialData fromJson(String location) {
        TrialData trialData = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            // aus Datei lesen
            trialData = mapper.readValue(new File(location), TrialData.class);
        } catch (StreamReadException e) {
            Logger.info("An error occured while loading a trial", e, true);
        } catch (DatabindException e) {
            Logger.info("An error occured while loading a trial", e, true);
        } catch (IOException e) {
            Logger.info("An error occured while loading a trial", e, true);
        }
        return trialData;
    }

    /**
     * Speichert eine {@link TrialData} in einer JSON-Datei.
     *
     * @param location  - Speicherort der JSON-Datei
     * @param trialData - den abzuspeichernden Versuch/Trial
     * @return Erfolgsboolean
     *
     * @since v1.0
     */
    public static boolean toJson(String location, TrialData trialData) {
        // nur als JSON schreiben
        if (!location.endsWith(".json")) {
            return false;
        }
        try {
            // umwandeln von TrialData zu JSON String
            ObjectMapper om = new ObjectMapper().registerModule(new JodaModule());
            ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(trialData);

            // Öffnen der Datei
            File file = new File(location);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_16);
            BufferedWriter writer = new BufferedWriter(osw);

            // Schreiben des JSON
            writer.append(json);
            writer.close();

            return true; // Schreiben war erfolgreich
        } catch (JsonProcessingException e) {
            Logger.info("An error occured while writing a trial", e);
        } catch (IOException e) {
            Logger.info("An error occured while writing a trial", e);
        }
        return false; // Schreiben war nicht erfolgreich
    }

    @Override
    public String toString() {
        return String.format("""
                TrialData: {
                    toolType: %s
                    trialId: %s
                    configId: %s
                    startTime: %s
                    answer: %s
                    dataPoints: dataPoints[%d]
                }""",
                toolType.toString(),
                trialId,
                configId,
                startTime != null ? startTime.toString() : "null",
                answer,
                dataPoints.size());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TrialData that = (TrialData) (other);
        return toolType.equals(that.toolType)
                && (trialId == that.trialId || trialId.equals(that.trialId))
                && (configId == that.configId || configId.equals(that.configId))
                && startTime.equals(that.startTime)
                && (answer == that.answer || answer.equals(that.answer))
                && dataPoints.equals(that.dataPoints);
    }
}
