package com.dataart.javaschool.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;

@EqualsAndHashCode
@Builder
@Entity
@Table("Article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    Long id;
    String name;
    String text;
    Long category_id;

    public Article(String name, String text, Long category_id) {
        this.name = name;
        this.text = text;
        this.category_id = category_id;
    }
}
