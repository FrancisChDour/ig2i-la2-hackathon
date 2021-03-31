package ig2i.la2.hackathon.ladydiary.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordEncoderService {

    private static final int strength = 10;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderService(){
        bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
    }

    public String encode(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean compare(String plainTextPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(plainTextPassword, encodedPassword);
    }
}
