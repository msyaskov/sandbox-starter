package aston.correlation.id.gateway;

import aston.correlation.id.CorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    private final RestTemplate restTemplate;

    private final CorrelationId correlationId;

    public GatewayController(CorrelationId correlationId, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.correlationId = correlationId;
    }

    @GetMapping(path = "/trace")
    public String trace() {
        log.info("Gateway -> generated correlationId: {}", correlationId.get());

        String traceBody = restTemplate.getForObject("/trace", String.class);
        return "<p>API Gateway: %s</p>%s".formatted(correlationId.get(), traceBody);
    }

}
