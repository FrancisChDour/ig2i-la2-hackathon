package ig2i.la2.hackathon.ladydiary.repositories;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(Integer id);
    
    Optional<User> findUserByName(String name);

    Optional<User> findUserByToken(String token);
}
