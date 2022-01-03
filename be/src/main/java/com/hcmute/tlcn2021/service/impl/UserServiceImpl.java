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
                .orElseThrow(() -> new UteForumException("Vai trò với id = " + signUpRequest.getRoleId() + "không tồn tại.", HttpStatus.NOT_FOUND)));

        if (signUpRequest.getFacultyId() != 0) {
            user.setFaculty(facultyRepository.findByIdAndIsDeletedFalse(signUpRequest.getFacultyId())
                    .orElseThrow(() -> new UteForumException("Khoa với id = '" + signUpRequest.getFacultyId() + "' không tồn tại", HttpStatus.NOT_FOUND)));
        } else {
            user.setFaculty(null);
        }

        user.setFullName(signUpRequest.getFullName());

        User savedUser = userRepository.save(user);

        // send email
        executorService.execute(() -> {
            String messageBuilder = "Xin chào " + savedUser.getFullName() + "\n" +
                    "Tài khoản của bạn đã được tạo thành công!\n" +
                    "Tài khoản: " + savedUser.getUsername() + "\n" +
                    "Mật khẩu: " + password + "\n" +
                    "Xin mời đăng nhập tại " + loginPath + " và đổi mật khẩu" + "\n";
            emailService.sendSimpleMessage(savedUser.getEmail(), "Tạo tài khoản thành công", messageBuilder);
        });

        return new MessageResponse(String.format("User %s registered successfully!",
                savedUser.getUsername()));
    }

    @Override
    public SingleUserDetailsResponse update(UserUpdateRequest userUpdateRequest) {
        Long id = userUpdateRequest.getId();

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UteForumException("Người dùng với id = " + id + " không tồn tại", HttpStatus.NOT_FOUND));
        foundUser.setRole(roleRepository.findById(userUpdateRequest.getRoleId())
                .orElseThrow(() -> new UteForumException(String.format("Không tìm thấy vai trò với id = '%d'.", userUpdateRequest.getRoleId()), HttpStatus.NOT_FOUND)));
        foundUser.setEmail(userUpdateRequest.getEmail());

        if (userUpdateRequest.getFacultyId() != 0) {
            foundUser.setFaculty(facultyRepository.findById(userUpdateRequest.getFacultyId())
                    .orElseThrow(() -> new UteForumException("Khoa với id = '" + userUpdateRequest.getFacultyId() + "' không tồn tại", HttpStatus.NOT_FOUND)));
        } else {
            foundUser.setFaculty(null);
        }
        foundUser.setFullName(userUpdateRequest.getFullName());

        return convertSingleUser(userRepository.save(foundUser));
    }

    @Override
    public SingleUserDetailsResponse findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() ->
                new UteForumException("Người dùng với id = " + id + " không tồn tại!", HttpStatus.NOT_FOUND));
        return convertSingleUser(foundUser);
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = userRepository.softDeleteUser(id);
        if (affectedRows == 0)
            throw new UteForumException("Có lỗi xảy ra khi xóa người dùng với id = " + id, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new UteForumException("Mật khẩu hiện tại không đúng", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UteForumException("Không tìm thấy người dùng", HttpStatus.INTERNAL_SERVER_ERROR));
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("Password updated successfully");

    }

    @Secured({"ROLE_DEAN"})
    @Override
    public PaginationResponse findByDean(Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (userDetails.getFaculty() == null) {
            throw new UteForumException("Bạn không thuộc bất kỳ khoa nào", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long roleDeanId = roleRepository.findByName(ERole.ROLE_ADVISER)
                .orElseThrow(() -> new UteForumException("Không tìm thấy id của ROLE_ADVISER", HttpStatus.INTERNAL_SERVER_ERROR))
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
                .orElseThrow(() -> new UteForumException("Không tìm thấy người dùng", HttpStatus.NOT_FOUND));
        toBeUpdatedUser.setUsername(request.getUsername());
        toBeUpdatedUser.setEmail(request.getEmail());
        toBeUpdatedUser.setFullName(request.getFullName());

        toBeUpdatedUser.setTopics(request.getTopicIdList().stream()
                .map(id -> topicRepository.findById(id).orElseThrow(() -> new UteForumException("Không tìm thấy chủ đề có id = " + id, HttpStatus.NOT_FOUND)))
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
            throw new UteForumException("Bạn không thuộc bất kỳ khoa nào", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String password = generatePassword();

        User toBeSavedUser = new User();
        toBeSavedUser.setUsername(request.getUsername());
        toBeSavedUser.setEmail(request.getEmail());
        toBeSavedUser.setFullName(request.getFullName());
        toBeSavedUser.setPassword(encoder.encode(password));
        toBeSavedUser.setRole(roleRepository.findByName(ERole.ROLE_ADVISER)
                .orElseThrow(() -> new UteForumException("Không tìm được id của ROLE_ADVISER", HttpStatus.INTERNAL_SERVER_ERROR)));
        toBeSavedUser.setTopics(request.getTopicIdList().stream()
                .map(id -> topicRepository.findById(id).orElseThrow(() -> new UteForumException("Không tìm thấy chủ đề có id = " + id, HttpStatus.NOT_FOUND)))
                .collect(Collectors.toList()));
        toBeSavedUser.setFaculty(userDetails.getFaculty());
        User saved = userRepository.save(toBeSavedUser);
        log.info("User created successfully, username = " + saved.getUsername());

        // send email
        executorService.execute(() -> {
            String messageBuilder = "Xin chào " + saved.getFullName() + "\n" +
                    "Tài khoản của bạn đã được tạo thành công!\n" +
                    "Tài khoản: " + request.getUsername() + "\n" +
                    "Mật khẩu: " + password + "\n" +
                    "Xin mời đăng nhập tại " + loginPath + " và đổi mật khẩu" + "\n";
            emailService.sendSimpleMessage(saved.getEmail(), "Tạo tài khoản thành công", messageBuilder);
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
