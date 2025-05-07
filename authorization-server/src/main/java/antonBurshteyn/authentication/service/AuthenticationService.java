package antonBurshteyn.authentication.service;

import antonBurshteyn.authentication.dto.RegisterRequestDto;
import antonBurshteyn.authentication.entity.User;


public interface AuthenticationService {

    User register(RegisterRequestDto request);
}