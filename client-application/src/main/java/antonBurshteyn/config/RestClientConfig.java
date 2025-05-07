package antonBurshteyn.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final Logger logger = LoggerFactory.getLogger(RestClientConfig.class);
    @Bean
    public RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        logger.info("RestTemplate with OAuth2 initialized");
        return RestClient.builder()
                .baseUrl("https://localhost:8081")
                .requestInterceptor(interceptor)
                .build();
    }
}
