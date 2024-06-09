package com.example.news.controller;

import com.example.news.entity.Category;
import com.example.news.entity.Comment;
import com.example.news.entity.News;
import com.example.news.service.CategoryService;
import com.example.news.service.CommentService;
import com.example.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                        @RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<News> newsList;
        if (keyword != null && !keyword.isEmpty()) {
            newsList = newsService.searchNews(keyword);
        } else if (categoryId != null) {
            newsList = newsService.getNewsByCategory(categoryId);
        } else {
            newsList = newsService.getAllNews();
        }

        // 处理空 newsList 的情况
        if (newsList == null) {
            newsList = new ArrayList<>(); // 或者其他默认值
        }

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("newsList", newsList);
        model.addAttribute("categoryList", categoryList);
        return "index";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable("id") Long id, Model model) {
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        model.addAttribute("newComment", new Comment());
        return "news";
    }

    @PostMapping("/news/{id}/comments")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute Comment comment) {
        News news = newsService.getNewsById(id);
        comment.setNews(news);
        commentService.createComment(comment);
        return "redirect:/news/" + id;
    }
}