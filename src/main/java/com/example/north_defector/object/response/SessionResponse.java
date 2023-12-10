package com.example.north_defector.object.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.north_defector.object.OriginObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SessionResponse extends OriginObject {

    private Integer userNo;
    private String name;
    private String email;
//    private UserStatusTypes userStatusType;
//    private String userStatusTypeCode;

    private String sessionKey;
    private String profile;

//    public void setUserStatusType(UserStatusTypes userStatusType) {
//        this.userStatusType = userStatusType;
//        this.userStatusTypeCode = userStatusType.getUserStatus();
//    }

}
