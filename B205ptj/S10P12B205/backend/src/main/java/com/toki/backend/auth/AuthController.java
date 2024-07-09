package com.toki.backend.auth;

import com.toki.backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserRepository userRepository;

    @GetMapping("/login")
    public ResponseEntity<?> index(){
        log.warn("요청되돌아옴");
        System.out.println("요청돌아옴");


        return ResponseEntity.created(URI.create("localhost:5173/"))
                .build();
    }
    @GetMapping("/login2")
    public ResponseEntity<?> oauthLogin(){
        log.warn("요청되돌아옴");

        return ResponseEntity.created(URI.create("localhost:5173/"))
                .build();
    }

}
