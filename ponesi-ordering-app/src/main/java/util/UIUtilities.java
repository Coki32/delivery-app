package util;

import javax.swing.*;

public class UIUtilities {

    public static void msg(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
