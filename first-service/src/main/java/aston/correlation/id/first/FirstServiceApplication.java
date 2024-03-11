package aston.correlation.id.first;

import aston.correlation.id.CorrelatedId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FirstServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstServiceApplication.class, args);
    }

    @Bean
    @CorrelatedId
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://localhost:8082/second").build();
    }

}
