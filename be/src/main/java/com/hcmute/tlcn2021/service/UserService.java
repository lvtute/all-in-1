package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.*;
import com.hcmute.tlcn2021.payload.response.*;
import org.springframework.data.domain.Pageable;

public interface UserService {

    JwtResponse signIn(LoginRequest loginRequest);

    MessageResponse signUp(SignupRequest signupRequest);

    SingleUserDetailsResponse update(UserUpdateRequest userUpdateRequest);

    SingleUserDetailsResponse findById(Long id);

    void deleteById(Long id);

    UsersPaginationResponse getPage(Pageable pageable);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    PaginationResponse findByDean(Pageable pageable);

    void updateByDean(UserUpdateByDeanRequest userUpdateByDeanRequest);

    void createByDean(UserCreateByDeanRequest request);
}
