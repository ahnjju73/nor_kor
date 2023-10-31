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
public class JoinRequest extends OriginObject {

    private String email;
    private String name;
    private String password;

    public void checkValidation(){
        if(!bePresent(this.email)) throw new BusinessException("email을 입력해주세요", HttpStatus.BAD_REQUEST);
        if(!bePresent(this.name)) throw new BusinessException("이름을 입력해 주세요", HttpStatus.BAD_REQUEST);
        if(!bePresent(this.password)) throw new BusinessException("비밀번호를 입력해 주세요", HttpStatus.BAD_REQUEST);
    }

}
