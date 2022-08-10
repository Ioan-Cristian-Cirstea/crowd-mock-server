package ro.esolutions.crowdmockserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.esolutions.crowdmockserver.interceptors.Interceptor;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptor);
    }
}

