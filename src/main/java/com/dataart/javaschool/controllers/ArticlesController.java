package com.dataart.javaschool.controllers;

import com.dataart.javaschool.models.Article;
import com.dataart.javaschool.models.Category;
import com.dataart.javaschool.repositories.ArticleRepository;
import com.dataart.javaschool.repositories.CategoryRepository;
import com.dataart.javaschool.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/")
    public String index(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("url", "/");
        int totalPages = articles.getTotalPages();
        if (totalPages > 0) {
            Iterable<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable(value = "id") long categoryId, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> articles = articleRepository.findArticleByCategory(categoryId, pageable);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        int totalPages = articles.getTotalPages();
        if (totalPages > 0) {
            Iterable<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping("/article")
    public String article(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "article";
    }

    @PostMapping("/article")
    public String addArticle(@RequestParam("file") MultipartFile file, @RequestParam Long category, Model model) {
        String error = "";
        if(!file.isEmpty()) {
            try {
                articleService.addArticle(file, category);
                return "redirect:/";
            } catch (Exception exception) {
                error = exception.getMessage();
            }
        } else {
            error = "Файл не найден";
        }
        model.addAttribute("error", error);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "article";
    }
}
