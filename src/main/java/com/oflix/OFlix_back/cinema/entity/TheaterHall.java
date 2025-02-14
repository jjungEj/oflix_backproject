package com.oflix.OFlix_back.cinema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 순환 참조 방지: TheaterHall에서 Cinema를 참조하는 역참조
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @JsonBackReference  // 자식에서 부모로의 참조
    private Cinema cinema;

    @Column(length = 50)
    private String name;
}