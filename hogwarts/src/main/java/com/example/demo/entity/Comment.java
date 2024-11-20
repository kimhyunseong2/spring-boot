package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;



    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Transient  // 데이터베이스에 저장되지 않음, 객체에만 존재
    private List<Reply> replies;




}

