package forms;

import components.LabeledInput;
import data.UserRepository;
import entities.User;
import util.UIUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserCreate extends JFrame {

    private String username;
    private String email;
    private String password;
    private String address;
    private String name;

    public UserCreate() {
        super("Create a new user");
        var content = new JPanel(new GridLayout(0, 1, 5, 5));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        content.add(new LabeledInput("Username", u -> this.username = u));
        content.add(new LabeledInput("E-Mail", u -> this.email = u));
        content.add(new LabeledInput("Password", u -> this.password = u));
        content.add(new LabeledInput("Address", u -> this.address = u));
        content.add(new LabeledInput("Full name", u -> this.name = u));

        var addBtn = new JButton("Add");
        var cancelBtn = new JButton("Cancel");
        addBtn.addActionListener(this::addUser);
        cancelBtn.addActionListener(this::cancel);

        content.add(addBtn);
        content.add(cancelBtn);
        this.add(content);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void cancel(ActionEvent actionEvent) {
        this.dispose();
        this.setVisible(false);
    }

    private void addUser(ActionEvent actionEvent) {
        try {
            if (UserRepository.getInstance().createNew(new User(0, username, email, address, name, password))) {
                UIUtilities.msg("Success, refresh main page (CTRL+R).", "Success");
                this.dispose();
                this.setVisible(false);
            }
        } catch (SQLException e) {
            UIUtilities.msg(e.getMessage(), "Could not save user...");
        }
    }

}
