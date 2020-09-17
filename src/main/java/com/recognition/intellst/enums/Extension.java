package com.recognition.intellst.enums;

public enum Extension {
    WIN(".dll");

    private String label;

    Extension(String lib) {
        this.label = lib;
    }

    public String getLabel() {
        return label;
    }
}
