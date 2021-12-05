package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.exception.TopicDeleteFailedException;
import com.hcmute.tlcn2021.exception.TopicNotFoundException;
import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.request.TopicUpdateRequest;
import com.hcmute.tlcn2021.payload.response.TopicResponse;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    // hien thi theo id
    private TopicResponse convertSingleTopic(Topic topic) {
        return modelMapper
                .map(topic, TopicResponse.class);
    }

    @Override
    public TopicResponse findById(Long id) {

        Topic foundUser = topicRepository.findById(id).orElseThrow(() ->
                new TopicNotFoundException("Topic with id = " + id + " can not be found!"));
        return convertSingleTopic(foundUser);
    }
    // cap nhat


    @Override
    public TopicResponse update(TopicUpdateRequest topicUpdateRequest) {
        Long id = topicUpdateRequest.getId();

        Topic foundUser = topicRepository.findById(id).orElseThrow(() ->
                new TopicNotFoundException("Faculty with id = " + id + " can not be found!"));

        foundUser.setName(topicUpdateRequest.getName());
        topicRepository.save(foundUser);
        return convertSingleTopic(topicRepository.save(foundUser));
    }

    // xoa voi id
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = topicRepository.softDeleteTopic(id);
        if (affectedRows == 0)
            throw new TopicDeleteFailedException("There is error(s) trying to delete topic with id = " + id);
    }

    @Override
    public List<Topic> findByFacultyId(Long id) {
        return topicRepository.findAllByFaculty_Id(id);
    }
}
