package aston.correlation.id.conditional;

import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.system.JavaVersion;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import java.io.Serializable;
import java.nio.file.WatchService;
import java.util.EventListener;
import java.util.Optional;

@ConditionalOnBean(
        value = { WatchService.class, PrintService.class }, // мета-классы бинов
        type = { "java.nio.file.WatchService", "javax.print.PrintService" },
        annotation = { Service.class, Controller.class }, // аннотации которыми помечен бин
        name = { "watchService", "printService" }, // имена бинов
        search = SearchStrategy.ANCESTORS, // стратегия поиска бинов в иерархии контекстов
        parameterizedContainer = Optional.class // будут обнаруживаться как WatchService, так и Optional<WatchService> и т.д.
)
@ConditionalOnMissingBean( // когда в BeanFactory не содержится ни одного bean, отвечающего указанным требованиям
        value = { WatchService.class, PrintService.class }, // мета-классы бинов
        type = { "java.nio.file.WatchService", "javax.print.PrintService" },
        ignored = { Serializable.class, EventListener.class }, // мета-классы бинов которые следует игнорировать
        ignoredType = { "java.io.Serializable", "java.util.EventListener" },
        annotation = { Service.class, Controller.class }, // аннотации которыми помечен бин
        name = { "watchService", "printService" }, // имена бинов
        search = SearchStrategy.ANCESTORS, // стратегия поиска бинов в иерархии контекстов
        parameterizedContainer = Optional.class // будут обнаруживаться как WatchService, так и Optional<WatchService> и т.д.
)

// https://docs.spring.io/spring-framework/reference/integration/checkpoint-restore.html
@ConditionalOnCheckpointRestore // при восстановлении JVM по контрольной точке

@ConditionalOnClass(
        value = { WatchService.class, PrintService.class },
        name = { "java.nio.file.WatchService", "javax.print.PrintService" }
)
@ConditionalOnMissingClass({ "java.nio.file.WatchService", "javax.print.PrintService" })

@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES) // когда активна указанная облачная платформа
@ConditionalOnDefaultWebSecurity // когда активна security, и нет пользовательской конфигурации
@ConditionalOnEnabledResourceChain // когда включена цепочка обработки ресурсов
@ConditionalOnExpression("${spring.application.name} != null && ${spring.application.name}.length() > 10") // SpEL
@ConditionalOnGraphQlSchema // когда для приложения определена GraphQL-схема
@ConditionalOnJava(
        value = JavaVersion.SEVENTEEN, // версия
        range = ConditionalOnJava.Range.EQUAL_OR_NEWER // либо "Эта и новее", либо "Старше этой"
)
@ConditionalOnJndi // когда определен InitialContext
@ConditionalOnMissingFilterBean({}) // aliasFor ConditionalOnMissingBean, но для специфики фильтров

@ConditionalOnProperty (
        value = { "name", "display-name" }, // aliasFor name
        prefix = "spring.application", // общий префикс свойств
        name = { "name", "display-name" }, // имена свойств (не забываем про префикс)
        havingValue = "sandbox", // одно значение на все свойства
        matchIfMissing = false // если свойства не указаны, то условие не выполнится
)

@ConditionalOnRepositoryType( // когда включен определенный тип репозитория Spring Data
        type = RepositoryType.AUTO,
        store = "store-name"
)

@ConditionalOnResource( // когда указанные ресурсы в classpath
        resources = { "watch.properties", "print.properties" }
)

@ConditionalOnSingleCandidate( // aliasFor ConditionalOnBean, но для одного кандидата
        value = WatchService.class,
        type = "java.nio.file.WatchService",
        search = SearchStrategy.ALL
)
@ConditionalOnThreading(Threading.VIRTUAL) // с Java 21, виртуальные потоки или потоки платформы

@ConditionalOnWarDeployment // для приложений без встроенного сервера
@ConditionalOnNotWarDeployment // для приложений со встроенным сервером

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET) // для веб-приложений
@ConditionalOnNotWebApplication // для не веб-приложений
public class AllConditionals {
}
