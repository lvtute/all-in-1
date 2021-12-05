package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.config.jwt.JwtUtils;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.exception.CustomedRoleNotFoundException;
import com.hcmute.tlcn2021.exception.FacultyNotFoundException;
import com.hcmute.tlcn2021.exception.UserDeleteFailedException;
import com.hcmute.tlcn2021.exception.UserNotFoundException;
import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.LoginRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.*;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.RoleRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import com.hcmute.tlcn2021.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;
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

    @Autowired
    private FacultyRepository facultyRepository;

//    @PostConstruct
//    private void postConstruct() {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//    }

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String role = "";

        Optional<?> optionalRoleString = userDetails.getAuthorities().stream().findAny();
        if (optionalRoleString.isPresent()) {
            role = optionalRoleString.get().toString();
        }

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }

    @Override
    public MessageResponse signUp(SignupRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(generatePassword()));

        user.setRole(roleRepository.findById(signUpRequest.getRoleId())
                .orElseThrow(() -> new CustomedRoleNotFoundException("Role with id = " + signUpRequest.getRoleId() + "does not exist")));

        if (signUpRequest.getFacultyId() != 0) {
            user.setFaculty(facultyRepository.findById(signUpRequest.getFacultyId())
                    .orElseThrow(() -> new FacultyNotFoundException(
                            "Faculty with id = '" + signUpRequest.getFacultyId() +
                                    "' does not exist"
                    )));
        } else {
            user.setFaculty(null);
        }

        user.setFullName(signUpRequest.getFullName());

        User savedUser = userRepository.save(user);

        return new MessageResponse(String.format("User %s registered successfully!",
                savedUser.getUsername()));
    }

    @Override
    public SingleUserDetailsResponse update(UserUpdateRequest userUpdateRequest) {
        Long id = userUpdateRequest.getId();

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " can not be found!"));
        foundUser.setRole(roleRepository.findById(userUpdateRequest.getRoleId())
                .orElseThrow(() -> new CustomedRoleNotFoundException(String
                        .format("Can not find role with id = '%d'.", userUpdateRequest.getRoleId()))));
        foundUser.setEmail(userUpdateRequest.getEmail());

        if (userUpdateRequest.getFacultyId() != 0) {
            foundUser.setFaculty(facultyRepository.findById(userUpdateRequest.getFacultyId())
                    .orElseThrow(() -> new FacultyNotFoundException(
                            "Faculty with id = '" + userUpdateRequest.getFacultyId() +
                                    "' does not exist")));
        } else {
            foundUser.setFaculty(null);
        }
        foundUser.setFullName(userUpdateRequest.getFullName());

        userRepository.save(foundUser);
        return convertSingleUser(userRepository.save(foundUser));
    }

    @Override
    public SingleUserDetailsResponse findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " can not be found!"));
        return convertSingleUser(foundUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = userRepository.softDeleteUser(id);
        if (affectedRows == 0)
            throw new UserDeleteFailedException("There is error(s) trying to delete user with id = " + id);
    }

    @Override
    public UsersPaginationResponse getPage(Pageable pageable) {
        return convert(userRepository.findAllByIsDeletedFalse(pageable));
    }

    private SingleUserDetailsResponse convertSingleUser(User user) {
        return modelMapper
                .map(user, SingleUserDetailsResponse.class);
    }

    private UserInListResponse convertUserInList(User user) {
        return modelMapper.map(user, UserInListResponse.class);
    }

    private UsersPaginationResponse convert(Page<User> userPage) {
        UsersPaginationResponse result = modelMapper.map(userPage, UsersPaginationResponse.class);
        result.setContent(userPage.getContent().stream()
                .map(this::convertUserInList).collect(Collectors.toList()));
        return result;
    }

    // this method will generate a random password
    private String generatePassword() {
        return "1";
    }
}
