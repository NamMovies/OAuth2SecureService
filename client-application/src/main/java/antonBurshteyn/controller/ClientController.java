package antonBurshteyn.controller;

import antonBurshteyn.dto.UserProfileDto;
import antonBurshteyn.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class ClientController {

    private final ClientService resourceClientService;

    public ClientController(ClientService resourceClientService) {
        this.resourceClientService = resourceClientService;
    }


    @Operation(summary = "Get public content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Public content retrieved",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping({"/", "/public"})
    public String publicPage(Model model) {
        String publicContent = resourceClientService.getPublic();
        model.addAttribute("publicContent", publicContent);
        model.addAttribute("timestamp", new java.util.Date().toString());
        return "public-page";
    }

    @Operation(summary = "Get secure profile data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile data retrieved",
                    content = @Content(schema = @Schema(implementation = UserProfileDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/secure")
    public String profile(Model model) {
        UserProfileDto profile = resourceClientService.getProfile();
        model.addAttribute("profileData", profile);
        return "profile-view";
    }

    @GetMapping("/token")
    public String tokenPage(Model model) {
        Map<String, Object> tokenInfo = resourceClientService.getTokenInfo();
        model.addAttribute("token", tokenInfo.get("token"));
        return "token-page";
    }

}


