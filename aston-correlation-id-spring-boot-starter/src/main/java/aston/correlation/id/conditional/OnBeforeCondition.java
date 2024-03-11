package aston.correlation.id.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class OnBeforeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Long timestamp = (Long) Objects.requireNonNull(metadata.getAnnotationAttributes("aston.correlation.id.conditional.ConditionalOnBefore"))
                .get("timestamp");

        return timestamp != null && System.currentTimeMillis() < timestamp;
    }
}
