package com.trigues.entity;


/**
 * Created by Albert on 27/04/2017.
 */

public class Parameter {
    private String type;
    private String value;

    public Parameter(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
