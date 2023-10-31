package com.example.north_defector.domain.types;

import lombok.Getter;

@Getter
public enum BoardTypes {
    ASSISTANCE("assist", "0"), KNOW_HOW("know_how", "1");

    private String type;
    private String boardType;

    BoardTypes(String type, String boardType) {
        this.type = type;
        this.boardType = boardType;
    }

    public static BoardTypes getType(String type){
        if(type == null){
            return null;
        }
        for (BoardTypes bt : BoardTypes.values()) {
            if(type.equals(bt.getType())){
                return bt;
            }
        }
        return null;
    }

    public static BoardTypes getTypeCode(String type){
        if(type == null){
            return null;
        }
        for (BoardTypes bt : BoardTypes.values()) {
            if(type.equals(bt.getBoardType())){
                return bt;
            }
        }
        return null;
    }
}
