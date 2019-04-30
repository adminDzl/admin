package com.wolves.dto.right;

import java.io.Serializable;

/**
 * @author xulu
 */
public class RightDTO implements Serializable {

    private Integer id;
    private String resourceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
