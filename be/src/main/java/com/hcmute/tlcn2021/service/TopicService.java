package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> findById(Long id);
}
