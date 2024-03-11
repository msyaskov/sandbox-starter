package aston.correlation.id;

@FunctionalInterface
public interface CorrelationIdGenerator {

    CorrelationId generate();

}
