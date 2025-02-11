package com.oflix.OFlix_back.movie.entity;

import com.oflix.OFlix_back.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

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

    public enum Nation {
        korea(0, "한국"),
        USA(1, "미국"),
        others(2, "해외"),
        ;

        Nation(int code, String description) {}

    }


    @Enumerated(EnumType.STRING)
    @Column(name = "viewAge", nullable = false)
    private ViewAge viewAge;

    public enum ViewAge {
        ALL(0, "전체관람가"),
        AGE12(1,"12세관람가"),
        AGE15(2,"15세관람가"),
        AGE19(3,"19세관람가"),
        ;


        ViewAge(int code, String description) {}
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    public enum Genre {
        FANTASY(0, "판타지"),
        ACTION(1, "액션"),
        SF(2, "SF"),
        CRIME(3, "범죄"),
        COMEDY(4, "코미디"),
        DRAMA(5, "드라마"),
        HORROR(6, "공포"),
        THRILLER(7, "스릴러"),
        FAMILY(8, "가족"),
        MYSTERY(9, "미스터리"),
        ROMANCE(10, "로맨스"),
        MUSIC(11, "음악");
        Genre(int code, String description) {}
    }

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