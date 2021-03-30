package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @ApiOperation(value = "Retrieve all records")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records found"),
            @ApiResponse(code = 200, message = "Re records")})
    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();

        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) throws WrongFormatException {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer idUser) throws RecordNotFoundException {
        userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation(value = "Login an user and return a token")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "user doest not exit"),
            @ApiResponse(code = 401, message = "User exits but password is incorrect")})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String name, @RequestParam String password) throws UnauthorizedException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(name, password).getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader String token) throws WrongFormatException {
        userService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
