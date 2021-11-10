package com.dataart.javaschool.services;

import com.dataart.javaschool.models.Article;
import com.dataart.javaschool.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void addArticle(MultipartFile file, Long category) throws Exception {
        try(ZipInputStream zin = new ZipInputStream(file.getInputStream())) {
            if (category == 0) {
                throw new RuntimeException("Не выбрана категория");
            }
            ZipEntry entry = zin.getNextEntry();
            if (!entry.getName().endsWith(".txt")) {
                throw new RuntimeException("Файл не .txt");
            }
            Scanner fileText = new Scanner(zin);
            String name = fileText.nextLine();
            StringBuilder text = new StringBuilder();
            if (!fileText.hasNext()) {
                throw new RuntimeException("Только одна строка в файле");
            }
            while (fileText.hasNext()) {
                text.append(fileText.nextLine() + " ");
            }
            Article article = new Article(name, text.toString(), category);
            System.out.println(text.toString());
            if (zin.getNextEntry() != null) {
                throw new RuntimeException("Есть другие файлы в архиве");
            }
            articleRepository.save(article);
        }
    }
}
