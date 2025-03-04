package com.oflix.OFlix_back.movie.entity;

import com.oflix.OFlix_back.schedule.entity.MovieSchedule;
import com.oflix.OFlix_back.category.entity.Category;
import com.oflix.OFlix_back.image.entity.Image;
import com.oflix.OFlix_back.movie.dto.ResponseMovieDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "actors")
    private String actors;

    @Column(name = "synopsis", length = 1000)
    private String synopsis;

    @Column(name = "runTime")
    private String runTime; // 러닝타임

    @Enumerated(EnumType.STRING)
    @Column(name = "nation", nullable = false)
    private Nation nation;

    @Enumerated(EnumType.STRING)
    @Column(name = "viewAge", nullable = false)
    private ViewAge viewAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre1", nullable = false)
    private Genre genre1; //장르1

    @Enumerated(EnumType.STRING)
    @Column(name="genre2")
    private Genre genre2; //장르2


    @Enumerated(EnumType.STRING)
    @Column(name = "movieStatus", nullable = false)
    private MovieStatus movieStatus;

    //연관관계 설정
    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieSchedule> movieSchedules = new ArrayList<>();

    public ResponseMovieDto toResponseMovieDto() {
        return ResponseMovieDto.builder()
                .movieId(movieId)
                .title(title)
                .releaseDate(releaseDate)
                .director(director)
                .actors(actors)
                .synopsis(synopsis)
                .nation(nation)
                .viewAge(viewAge)
                .genre1(genre1)
                .genre2(genre2)
                .movieStatus(movieStatus)
                .build();
    }
    public String addImage(Image image) {
        if(this.images==null){
            this.images = new ArrayList<>();
        }
        images.add(image);
        image.setMovie(this);
        return images.toString();
    }
}
