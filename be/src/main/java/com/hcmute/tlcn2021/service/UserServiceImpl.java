package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.config.jwt.JwtUtils;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.exception.UserNotFoundException;
import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.LoginRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.JwtResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.UserDetailsResponse;
import com.hcmute.tlcn2021.repository.RoleRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse signUp(SignupRequest signUpRequest) {

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Optional<ERole> requestedERole = Optional.ofNullable(signUpRequest.getRole());
        Optional<Role> role = roleRepository.findByName(requestedERole.orElse(ERole.ROLE_ADVISER));
        role.ifPresent(value -> user.setRoles(Collections.singleton(value)));

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    @Override
    public User updateUser(UserUpdateRequest userUpdateRequest) {
        return null;
    }

    @Override
    public UserDetailsResponse findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " can not be found!"));
        UserDetailsResponse result = modelMapper
                .map(foundUser, UserDetailsResponse.class);
        result.setRoleNames(foundUser.getRoles().stream().
                map(role -> role.getName().toString()).collect(Collectors.toSet()));

        return result;
    }
}
