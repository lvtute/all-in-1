package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.config.jwt.JwtUtils;
import com.hcmute.tlcn2021.config.service.AuthenticationFacade;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.exception.*;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.*;
import com.hcmute.tlcn2021.payload.response.*;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.RoleRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import com.hcmute.tlcn2021.service.EmailService;
import com.hcmute.tlcn2021.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.text.RandomStringGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@Log4j2
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

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RandomStringGenerator passwordGenerator;

    @Autowired
    private EmailService emailService;

    @Value("${forum.app.password.length}")
    private int passwordLength;

    @Value("${fe.path.login}")
    private String loginPath;

    @Autowired
    private ExecutorService executorService;

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
                role,
                userDetails.getFaculty());
    }

    @Override
    public MessageResponse signUp(SignupRequest signUpRequest) {
        String password = generatePassword();
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(password));

        user.setRole(roleRepository.findById(signUpRequest.getRoleId())
                .orElseThrow(() -> new UteForumException("Vai tr?? v???i id = " + signUpRequest.getRoleId() + "kh??ng t???n t???i.", HttpStatus.BAD_REQUEST)));

        if (signUpRequest.getFacultyId() != 0) {
            user.setFaculty(facultyRepository.findByIdAndIsDeletedFalse(signUpRequest.getFacultyId())
                    .orElseThrow(() -> new UteForumException("Khoa v???i id = '" + signUpRequest.getFacultyId() + "' kh??ng t???n t???i", HttpStatus.BAD_REQUEST)));
        } else {
            user.setFaculty(null);
        }

        user.setFullName(signUpRequest.getFullName());

        User savedUser = userRepository.save(user);

        // send email
        executorService.execute(() -> {
            String messageBuilder = "Xin ch??o " + savedUser.getFullName() + "\n" +
                    "T??i kho???n c???a b???n ???? ???????c t???o th??nh c??ng!\n" +
                    "T??i kho???n: " + savedUser.getUsername() + "\n" +
                    "M???t kh???u: " + password + "\n" +
                    "Xin m???i ????ng nh???p t???i " + loginPath + " v?? ?????i m???t kh???u" + "\n";
            emailService.sendSimpleMessage(savedUser.getEmail(), "T???o t??i kho???n th??nh c??ng", messageBuilder);
        });

        return new MessageResponse(String.format("User %s registered successfully!",
                savedUser.getUsername()));
    }

    @Override
    public SingleUserDetailsResponse update(UserUpdateRequest userUpdateRequest) {
        Long id = userUpdateRequest.getId();

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UteForumException("Ng?????i d??ng v???i id = " + id + " kh??ng t???n t???i", HttpStatus.NOT_FOUND));
        foundUser.setRole(roleRepository.findById(userUpdateRequest.getRoleId())
                .orElseThrow(() -> new UteForumException(String.format("Kh??ng t??m th???y vai tr?? v???i id = '%d'.", userUpdateRequest.getRoleId()), HttpStatus.NOT_FOUND)));
        foundUser.setEmail(userUpdateRequest.getEmail());

        if (userUpdateRequest.getFacultyId() != 0) {
            foundUser.setFaculty(facultyRepository.findById(userUpdateRequest.getFacultyId())
                    .orElseThrow(() -> new UteForumException("Khoa v???i id = '" + userUpdateRequest.getFacultyId() + "' kh??ng t???n t???i", HttpStatus.NOT_FOUND)));
        } else {
            foundUser.setFaculty(null);
        }
        foundUser.setFullName(userUpdateRequest.getFullName());

        return convertSingleUser(userRepository.save(foundUser));
    }

    @Override
    public SingleUserDetailsResponse findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UteForumException("Ng?????i d??ng v???i id = " + id + " kh??ng t???n t???i!", HttpStatus.NOT_FOUND));
        return convertSingleUser(foundUser);
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = userRepository.softDeleteUser(id);
        if (affectedRows == 0)
            throw new UteForumException("C?? l???i x???y ra khi x??a ng?????i d??ng v???i id = " + id, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public UsersPaginationResponse getPage(Pageable pageable) {
        return convert(userRepository.findAllByIsDeletedFalse(pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADMIN", "ROLE_ADVISER"})
    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (!encoder.matches(changePasswordRequest.getOldPassword(), userDetails.getPassword())) {
            throw new UteForumException("M???t kh???u hi???n t???i kh??ng ????ng", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UteForumException("Kh??ng t??m th???y ng?????i d??ng", HttpStatus.INTERNAL_SERVER_ERROR));
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("Password updated successfully");

    }

    @Secured({"ROLE_DEAN"})
    @Override
    public PaginationResponse findByDean(Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (userDetails.getFaculty() == null) {
            throw new UteForumException("B???n kh??ng thu???c b???t k??? khoa n??o", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long roleDeanId = roleRepository.findByName(ERole.ROLE_ADVISER)
                .orElseThrow(() -> new UteForumException("Kh??ng t??m th???y id c???a ROLE_ADVISER", HttpStatus.INTERNAL_SERVER_ERROR))
                .getId();
        Page<User> pageUserFromDb =
                userRepository.findAllByIsDeletedFalseAndFaculty_IdEqualsAndRole_IdEquals(userDetails.getFaculty().getId(), roleDeanId, pageable);

        PaginationResponse result = modelMapper.map(pageUserFromDb, PaginationResponse.class);
        result.setContent(pageUserFromDb.getContent().stream()
                .map(u -> modelMapper.map(u, SingleUserDetailsResponse.class))
                .collect(Collectors.toList()));

        return result;
    }

    @Secured({"ROLE_DEAN"})
    @Transactional
    @Override
    public void updateByDean(UserUpdateByDeanRequest request) {
        User toBeUpdatedUser = userRepository.findById(request.getId())
                .orElseThrow(() -> new UteForumException("Kh??ng t??m th???y ng?????i d??ng", HttpStatus.NOT_FOUND));
        toBeUpdatedUser.setUsername(request.getUsername());
        toBeUpdatedUser.setEmail(request.getEmail());
        toBeUpdatedUser.setFullName(request.getFullName());

        toBeUpdatedUser.setTopics(request.getTopicIdList().stream()
                .map(id -> topicRepository.findById(id).orElseThrow(() -> new UteForumException("Kh??ng t??m th???y ch??? ????? c?? id = " + id, HttpStatus.NOT_FOUND)))
                .collect(Collectors.toList()));
        userRepository.save(toBeUpdatedUser);
        log.info("User updated successfully");

    }

    @Transactional
    @Secured({"ROLE_DEAN"})
    @Override
    public void createByDean(UserCreateByDeanRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (userDetails.getFaculty() == null) {
            throw new UteForumException("B???n kh??ng thu???c b???t k??? khoa n??o", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String password = generatePassword();

        User toBeSavedUser = new User();
        toBeSavedUser.setUsername(request.getUsername());
        toBeSavedUser.setEmail(request.getEmail());
        toBeSavedUser.setFullName(request.getFullName());
        toBeSavedUser.setPassword(encoder.encode(password));
        toBeSavedUser.setRole(roleRepository.findByName(ERole.ROLE_ADVISER)
                .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c id c???a ROLE_ADVISER", HttpStatus.INTERNAL_SERVER_ERROR)));
        toBeSavedUser.setTopics(request.getTopicIdList().stream()
                .map(id -> topicRepository.findById(id).orElseThrow(() -> new UteForumException("Kh??ng t??m th???y ch??? ????? c?? id = " + id, HttpStatus.NOT_FOUND)))
                .collect(Collectors.toList()));
        toBeSavedUser.setFaculty(userDetails.getFaculty());
        User saved = userRepository.save(toBeSavedUser);
        log.info("User created successfully, username = " + saved.getUsername());

        // send email
        executorService.execute(() -> {
            String messageBuilder = "Xin ch??o " + saved.getFullName() + "\n" +
                    "T??i kho???n c???a b???n ???? ???????c t???o th??nh c??ng!\n" +
                    "T??i kho???n: " + request.getUsername() + "\n" +
                    "M???t kh???u: " + password + "\n" +
                    "Xin m???i ????ng nh???p t???i " + loginPath + " v?? ?????i m???t kh???u" + "\n";
            emailService.sendSimpleMessage(saved.getEmail(), "T???o t??i kho???n th??nh c??ng", messageBuilder);
        });
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

        return passwordGenerator.generate(passwordLength);
    }
}
