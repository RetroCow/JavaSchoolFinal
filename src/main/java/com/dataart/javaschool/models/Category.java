package com.dataart.javaschool.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;

@EqualsAndHashCode
@Builder
@Entity
@Table("Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    Long id;
    String name;
}
