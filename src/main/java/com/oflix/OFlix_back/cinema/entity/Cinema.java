package com.oflix.OFlix_back.cinema.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String location;

    // 순환 참조 방지: Cinema에서 TheaterHall을 관리하는 참조
    @OneToMany(mappedBy = "cinema")
    @JsonManagedReference  // 부모에서 자식으로의 참조
    private List<TheaterHall> theaterHalls;
}