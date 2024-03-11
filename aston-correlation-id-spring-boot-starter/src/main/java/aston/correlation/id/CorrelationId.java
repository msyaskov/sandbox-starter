package aston.correlation.id;

public class CorrelationId {

    private final String value;

    public CorrelationId(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
