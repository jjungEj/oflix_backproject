package com.oflix.OFlix_back.cinema.dto;

import com.oflix.OFlix_back.cinema.entity.Cinema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCinemaDto {
    private Long id;
    private String name;
    private String location;

}