package com.example.news.service;

import com.example.news.entity.News;
import com.example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    @Cacheable("news")
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
    }

    @Override
    @CacheEvict(value = "news", allEntries = true)
    public News createNews(News news) {
        news.setPublishDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    @Override
    @CacheEvict(value = "news", allEntries = true)
    public News updateNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    @CacheEvict(value = "news", allEntries = true)
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> searchNews(String keyword) {
        return newsRepository.findByTitleContaining(keyword);
    }

    @Override
    public List<News> getNewsByCategory(Long categoryId) {
        return newsRepository.findByCategoryId(categoryId);
    }
}