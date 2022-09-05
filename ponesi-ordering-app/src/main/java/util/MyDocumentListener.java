package util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface MyDocumentListener extends DocumentListener {

    void onChange(DocumentEvent ev);

    @Override
    default void insertUpdate(DocumentEvent documentEvent) {
        onChange(documentEvent);
    }

    @Override
    default void removeUpdate(DocumentEvent documentEvent) {
        onChange(documentEvent);
    }

    @Override
    default void changedUpdate(DocumentEvent documentEvent) {
        onChange(documentEvent);
    }
}
