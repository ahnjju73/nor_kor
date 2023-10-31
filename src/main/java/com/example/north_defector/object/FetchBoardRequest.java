package com.example.north_defector.object;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.north_defector.object.request.RequestListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchBoardRequest extends RequestListDto {

    private Integer boardNo;
    private String boardType;
    private String schoolName;
    private Integer userNo;
//    private boolean isForSchool;
//    private String forSchool;
    private String email;

//    public void setIsForSchool(boolean forSchool) {
//        isForSchool = forSchool;
//        this.forSchool = isForSchool ? "1" : "0";
//    }
}
