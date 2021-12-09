package com.hcmute.tlcn2021.service;


import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.request.CreateTopicRequest;
import com.hcmute.tlcn2021.payload.request.TopicUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.TopicResponse;

import java.util.List;

public interface TopicService {
    List<Topic> findAll();
    TopicResponse findById(Long id);

    MessageResponse createTopic(CreateTopicRequest createTopicRequest);
    TopicResponse update(TopicUpdateRequest topicUpdateRequest);
    void deleteById(Long id);
    List<Topic> findByFacultyId(Long id);
}
