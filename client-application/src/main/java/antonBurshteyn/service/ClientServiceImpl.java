//package antonBurshteyn.service;
//
//import antonBurshteyn.dto.UserProfileDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//
//import java.util.Map;
//
//@Service
//public class ClientServiceImpl implements ClientService {
//
//    private final RestClient restClient;
//    private final OAuth2AuthorizedClientManager authorizedClientManager;
//    private final String publicEndpoint;
//    private final String secureEndpoint;
//
//    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
//
//    public ClientServiceImpl(RestClient restClient,
//                             OAuth2AuthorizedClientManager authorizedClientManager,
//                             @Value("https://resource-server:8081/resource/public") String publicEndpoint,
//                             @Value("https://resource-server:8081/resource/secure") String secureEndpoint) {
//        this.restClient = restClient;
//        this.authorizedClientManager = authorizedClientManager;
//        this.publicEndpoint = publicEndpoint;
//        this.secureEndpoint = secureEndpoint;
//    }
//
//    @Override
//    public String getPublic() {
//        logger.debug("Calling public resource endpoint: {}", publicEndpoint);
//        return restClient.get()
//                .uri(publicEndpoint)
//                .retrieve()
//                .body(String.class);
//    }
//
//    @Override
//    public UserProfileDto getProfile() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        logger.debug("Authenticated principal: {}", auth.getName());
//        OAuth2AuthorizeRequest req = OAuth2AuthorizeRequest.withClientRegistrationId("custom-provider")
//                .principal(auth)
//                .build();
//        OAuth2AuthorizedClient client = authorizedClientManager.authorize(req);
//        if (client == null || client.getAccessToken() == null) {
//            throw new IllegalStateException("No access to OAuth2-client");
//        }
//        String token = client.getAccessToken().getTokenValue();
//        logger.debug("Using access token: {}", token);
//        return restClient.get()
//                .uri(secureEndpoint)
//                .headers(h -> h.setBearerAuth(token))
//                .retrieve()
//                .body(UserProfileDto.class);
//    }
//
//    @Override
//    public Map<String, Object> getTokenInfo() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("custom-provider")
//                .principal(auth)
//                .build();
//        OAuth2AuthorizedClient client = authorizedClientManager.authorize(request);
//        if (client == null || client.getAccessToken() == null) {
//            throw new IllegalStateException("No access to OAuth2-client");
//        }
//        String token = client.getAccessToken().getTokenValue();
//        logger.info("Sending token to resource: {}", token);
//        return restClient.get()
//                .uri("https://localhost:8081/resource/token")  // это будет
//                .headers(h -> h.setBearerAuth(token))
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});
//    }
//}

package antonBurshteyn.service;

import antonBurshteyn.dto.UserProfileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final RestClient restClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(RestClient restClient, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restClient = restClient;
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public String getPublic() {
        logger.debug("Calling public resource endpoint: /resource/public");
        return restClient.get()
                .uri("/resource/public")
                .retrieve()
                .body(String.class);
    }

    @Override
    public UserProfileDto getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authenticated principal: {}", auth.getName());
        OAuth2AuthorizeRequest req = OAuth2AuthorizeRequest.withClientRegistrationId("custom-provider")
                .principal(auth)
                .build();
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(req);
        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("No access to OAuth2-client");
        }
        String token = client.getAccessToken().getTokenValue();
        logger.debug("Using access token: {}", token);
        return restClient.get()
                .uri("/resource/secure")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .body(UserProfileDto.class);
    }

    @Override
    public Map<String, Object> getTokenInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("custom-provider")
                .principal(auth)
                .build();
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(request);
        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("No access to OAuth2-client");
        }
        String token = client.getAccessToken().getTokenValue();
        logger.info("Sending token to resource: {}", token);
        return restClient.get()
                .uri("/resource/token")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}