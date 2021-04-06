package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Retrieve all topics")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No user found"),
            @ApiResponse(code = 200, message = "The list of existing users")})
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

    @ApiOperation(value = "Retrieve an user")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "This user does not exist"),
            @ApiResponse(code = 200, message = "The retrieved user")})
    @GetMapping("/{idUser}")
    public ResponseEntity<User> findTopicById(@PathVariable Integer idUser) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserbyId(idUser));
    }

    @ApiOperation(value = "Create an user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 200, message = "User successfully updated")})
    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user, HttpServletRequest request) throws WrongFormatException {
        User userCreated = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("%s/%d", request.getRequestURL(), userCreated.getId())).build();
    }

    @ApiOperation(value = "Update an user from his ID. Non provided fields will override existing values to null")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User does not exit"),
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 200, message = "User successfully updated")})
    @PutMapping("/{idUser}")
    public ResponseEntity<HttpStatus> putUser(@PathVariable Integer idUser, @Valid @RequestBody User user) throws UserNotFoundException {
        user.setId(idUser);
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete a user from his ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User doest not exit"),
            @ApiResponse(code = 200, message = "User successfully deleted")})
    @DeleteMapping("/{idUser}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer idUser) throws UserNotFoundException {
        userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Deprecated
    @ApiOperation(value = "Login an user and return a token")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "user doest not exit"),
            @ApiResponse(code = 401, message = "User exits but password is incorrect")})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String name, @RequestParam String password) throws UnauthorizedException, UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(name, password).getToken());
    }

    @Deprecated
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader String token) throws WrongFormatException {
        userService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
