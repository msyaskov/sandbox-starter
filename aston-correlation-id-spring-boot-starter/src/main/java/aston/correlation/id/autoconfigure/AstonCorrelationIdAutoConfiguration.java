package aston.correlation.id.autoconfigure;

import aston.correlation.id.CorrelatedIdClientHttpRequestInterceptor;
import aston.correlation.id.CorrelationId;
import aston.correlation.id.CorrelationIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@AutoConfiguration
@EnableConfigurationProperties(AstonCorrelationIdProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = "aston.correlation-id.enabled", havingValue = "true", matchIfMissing = true)
public class AstonCorrelationIdAutoConfiguration {

    private final AstonCorrelationIdProperties correlationIdProperties;

    public AstonCorrelationIdAutoConfiguration(AstonCorrelationIdProperties correlationIdProperties) {
        this.correlationIdProperties = correlationIdProperties;
    }

    @Bean
    @RequestScope
    @ConditionalOnMissingBean({CorrelationId.class, CorrelationIdGenerator.class})
    CorrelationId correlationId(HttpServletRequest request) {
        String correlationIdValue = request.getHeader(correlationIdProperties.getHeaderName());
        return new CorrelationId(correlationIdValue);
    }

    @Bean
    @RequestScope
    @ConditionalOnMissingBean(CorrelationId.class)
    @ConditionalOnBean(CorrelationIdGenerator.class)
    CorrelationId generatedCorrelationId(CorrelationIdGenerator correlationIdGenerator) {
        return correlationIdGenerator.generate();
    }

    @Bean
    @ConditionalOnMissingBean(CorrelatedIdClientHttpRequestInterceptor.class)
    CorrelatedIdClientHttpRequestInterceptor correlationIdClientHttpRequestInterceptor(CorrelationId correlationId) {
        return new CorrelatedIdClientHttpRequestInterceptor(correlationId, correlationIdProperties.getHeaderName());
    }

    @Bean
    @ConditionalOnBean(CorrelatedIdClientHttpRequestInterceptor.class)
    CorrelatedIdBeanPostProcessor correlatedIdBeanPostProcessor(ApplicationContext context, CorrelatedIdClientHttpRequestInterceptor interceptor) {
        return new CorrelatedIdBeanPostProcessor(context, interceptor);
    }
}
