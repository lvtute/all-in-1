package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.LoginRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.JwtResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.UserDetailsResponse;

public interface UserService {

    JwtResponse signIn(LoginRequest loginRequest);

    MessageResponse signUp(SignupRequest signupRequest);

    UserDetailsResponse updateUser(UserUpdateRequest userUpdateRequest);

    UserDetailsResponse findById(Long id);
}
