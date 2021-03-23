package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserbyId(Integer id) throws RecordNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void createUser(User user){
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
        return userRepository.findUserByToken(token)
                .orElseThrow(UnauthorizedException::new);
    }

    public User login(String name, String password) throws WrongFormatException {
        User user = userRepository.findUserByName(name)
                .orElseThrow(WrongFormatException::new);

        user.setToken(UUID.randomUUID().toString());

        userRepository.save(user);

        return user;
    }

    public void logout(String token) throws WrongFormatException {
        User user = userRepository.findUserByToken(token)
                .orElseThrow(WrongFormatException::new);

        user.setToken(null);

        userRepository.save(user);
    }
}
