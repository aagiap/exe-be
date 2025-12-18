package com.exebe.service;


import com.exebe.base.BaseResponse;
import com.exebe.base.PageDTO;
import com.exebe.constant.ErrorCode;
import com.exebe.constant.UserRole;
import com.exebe.dto.user.UserCreateRequestDTO;
import com.exebe.dto.user.UserDTO;
import com.exebe.dto.user.UserProfileRequestDTO;
import com.exebe.entity.User;
import com.exebe.exception.CustomException;
import com.exebe.exception.NoResultException;
import com.exebe.handler.UserHandler;
import com.exebe.mapper.UserMapper;
import com.exebe.repository.UserRepository;
import com.exebe.util.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getOne(Long id) {
        User user =
                userRepository.findById(id)
                        .orElseThrow(() -> new NoResultException("NOT FOUND"));

        return UserMapper.toUserDTO(user);
    }

    public PageDTO<UserDTO> search(String keyword, UserRole role, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<User> result =
                userRepository.findAll(UserHandler.createSearchUserSpec(keyword, role), pageable);

        return PageDTO.of(result.getContent(), UserMapper::toUserDTO,
                result.getTotalElements(), page, size);
    }

    public UserDTO registry(UserCreateRequestDTO request){
        var checkIsExisted = (userRepository.findByUsername(request.getUsername()).isPresent()
                || userRepository.findByEmail(request.getEmail()).isPresent());

        if(checkIsExisted) {
            throw new CustomException(400, "Username Or Email is Existed!", HttpStatus.BAD_REQUEST);
        }

        User user =
                User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .isEnable(true)
                        .role(UserRole.ROLE_MEMBER)
                        .build();
        userRepository.save(user);

        return UserMapper.toUserDTO(user);
    }

    public BaseResponse<String> updateProfile(User user, UserProfileRequestDTO request){
        String typeUpdate = request.getType();
        switch (typeUpdate) {
            case "BASIC":
                String validationMessage = validateCaseBasic(request);
                if(validationMessage != null){
                    return BaseResponse.failure(validationMessage, ErrorCode.INVALID_INPUT.getCode());
                }
                user.setFullName(request.getFullName());
                user.setPhone(request.getPhone());
                try {
                    userRepository.save(user);
                } catch (Exception e){
                    return BaseResponse.failure("Update profile failed", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
                }
                return BaseResponse.success("Update profile successfully");
            case "ADDRESS":
                String validationAddressMessage = validateCaseAddress(request);
                if(validationAddressMessage != null){
                    return BaseResponse.failure(validationAddressMessage, ErrorCode.INVALID_INPUT.getCode());
                }
                user.setAddress(request.getAddress());
                try {
                    userRepository.save(user);
                } catch (Exception e){
                    return BaseResponse.failure("Update profile failed", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
                }
                return BaseResponse.success("Update address successfully");
            default:
                throw new CustomException(ErrorCode.BAD_REQUEST.getCode(), "Type update is invalid", HttpStatus.BAD_REQUEST);
        }
    }

    private String validateCaseBasic(UserProfileRequestDTO request){
        if(request.getFullName() == null || request.getFullName().isEmpty()){
            return "Full name is required";
        }
        if(request.getPhone() == null || request.getPhone().isEmpty()){
            return "Phone is required";
        }
        if(!Validate.isValidPhone(request.getPhone())){
            return "Phone is invalid";
        }
        if (!Validate.isValidFullName(request.getFullName())) {
            return "Full name is invalid";
        }
        return null;
    }

    private String validateCaseAddress(UserProfileRequestDTO request){
        if(request.getAddress() == null || request.getAddress().isEmpty()){
            return "Address is required";
        }
        return null;
    }

}
