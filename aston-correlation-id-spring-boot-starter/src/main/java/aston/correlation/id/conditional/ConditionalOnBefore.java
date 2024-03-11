package aston.correlation.id.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Conditional}, которое соответствует только тогда, когда текущее время раньше чем указанный {@code timestamp}.
 *
 * @author Максим Яськов
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Conditional(OnBeforeCondition.class)
public @interface ConditionalOnBefore {

    /**
     * @return Unix-time in milliseconds
     */
    long timestamp();

}
