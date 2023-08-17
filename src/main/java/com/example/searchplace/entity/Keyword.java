package com.example.searchplace.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KEYWORD_NAME")
    private String keywordName;

    private Integer count;

    @Column(name = "CRT_TM", updatable = false)
    @CreatedDate
    private LocalDateTime crtTm;

    @Column(name = "CHG_TM")
    @LastModifiedDate
    private LocalDateTime chgTm;

    @Builder
    public Keyword(Long id, String keywordName, Integer count, LocalDateTime crtTm, LocalDateTime chgTm) {
        this.id = id;
        this.keywordName = keywordName;
        this.count = count;
        this.crtTm = crtTm;
        this.chgTm = chgTm;
    }
}
