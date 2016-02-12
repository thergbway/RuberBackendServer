package com.ruber.util;

import java.beans.PropertyEditorSupport;

public class CaseInsensitiveEnumEditor<T extends Enum<T>> extends PropertyEditorSupport {

    private final Class<T> type;

    public CaseInsensitiveEnumEditor(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setAsText(final String textRepresentation) throws IllegalArgumentException {
        T value = T.valueOf(type, textRepresentation.toUpperCase());
        setValue(value);
    }
}