package com.example.north_defector.object.response;

import com.example.north_defector.domain.Board;
import com.example.north_defector.domain.Comment;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDetailResponse {
    private Integer numLike;
    private List<Comment> comments;
    private Board board;
}
