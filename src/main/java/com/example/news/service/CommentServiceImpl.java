package com.example.news.service;

import com.example.news.entity.Comment;
import com.example.news.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        comment.setPublishDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}