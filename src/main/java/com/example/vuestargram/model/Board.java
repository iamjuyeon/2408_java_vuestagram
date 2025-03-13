package com.example.vuestargram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@EnableJpaAuditing // created_at updated_at 를 자동으로 적용
@EntityListeners(AuditingEntityListener.class)
@Table(name = "boards")
@SQLDelete(sql = "UPDATE boards SET updated_at = NOW(), deleted_at = NOW() WHERE board_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId; //Long = BIGINT

    @ManyToOne
    @JoinColumn(name = "user_id") // board테이블의 user_id
    private User user;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "likes", nullable = false, length = 11)
    private int likes;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
