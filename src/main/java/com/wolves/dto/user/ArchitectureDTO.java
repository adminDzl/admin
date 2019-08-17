package com.wolves.dto.user;

import java.util.List;

/**
 * 建筑
 * Created by Administrator on 2019/8/17.
 */
public class ArchitectureDTO {

    private String id;

    private String name;

    private List<ArchitectureDTO> childs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArchitectureDTO> getChilds() {
        return childs;
    }

    public void setChilds(List<ArchitectureDTO> childs) {
        this.childs = childs;
    }
}
