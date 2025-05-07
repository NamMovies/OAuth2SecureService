package antonBurshteyn;

import antonBurshteyn.controller.ClientController;
import antonBurshteyn.dto.UserProfileDto;
import antonBurshteyn.service.ClientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class
        })
@Import(ClientControllerTest.TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)

class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ClientService clientService() {
            ClientService mock = Mockito.mock(ClientService.class);
            Mockito.when(mock.getPublic()).thenReturn("Mocked Public Content");
            Mockito.when(mock.getProfile()).thenReturn(
                    new UserProfileDto(
                            "MockedFirst",
                            "MockedLast",
                            "mocked@example.com",
                            "mock-provider",
                            Instant.parse("2025-04-29T12:00:00Z"),
                            Instant.parse("2025-04-29T14:00:00Z")
                    )
            );
            return mock;
        }
    }


    @Test
    void shouldReturnPublicPageWithContent() throws Exception {
        mockMvc.perform(get("/public").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("public-page"))
                .andExpect(model().attribute("publicContent", "Mocked Public Content"))
                .andExpect(model().attributeExists("timestamp"));
    }

    @Test
    void shouldReturnSecureProfileViewWithMockedUser() throws Exception {
        mockMvc.perform(get("/secure").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("profile-view"))
                .andExpect(model().attributeExists("profileData"));


    }
}
