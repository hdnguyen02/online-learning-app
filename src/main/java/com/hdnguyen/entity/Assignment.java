package com.hdnguyen.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="assignments")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Assignment {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date deadline;

    private String description; // mô tả.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_group")
    private Group group;

    private String url; // liên kết tới file ( firebase )

    @OneToMany(mappedBy = "assignment", fetch = FetchType.EAGER)
    private List<Submit> submits = new ArrayList<>();

}

