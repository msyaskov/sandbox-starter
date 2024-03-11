package aston.correlation.id.first;

import aston.correlation.id.CorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FirstServiceController {

    private static final Logger log = LoggerFactory.getLogger(FirstServiceController.class);

    private final CorrelationId correlationId;

    private final RestTemplate restTemplate;

    public FirstServiceController(CorrelationId correlationId, RestTemplate restTemplate) {
        this.correlationId = correlationId;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/first/trace")
    public String firstServiceWithCorrelationId() {
        log.info("First -> retrieved correlationId: {}", correlationId.get());

        String traceBody = restTemplate.getForObject("/trace", String.class);
        return "<p>First Service: %s</p>%s".formatted(correlationId.get(), traceBody);
    }

}
