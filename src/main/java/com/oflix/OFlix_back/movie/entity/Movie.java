package com.oflix.OFlix_back.movie.entity;

import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.image.entity.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long movieId;

    @Column(name = "title",length = 50, nullable = false)
    private String title;

    @Column(name = "release_Date")
    private String releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "actors")
    private String actors;

    @Column(name = "synopsis")
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "category_Id ")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "nation", nullable = false)
    private Nation nation;

    @Enumerated(EnumType.STRING)
    @Column(name = "viewAge", nullable = false)
    private ViewAge viewAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    //연관관계 설정
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<Image> images = new ArrayList<>();

}
/*
movie_id 영화 id Long
title 영화제목 string
releaseDate 영화 개봉일 string
runtime 영화 러닝 타임 Datetime
director 감독 string
actors 출연진  string
synopsis 줄거리  string

many to one
category_id Long

one to many
image_id    Long
movie_schedules

eunm
nation
viewAge
genre
 */