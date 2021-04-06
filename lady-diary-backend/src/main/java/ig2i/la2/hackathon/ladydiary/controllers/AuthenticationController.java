package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.user.AuthenticationCredentials;
import ig2i.la2.hackathon.ladydiary.domain.user.AuthenticationToken;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.domain.user.UserNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @ApiOperation(value = "Login an user and return a token")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Somethings is wrong with your request"),
            @ApiResponse(code = 404, message = "user doest not exit"),
            @ApiResponse(code = 401, message = "User exits but password is incorrect")})
    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@Valid @RequestBody AuthenticationCredentials authenticationCredentials) throws UnauthorizedException, UserNotFoundException {
        User user = userService.login(authenticationCredentials.getUsername(), authenticationCredentials.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthenticationToken.builder()
                        .token(user.getToken())
                        .expirationDate(user.getTokenExpirationDate())
                        .build());
    }

    @ApiOperation(value = "Logout an user from his token")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Somethings is wrong with your request"),
            @ApiResponse(code = 200, message = "user successfully logout")})
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader String token) throws WrongFormatException {
        userService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
