package aston.correlation.id.second;

import aston.correlation.id.CorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SecondServiceController {

    private static final Logger log = LoggerFactory.getLogger(SecondServiceController.class);

    private final CorrelationId correlationId;

    public SecondServiceController(CorrelationId correlationId) {
        this.correlationId = correlationId;
    }

    @GetMapping("/second/trace")
    public String secondServiceWithCorrelationId() {
        log.info("Second -> retrieved correlationId: {}", correlationId.get());

        return "<p>Second Service: %s</p>".formatted(correlationId.get());
    }

}
