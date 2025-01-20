package tech.saintbassanaga.authservice.controller;

/*
  Created by saintbassanaga {saintbassanaga}
  In the Project reservation-api at Tue - 12/17/24
 */

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.authservice.dtos.LoginDto;
import tech.saintbassanaga.authservice.dtos.AuthResponse;
import tech.saintbassanaga.authservice.dtos.UserCreationDto;
import tech.saintbassanaga.authservice.services.UserService;

@RestController
@RequestMapping(path = "/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Register a new user", description = "Registers a new user and returns success confirmation.")
    public ResponseEntity<String> register(@RequestBody UserCreationDto registerRequest) {
        String message  = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.SC_OK).body(message);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns access and refresh tokens.")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto authRequest) {
        AuthResponse response = userService.login(authRequest);
        return ResponseEntity.ok(response);
    }
}
