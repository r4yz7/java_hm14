package com.example.java_lr14.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header;
    @Column(columnDefinition = "TEXT")
    private String context;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp date = new Timestamp(System.currentTimeMillis());
    private String linkImg;
    @PrePersist
    protected void onCreate() {
        date = new Timestamp(new Date().getTime());
    }
}
