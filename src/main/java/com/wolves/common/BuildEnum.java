package com.wolves.common;

/**
 * Created by gf on 2019/4/27.
 */
public enum BuildEnum {

    south("南栋"),
    north("北栋"),
    ;

    private String name;

    public String getName() {
        return name;
    }

    BuildEnum(String name){
        this.name = name;
    }
}
