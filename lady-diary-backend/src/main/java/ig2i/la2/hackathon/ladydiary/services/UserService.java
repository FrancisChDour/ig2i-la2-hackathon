package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public void CreateUser(User user){
        userRepository.save(user);
    }

    public void DeleteUser(Integer id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()){
            throw new RecordNotFoundException();
        }
        userRepository.delete(user.get());

    }
}
