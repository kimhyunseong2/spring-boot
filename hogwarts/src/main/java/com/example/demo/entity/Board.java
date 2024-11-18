package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    private String username;  // 작성자 (사용자)

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();


    @Column(nullable = false)
    private int hit = 0;

    @Column(nullable = false)
    private int likeCount = 0;


    @ElementCollection
    @CollectionTable(name = "board_likes", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "username")
    private Set<String> likedUsernames = new HashSet<>();

    private String role;

}
