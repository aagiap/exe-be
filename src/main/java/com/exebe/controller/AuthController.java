package com.exebe.controller;


import com.exebe.base.BaseController;
import com.exebe.base.BaseResponse;
import com.exebe.dto.auth.LoginRequestDTO;
import com.exebe.dto.auth.LoginResponseDTO;
import com.exebe.dto.user.UserCreateRequestDTO;
import com.exebe.exception.CustomException;
import com.exebe.security.JwtTokenUtil;
import com.exebe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Value("${app.auth.tokenExpirationMs}")
    private long jwtExpirationMs;


    @PostMapping("/login")
    public BaseResponse<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            var authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()));

            var expiration = Instant.now().plus(jwtExpirationMs, ChronoUnit.MILLIS);

            var token = jwtTokenUtil.generateToken(request.getUsername(), expiration);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return wrapSuccess(new LoginResponseDTO(token, expiration));

        } catch (Exception e) {
            throw new CustomException(406, "Incorrect username or password!", HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/registry")
    public BaseResponse<?> register(@Valid @RequestBody UserCreateRequestDTO request) {
        return wrapSuccess(userService.registry(request));
    }

}
