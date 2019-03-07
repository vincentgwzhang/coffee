package tina.coffee.system.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.server.validation.ValidationConfig;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

public class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig> {

    private static final String RESOURCE_PACKAGE = "tina.coffee.resources";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ValidationConfig getContext(Class<?> type) {
        return new ValidationConfig().parameterNameProvider(new CustomParameterNameProvider());
    }

    private class CustomParameterNameProvider extends DefaultParameterNameProvider {
        private final Map<String, List<String>> parameterNameCache = new HashMap<>();

        @Override
        public List<String> getParameterNames(final Method method) {
            final String packageName = ClassUtils.getPackageName(method.getDeclaringClass());
            if (packageName.equals(RESOURCE_PACKAGE)) {
                final Method userMethod = retrieveUserMethod(method);
                final String userPackageName = ClassUtils.getPackageName(userMethod.getDeclaringClass());
                if (userPackageName.equals(RESOURCE_PACKAGE)) {
                    final String userMethodSignature = ClassUtils.getQualifiedMethodName(userMethod) + "(" + StringUtils.arrayToCommaDelimitedString(userMethod.getParameterTypes()) + ")";
                    //logger.info("[CustomParameterNameProvider] -> userMethodSignature:" + userMethodSignature);
                    if (parameterNameCache.containsKey(userMethodSignature)) {
                        return parameterNameCache.get(userMethodSignature);
                    }
                    final List<String> names = retrieveParameterNames(userMethod.getParameterAnnotations());
                    //logger.info("[CustomParameterNameProvider] -> names:" + names);
                    if (names != null && !parameterNameCache.containsKey(userMethodSignature)) {
                        parameterNameCache.put(userMethodSignature, names);
                        return names;
                    }
                }
            }
            return super.getParameterNames(method);
        }

        private Method retrieveUserMethod(final Method method) {
            if (ClassUtils.isCglibProxyClassName(method.getDeclaringClass().getName())) {
                final Class<?> userClass = ClassUtils.getUserClass(method.getDeclaringClass());
                if (ClassUtils.hasMethod(userClass, method.getName(), method.getParameterTypes())) {
                    return ClassUtils.getMethod(userClass, method.getName(), method.getParameterTypes());
                }
            }
            return method;
        }

        private List<String> retrieveParameterNames(Annotation[][] annotationsByParam) {
            final List<String> names = new ArrayList<>(annotationsByParam.length);
            for (int i = 0; i < annotationsByParam.length; i++) {
                final String name = retrieveParameterName(annotationsByParam[i]);
                names.add(name == null ? getPrefix() + i : name);
            }
            return names;
        }

        protected String getPrefix() {
            return "arg";
        }

        private String retrieveParameterName(Annotation[] annotations) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == QueryParam.class) {
                    return QueryParam.class.cast(annotation).value();
                }
                if (annotation.annotationType() == PathParam.class) {
                    return PathParam.class.cast(annotation).value();
                }
                if (annotation.annotationType() == FormParam.class) {
                    return FormParam.class.cast(annotation).value();
                }
                if (annotation.annotationType() == HeaderParam.class) {
                    return HeaderParam.class.cast(annotation).value();
                }
                if (annotation.annotationType() == RequestBody.class) {
                    return "The request body";
                }
            }
            return null;
        }
    }
}