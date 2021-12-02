package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> findById(Long id) {
        return topicRepository.findAllByFaculty_Id(id);
    }
}
