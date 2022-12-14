package components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.function.Consumer;

public class LockableJPanel<T extends JPanel> extends JPanel {

    private boolean isLocked = true;
    private final T child;
    private JCheckBox cb;
    private Consumer<T> onDisable;
    private Consumer<T> onEnable;

    public LockableJPanel(T child, String label, boolean locked, Consumer<T> onDisable, Consumer<T> onEnable) {
        this.onDisable = onDisable;
        this.onEnable = onEnable;

        this.setLayout(new BorderLayout(5, 5));
        this.setBorder(new TitledBorder(new LineBorder(Color.BLACK), label));

        cb = new JCheckBox("Enabled", !locked);
        cb.addItemListener(itemEvent -> {
            var newIsLocked = itemEvent.getStateChange() == ItemEvent.DESELECTED;
            this.setIsLocked(newIsLocked);
        });
        this.child = child;
        this.add(cb, BorderLayout.PAGE_START);
        this.add(child, BorderLayout.CENTER);
        this.setIsLocked(locked);
    }

    //just in case
    public T getChild() {
        return child;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    //Naming is ridiculous because JPanel already took all the good names
    public void setIsLocked(boolean locked) {
        this.isLocked = locked;
        cb.setSelected(!locked);
        child.setEnabled(!locked);
        for (var c : child.getComponents()) {
            c.setEnabled(!locked);
        }
        if (isLocked)
            onDisable.accept(child);
        else
            onEnable.accept(child);
    }
}
