package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;  // 알림 내용
    private String recipient;  // 알림을 받을 사용자
    private LocalDateTime timestamp = LocalDateTime.now();  // 알림 시간

    public Notification(String message, String author) {
        this.message = message;
        this.recipient = author;
    }
}
