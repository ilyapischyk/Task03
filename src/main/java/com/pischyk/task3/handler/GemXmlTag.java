package com.pischyk.task3.handler;

public enum GemXmlTag {
    GEMS("gems"),
    PRECIOUS("precious"),
    SEMIPRECIOUS("semiprecious"),
    NAME("name"),
    ORIGIN("origin"),
    COLOR("color"),
    TRANSPARENCY("transparency"),
    CUT("cut"),
    VALUE("value"),
    DATE_OF_PROCESSING("date-of-processing"),
    VISUAL_PARAMETERS("visual-parameters");
    private String title;

    GemXmlTag(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;

    }
}
