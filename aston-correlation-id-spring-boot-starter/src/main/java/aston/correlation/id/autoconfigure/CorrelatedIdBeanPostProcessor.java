package aston.correlation.id.autoconfigure;

import aston.correlation.id.CorrelatedId;
import aston.correlation.id.CorrelatedIdClientHttpRequestInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

class CorrelatedIdBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext context;
    private final CorrelatedIdClientHttpRequestInterceptor interceptor;

    CorrelatedIdBeanPostProcessor(ApplicationContext context, CorrelatedIdClientHttpRequestInterceptor interceptor) {
        this.context = context;
        this.interceptor = interceptor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RestTemplate && context.findAnnotationOnBean(beanName, CorrelatedId.class) != null) {
            ((RestTemplate) bean).getInterceptors().add(interceptor);
        }
        return bean;
    }
}
