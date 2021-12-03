//package com.hcmute.tlcn2021.user;
//
//import com.hcmute.tlcn2021.model.Topic;
//import com.hcmute.tlcn2021.model.User;
//import com.hcmute.tlcn2021.repository.TopicRepository;
//import com.hcmute.tlcn2021.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
////@DataJpaTest
//@SpringBootTest
//public class UserRepositoryTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TopicRepository topicRepository;
//
//    @Test
//    public void whenFindUserForAssigningQuestion_thenReturnAUser() throws Exception {
//
//        Optional<User> optionalUser = userRepository.findUserForAssigningQuestion(1L);
//        Assert.assertNotNull(optionalUser.get());
//    }
//
//}
