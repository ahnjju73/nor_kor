package com.example.north_defector.object.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.north_defector.object.OriginObject;
import com.example.north_defector.object.exceptions.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmailRequest extends OriginObject {

    private String email;

    public void checkValidation(){
        if(!bePresent(email)) new BusinessException("이메일을 입렵해주세요", HttpStatus.BAD_REQUEST);
    }

}
