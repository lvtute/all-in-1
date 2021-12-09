package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.ChangePasswordRequest;
import com.hcmute.tlcn2021.payload.request.LoginRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.JwtResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.SingleUserDetailsResponse;
import com.hcmute.tlcn2021.payload.response.UsersPaginationResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    JwtResponse signIn(LoginRequest loginRequest);

    MessageResponse signUp(SignupRequest signupRequest);

    SingleUserDetailsResponse update(UserUpdateRequest userUpdateRequest);

    SingleUserDetailsResponse findById(Long id);

    void deleteById(Long id);

    UsersPaginationResponse getPage(Pageable pageable);

    void changePassword(ChangePasswordRequest changePasswordRequest);
}
