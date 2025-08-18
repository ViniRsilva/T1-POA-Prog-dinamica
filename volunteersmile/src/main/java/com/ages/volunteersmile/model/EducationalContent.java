package com.ages.volunteersmile.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "educational_content")
public class EducationalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(nullable = false)
    private String url;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public enum ContentType {
        VIDEO,
        TIP
    }
}
