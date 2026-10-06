package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Asigna {@link InfraRetryAnalyzer} a todos los tests sin anotarlos uno por uno.
 * Se registra via ServiceLoader (META-INF/services/org.testng.ITestNGListener),
 * porque TestNG no admite transformers de anotaciones declarados con @Listeners.
 */
public class RetryTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(InfraRetryAnalyzer.class);
    }
}
