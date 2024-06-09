package com.example.news.service;

import com.example.news.entity.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    News getNewsById(Long id);

    News createNews(News news);

    News updateNews(News news);

    void deleteNews(Long id);

    List<News> searchNews(String keyword);

    List<News> getNewsByCategory(Long categoryId);
}