package com.example.north_defector.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.north_defector.domain.types.BoardTypes;
import com.example.north_defector.domain.types.converter.BoardTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "board")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Integer boardNo;

    @Column(name = "author_no")
    private Integer authorNo;

    @ManyToOne
    @JoinColumn(name = "author_no", insertable = false, updatable = false)
    private User author;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content;

//    @Column(name = "school")
//    private String school;
//
//    @Column(name = "is_for_school")
//    private Boolean isForSchool;
//
//    @Column(name = "board_type", columnDefinition = "ENUM")
//    @Convert(converter = BoardTypeConverter.class)
//    private BoardTypes boardTypes;
//
//    @Column(name = "board_type", columnDefinition = "ENUM", insertable = false, updatable = false)
//    private String boardTypeCode;

    @Column(name = "comment_disabled")
    private Boolean commDisabled;

    @Column(name = "thumbnail", length = 1024)
    private String thumbnail;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}
