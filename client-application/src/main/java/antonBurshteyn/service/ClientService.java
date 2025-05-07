package antonBurshteyn.service;

import antonBurshteyn.dto.UserProfileDto;

import java.util.Map;

public interface ClientService {

    String getPublic();

    UserProfileDto getProfile();

    Map<String, Object> getTokenInfo();
}
