package com.example.north_defector.domain.types;

import javax.persistence.Column;
import java.io.Serializable;

public class BoardLikePk implements Serializable {

    @Column(name = "board_no")
    private Integer boardNo;

    @Column(name = "user_no")
    private Integer userNo;
}
