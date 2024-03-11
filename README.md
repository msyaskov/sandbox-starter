# Песочница spring-boot-starter
В этом репозитории представлен пример стартера ([aston-correlation-id-spring-boot-starter](./aston-correlation-id-spring-boot-starter)).

Этот стартер в рамках цепочки запросов (от одного сервиса к другому) предоставляет единый `CorrelationId` доступный из кода.

Отключить стартер: `aston.correlation-id.enabled = false`  
Заголовок-носитель: `aston.correlation-id.header-name`, по умолчанию `X-Correlation-Id`

Передача осуществляется с помощью `RestTemplate`, определение бина которого нужно пометить аннотацией `@CorrelatedId`.
```java
@Configuration
public class ApplicationConfiguration {
    
    @Bean
    @CorrelatedId
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```
Такое определение бина добавляет в этот `RestTemplate` интерсептор `CorrelatedIdClientHttpRequestInterceptor`.  
`CorrelatedIdClientHttpRequestInterceptor` это бин, который можно переопределить (можно настроить способ доставки `CorrelationId`).


[API Gateway](./api-gateway) - сервис (точка входа клиента), который умеет генерировать `CorrelationId` за счет определения бина `CorrelationIdGenerator`.  
Важно! Если этот бин определен, то он **всегда** будет использоваться для создания `CorrelationId` игнорируя текущий запрос.

### ConditionalOnBefore
Также стартер (в качестве примера реализации условия) предоставляет аннотацию `@ConditionalOnBefore(timestamp = <long unix-time ms>)`.  
Условие срабатывает если текущее время раньше, чем указанное.

Эта реализация бессмысленна т.к. данный функционал возможно реализовать с помощью аннотации `@ConditionalOnExpression("T(java.lang.System).currentTimeMillis() < <timestamp>")` 
