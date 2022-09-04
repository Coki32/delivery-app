package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectBox<T> extends JPanel implements ActionListener {

    private final List<Consumer<T>> onItemSelected = new ArrayList<>();
    private final JComboBox<String> cbox;
    private final List<T> options;

    public SelectBox(String label, List<T> options, Function<T, String> extractor) {
        this.setLayout(new BorderLayout(5, 5));
        this.options = options;
        var lbl = new JLabel(label);

        var choices = options.stream().map(extractor).toList().toArray(new String[]{});
        cbox = new JComboBox<>(choices);
        cbox.addActionListener(this);

        this.add(lbl, BorderLayout.LINE_START);
        this.add(cbox, BorderLayout.CENTER);
    }

    public void addItemSelectedListener(Consumer<T> listener) {
        this.onItemSelected.add(listener);
    }

    public T getCurrentItem() {
        return this.options.get(this.cbox.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cbox) {
            T selected = this.options.get(cbox.getSelectedIndex());
            onItemSelected.forEach(c -> {
                c.accept(selected);
            });
        }
    }

}
