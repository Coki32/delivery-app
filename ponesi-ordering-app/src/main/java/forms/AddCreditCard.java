package forms;

import components.SelectBox;
import data.UserRepository;
import entities.User;
import util.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AddCreditCard extends JFrame {

    private User user;

    public AddCreditCard() {
        super("Add a new credit card");
        this.setLayout(new GridLayout(0, 1));

        try {
            var sbox = new SelectBox<User>("Select card owner", UserRepository.getInstance().findAll(), u -> u.getUsername() + "-" + u.getName());
            sbox.addItemSelectedListener(u -> this.user = u);
            this.add(sbox);
        } catch (SQLException e) {
            UIUtilities.msg("Could not load users, maybe create a user first?\nMessage:" + e.getMessage(), "Error");
            this.dispose();
            this.setVisible(false);
            return;
        }


        var addBtn = new JButton("Add");
        var cancelBtn = new JButton("Cancel");
        addBtn.addActionListener(this::addCard);
        cancelBtn.addActionListener(this::cancel);

        this.add(addBtn);
        this.add(cancelBtn);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void cancel(ActionEvent actionEvent) {
        this.dispose();
        this.setVisible(false);
    }

    private void addCard(ActionEvent actionEvent) {


    }
}
