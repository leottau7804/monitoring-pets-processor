package monitoring.pets.processor.model.communication;

/**
 * Solicitud de procesamiento de metricas de localicacion
 *
 * @author sergioleottau
 */
public class PositionMetricRequest extends MetricRequest {

    /**
     * Latitud
     */
    private String latitude;
    /**
     * Longitud
     */
    private String longitude;

    public PositionMetricRequest() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PositionMetricRequest{" + "latitude=" + latitude + ", longitude=" + longitude + ", MetricRequest = " + super.toString() + "}";
    }

}
