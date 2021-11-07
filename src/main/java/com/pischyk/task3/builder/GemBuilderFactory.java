package com.pischyk.task3.builder;

public class GemBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private GemBuilderFactory() {
    }

    public static AbstractGemBuilder createGemBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new GemDomBuilder();
            case STAX:
                return new GemStaxBuilder();
            case SAX:
                return new GemSaxBuilder();
            default:
                throw new EnumConstantNotPresentException(
                        type.getDeclaringClass(), type.name());
        }
    }
}
