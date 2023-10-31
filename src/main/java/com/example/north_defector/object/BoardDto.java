package com.example.north_defector.object;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDto {

    private Integer boardNo;
    private String title;
    private String content;
//    private Boolean isForSchool;
//    private String boardType;
    private Boolean commentDisabled;

}
