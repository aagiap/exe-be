package com.exebe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_posts")
@Data
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_id", nullable = false)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // Có thể chứa HTML

    @ElementCollection
    private List<String> galleryImages;

    private LocalDateTime createdAt;
}
