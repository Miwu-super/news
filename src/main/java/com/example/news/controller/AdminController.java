package com.example.news.controller;

import com.example.news.entity.Category;
import com.example.news.entity.News;
import com.example.news.service.CategoryService;
import com.example.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String adminIndex(Model model) {
        return "redirect:/admin/news";
    }

    @GetMapping("/news")
    public String newsList(Model model) {
        List<News> newsList = newsService.getAllNews();
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("newsList", newsList);
        model.addAttribute("categoryList", categoryList);
        return "admin/news-list";
    }

    @GetMapping("/news/add")
    public String addNewsForm(Model model) {
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("news", new News());
        model.addAttribute("categoryList", categoryList);
        return "admin/add-news";
    }

    @PostMapping("/news")
    public String addNews(@ModelAttribute News news) {
        newsService.createNews(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/news/{id}/edit")
    public String editNewsForm(@PathVariable("id") Long id, Model model) {
        News news = newsService.getNewsById(id);
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("news", news);
        model.addAttribute("categoryList", categoryList);
        return "admin/edit-news";
    }

    @PostMapping("/news/{id}")
    public String updateNews(@PathVariable("id") Long id, @ModelAttribute News news) {
        news.setId(id); // 设置 ID 以进行更新
        newsService.updateNews(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/news/{id}/delete")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "redirect:/admin/news";
    }
}