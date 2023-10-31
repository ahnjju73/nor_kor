package com.example.north_defector.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.north_defector.domain.types.BoardLikePk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "board_like")
@Getter
@Setter
@IdClass(BoardLikePk.class)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardLike {

    @Id
    @Column(name = "board_no")
    private Integer boardNo;

    @Id
    @Column(name = "user_no")
    private Integer userNo;

}
