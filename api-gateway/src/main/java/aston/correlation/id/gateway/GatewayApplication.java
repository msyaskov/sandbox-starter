package aston.correlation.id.gateway;

import aston.correlation.id.CorrelatedId;
import aston.correlation.id.CorrelationId;
import aston.correlation.id.CorrelationIdGenerator;
import aston.correlation.id.conditional.ConditionalOnBefore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @CorrelatedId
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://localhost:8081/first").build();
    }

    @Bean
    public CorrelationIdGenerator correlationIdGenerator() {
        return () -> new CorrelationId(UUID.randomUUID().toString());
    }

    @Bean
//    @ConditionalOnBefore(timestamp = 1735678800000L) // 1 января 2025 года
    @ConditionalOnBefore(timestamp = 1672520400000L) // 1 января 2023 года
    public String onBeforeConditionString() {
        return "onBeforeConditionString";
    }

    @Bean
    public CommandLineRunner clr(@Autowired(required = false) String onBeforeConditionString) {
        return args -> {
            System.out.println("onBeforeConditionString = " + onBeforeConditionString);
        };
    }

}
