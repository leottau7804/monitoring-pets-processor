package monitoring.pets.processor.model.communication;

/**
 * Solicitud de procesamiento de metricas de salud.
 *
 * @author sergioleottau
 */
public class HealthMetricRequest extends MetricRequest {

    /**
     * Ritmo cardiaco
     */
    private String heartRate;
    /**
     * Ritmo respiratorio
     */
    private String breathingRate;

    public HealthMetricRequest() {
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBreathingRate() {
        return breathingRate;
    }

    public void setBreathingRate(String breathingRate) {
        this.breathingRate = breathingRate;
    }

    @Override
    public String toString() {
        return "HealthMetricRequest{" + "heartRate=" + heartRate + ", breathingRate=" + breathingRate + ", MetricRequest = " + super.toString() + "}";
    }

}
