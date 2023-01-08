package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author", "publisher", "publicDate", "library_id", "vendor"})
})
@Entity
public class Core extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Builder.Default
    private String author = "";
    @Column(nullable = false)
    @Builder.Default
    private String publisher = "";
    @Column(nullable = false)
    @Builder.Default
    private LocalDate publicDate = LocalDate.of(0, 1, 1);
    private String coverUrl;
    @Column(nullable = false)
    @Builder.Default
    private String vendor = "";
    private String category;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    protected Core() {}

}