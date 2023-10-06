package mbp.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${mbp.booking.API_KEY}")
    public  String API_KEY;
    @Bean
    public ApiKeyInterceptor apiKeyInterceptor() {
        return new ApiKeyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor())
                .addPathPatterns("/onboard/**"); // Secure the /onboard endpoint
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
