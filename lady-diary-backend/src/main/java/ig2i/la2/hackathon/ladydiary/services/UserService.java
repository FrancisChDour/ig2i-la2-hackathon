package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.domain.user.UserAlreadyExistsException;
import ig2i.la2.hackathon.ladydiary.domain.user.UserNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoderService passwordEncoderService;

    public User findUserbyId(Integer id) throws RecordNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findUserByName(user.getName()).isPresent()){
            throw new UserAlreadyExistsException(user.getName());
        }
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Integer id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()){
            throw new RecordNotFoundException();
        }
        userRepository.delete(user.get());
    }

    public User authenticate(String token) throws UnauthorizedException {
        User user = userRepository.findUserByToken(token)
                .orElseThrow(UnauthorizedException::new);

        if (user.getTokenExpirationDate().isBefore(LocalDateTime.now())){
            throw new UnauthorizedException();
        }
        return user;
    }

    public User login(String name, String password) throws UnauthorizedException, UserNotFoundException {

        User user = userRepository.findUserByName(name)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoderService.compare(password, user.getPassword())){
            throw new UnauthorizedException();
        }

        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpirationDate(LocalDateTime.now().plusHours(2));

        userRepository.save(user);

        return user;
    }

    public void logout(String token) throws WrongFormatException {
        User user = userRepository.findUserByToken(token)
                .orElseThrow(WrongFormatException::new);

        user.setToken(null);
        user.setTokenExpirationDate(null);

        userRepository.save(user);
    }
}
