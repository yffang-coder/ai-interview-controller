package com.ruoyi.ai.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class MenuItem {
    private String value;
    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuItem> children;

    public MenuItem() {}
    public MenuItem(String value, String text, List<MenuItem> children) {
        this.value = value;
        this.text = text;
        this.children = children;
    }

    // Getters 和 Setters
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<MenuItem> getChildren() { return children; }
    public void setChildren(List<MenuItem> children) { this.children = children; }
}
