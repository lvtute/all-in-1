package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.config.jwt.JwtUtils;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.exception.CustomedRoleNotFoundException;
import com.hcmute.tlcn2021.exception.FacultyNotFoundException;
import com.hcmute.tlcn2021.exception.UserDeleteFailedException;
import com.hcmute.tlcn2021.exception.UserNotFoundException;
import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.LoginRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.JwtResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.UserDetailsResponse;
import com.hcmute.tlcn2021.payload.response.UsersPaginationResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.RoleRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

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
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        user.setRoles(Collections.singleton(getRoleFromDb(signUpRequest.getRole())));
        Faculty faculty = facultyRepository.findByName(signUpRequest.getFaculty())
                .orElseThrow(() -> new FacultyNotFoundException(
                        "Faculty with name '" + signUpRequest.getFaculty() +
                                "' does not exist"
                ));
        user.setFaculty(faculty);

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    @Override
    public UserDetailsResponse update(UserUpdateRequest userUpdateRequest) {
        Long id = userUpdateRequest.getId();

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " can not be found!"));
        foundUser.setRoles(new HashSet<>(Collections.singletonList(getRoleFromDb(userUpdateRequest.getRole()))));
        foundUser.setEmail(userUpdateRequest.getEmail());
        foundUser.setFaculty(facultyRepository.findByName(userUpdateRequest.getFaculty())
                .orElseThrow(() ->
                        new FacultyNotFoundException("Faculty with name '"
                                + userUpdateRequest.getFaculty() + "' does not exits")));

        userRepository.save(foundUser);
        return convert(userRepository.save(foundUser));
    }

    @Override
    public UserDetailsResponse findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + id + " can not be found!"));
        return convert(foundUser);
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

    private UserDetailsResponse convert(User user) {
        UserDetailsResponse result = modelMapper
                .map(user, UserDetailsResponse.class);

        result.setRoleNames(user.getRoles().stream().
                map(role -> role.getName().toString()).collect(Collectors.toSet()));

        return result;
    }

    private Role getRoleFromDb(ERole eRole) {

        Optional<Role> role = roleRepository.findByName(eRole);
        return role.orElseThrow(() -> new CustomedRoleNotFoundException(
                "Can not find role '" + eRole.toString() + "' in the database"
        ));
    }

    private UsersPaginationResponse convert(Page<User> userPage) {
        UsersPaginationResponse result = modelMapper.map(userPage, UsersPaginationResponse.class);
        result.setContent(userPage.getContent().stream()
                .map(this::convert).collect(Collectors.toList()));
        return result;
    }
}
