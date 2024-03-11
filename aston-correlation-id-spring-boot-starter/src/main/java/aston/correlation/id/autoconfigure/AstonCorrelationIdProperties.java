package aston.correlation.id.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aston.correlation-id")
public class AstonCorrelationIdProperties {

    /**
     * Name of the header containing the correlation id
     */
    private String headerName = "X-Correlation-Id";

    /**
     *
     */
    private boolean generator = false;

    public boolean isGenerator() {
        return generator;
    }

    public void setGenerator(boolean generator) {
        this.generator = generator;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

}
