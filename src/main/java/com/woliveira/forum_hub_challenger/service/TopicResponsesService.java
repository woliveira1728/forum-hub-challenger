package com.woliveira.forum_hub_challenger.service;

import com.woliveira.forum_hub_challenger.dtos.TopicResponsesDto;
import com.woliveira.forum_hub_challenger.model.TopicPost;
import com.woliveira.forum_hub_challenger.model.TopicResponses;
import com.woliveira.forum_hub_challenger.model.User;
import com.woliveira.forum_hub_challenger.repository.TopicResponsesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TopicResponsesService {

    @Autowired
    private TopicResponsesRepository topicResponsesRepository;

    @Autowired
    private TopicPostService topicPostService;

    @Autowired
    private UserService userService;

    public User findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Transactional
    public void saveResponse(
            UUID topicId, TopicResponsesDto topicResponsesDto, User author,
            LocalDateTime createdAt
    ) {

        TopicPost topicPost = topicPostService.findById(topicId);
        if (topicPost.isStatus()) {
            TopicResponses topicResponses = topicResponsesDto.toEntity(author, topicPost, createdAt);
            topicResponsesRepository.save(topicResponses);
        } else {
            throw new IllegalArgumentException("Inactive topic");
        }
    }
}
