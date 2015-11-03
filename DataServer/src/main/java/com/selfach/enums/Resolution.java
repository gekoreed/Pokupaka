package com.selfach.enums;

/**
 * By gekoreed on 10/2/15.
 */
public enum Resolution {
    ORIGINAL("1920x1080"),
    COMPRESSED("700x400");

    private String value;

    Resolution(String s) {
        this.value = s;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
