package aston.correlation.id;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CorrelatedIdClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final CorrelationId correlationId;
    private final String correlationIdHeaderName;

    public CorrelatedIdClientHttpRequestInterceptor(CorrelationId correlationId, String correlationIdHeaderName) {
        this.correlationId = correlationId;
        this.correlationIdHeaderName = correlationIdHeaderName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(correlationIdHeaderName, correlationId.get());
        return execution.execute(request, body);
    }
}
