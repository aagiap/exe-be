package com.exebe.controller;


import com.exebe.base.BaseController;
import com.exebe.base.BaseResponse;
import com.exebe.constant.UserRole;
import com.exebe.dto.user.UserProfileResponseDTO;
import com.exebe.entity.User;
import com.exebe.exception.CustomException;
import com.exebe.repository.UserRepository;
import com.exebe.security.UserPrincipal;
import com.exebe.service.UserService;
import com.exebe.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/{id}")
    public BaseResponse<?> getOne(@PathVariable Long id) {
        return wrapSuccess(userService.getOne(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UserRole role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return wrapSuccess(userService.search(keyword, role, page, pageSize));
    }

    @GetMapping("/me")
    public BaseResponse<UserProfileResponseDTO> getCurrentUser() {
        UserPrincipal principal =
                SecurityUtils.requester()
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                401, "UNAUTHORIZED", HttpStatus.UNAUTHORIZED));

        User user =
                userRepository
                        .findByUsername(principal.getUsername())
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                404, "User not found", HttpStatus.NOT_FOUND));

        UserProfileResponseDTO dto =
                UserProfileResponseDTO.builder()
                        .username(user.getUsername())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .isEnable(user.isEnable())
                        .role(user.getRole().name())
                        .build();

        return wrapSuccess(dto);
    }
}
