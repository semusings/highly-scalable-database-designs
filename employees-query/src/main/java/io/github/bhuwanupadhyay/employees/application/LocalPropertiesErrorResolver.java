package io.github.bhuwanupadhyay.employees.application;

import io.github.bhuwanupadhyay.employees.application.EmployeeHandler.MessageResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static io.github.bhuwanupadhyay.employees.application.EmployeeHandler.ErrorResource;

@Component
public class LocalPropertiesErrorResolver implements ErrorResolver {

    private final ResourceLoader resourceLoader;
    private final Properties properties = new Properties();

    public LocalPropertiesErrorResolver(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        Resource resource = this.resourceLoader.getResource("classpath:i18n/en.properties");
        properties.load(resource.getInputStream());
    }

    @Override
    public ErrorResource resolve(String errorId, Object... params) {
        String value = (String) properties.get(errorId);
        if (StringUtils.isEmpty(value))
            throw new NoTranslationFoundException(errorId);
        return new ErrorResource(errorId, List.of(new MessageResource("en", String.format(value, params))));
    }

}
