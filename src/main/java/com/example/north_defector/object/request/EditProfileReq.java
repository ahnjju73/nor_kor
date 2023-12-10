package com.example.north_defector.object.request;


import com.example.north_defector.object.OriginObject;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EditProfileReq extends OriginObject {
    private String profile;
}
