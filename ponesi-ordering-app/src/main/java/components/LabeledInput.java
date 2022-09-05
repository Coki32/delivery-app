package components;

import util.MyDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LabeledInput extends JPanel {

    private Consumer<String> onTextChanged;
    private JTextField input;

    public LabeledInput(String label, Consumer<String> onTextChanged) {
        super(new BorderLayout(5, 5));
        this.onTextChanged = onTextChanged;

        var lbl = new JLabel(label);
        input = new JTextField();
        input.getDocument().addDocumentListener((MyDocumentListener) ev -> {
            onTextChanged.accept(input.getText());
        });
        this.add(lbl, BorderLayout.PAGE_START);
        this.add(input, BorderLayout.CENTER);
    }

    public String getText() {
        return input.getText();
    }
}
