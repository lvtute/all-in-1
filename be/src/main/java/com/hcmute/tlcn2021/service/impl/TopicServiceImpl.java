package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.config.service.AuthenticationFacade;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.request.CreateTopicRequest;
import com.hcmute.tlcn2021.payload.request.TopicUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.TopicResponse;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.service.TopicService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    public List<Topic> findAll() {
        return topicRepository.findAllByIsDeletedFalse();
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    public TopicResponse findById(Long id) {

        Topic found = topicRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() ->
                new UteForumException("Không tìm thấy chủ đề", HttpStatus.NOT_FOUND));
        return modelMapper.map(found, TopicResponse.class);
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    public MessageResponse createTopic(CreateTopicRequest createTopicRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        if (userDetails.getFaculty() == null) {
            throw new UteForumException("Bạn phải thuộc 1 khoa thì mới có thể tạo chủ đề mới", HttpStatus.FORBIDDEN);
        }
        Topic topic = new Topic();
        topic.setName(createTopicRequest.getName());
        topic.setFaculty(userDetails.getFaculty());

        Topic savedTopic = topicRepository.save(topic);

        log.info("Topic saved successfully");
        return new MessageResponse(String.format("Chủ để %s được tạo thành công!", savedTopic.getName()));
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    public TopicResponse update(TopicUpdateRequest topicUpdateRequest) {
        Topic found = topicRepository.findByIdAndIsDeletedFalse(topicUpdateRequest.getId()).orElseThrow(() ->
                new UteForumException("Không tìm thấy chủ đề", HttpStatus.NOT_FOUND));

        found.setName(topicUpdateRequest.getName());
        TopicResponse result = modelMapper.map(topicRepository.save(found), TopicResponse.class);

        log.info("Topic updated successfully");
        return result;
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = topicRepository.softDeleteTopic(id);
        if (affectedRows == 0) {
            log.error("Topic deleted failed!");
            throw new UteForumException("Xảy ra lỗi khi xóa chủ đề", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Topic deleted successfully");
    }

    //    @Secured({"ROLE_DEAN"})
    @Override
    public List<Topic> findByFacultyId(Long id) {
        return topicRepository.findAllByFaculty_IdAndIsDeletedFalse(id);
    }
}
