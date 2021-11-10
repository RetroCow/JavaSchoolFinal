package com.dataart.javaschool.repositories;

import com.dataart.javaschool.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    @Query("SELECT u FROM Article u WHERE u.category_id = :category")
    Page<Article> findArticleByCategory(long category, Pageable pageable);

}
