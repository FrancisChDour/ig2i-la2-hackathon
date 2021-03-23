package ig2i.la2.hackathon.ladydiary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK)
                .body("pong");
    }

}
