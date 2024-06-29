package com.hdnguyen.entity;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "search_history")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorySearch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email_user")
    private User user;

}
