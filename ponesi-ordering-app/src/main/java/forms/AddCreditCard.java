package forms;

import components.LabeledInput;
import components.SelectBox;
import data.CountryRepository;
import data.CreditCardRepository;
import data.UserRepository;
import entities.Country;
import entities.CreditCard;
import entities.User;
import util.UIUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AddCreditCard extends JFrame {

    private User user;
    private Country country;

    private String name;
    private String number;
    private String expDate;
    private String cvc;
    private String label;

    public AddCreditCard() {
        super("Add a new credit card");
        var content = new JPanel(new GridLayout(0, 1, 5, 5));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        var userSelect = createUserSelect();
        var countrySelect = createCountrySelect();
        if (userSelect == null || countrySelect == null) {
            closeAndDispose();
            return;
        }

        content.add(userSelect);
        content.add(new LabeledInput("Name on card", u -> this.name = u));
        content.add(new LabeledInput("Card number", u -> this.number = u));
        content.add(new LabeledInput("Expiration date (mm/yy)", u -> this.expDate = u));
        content.add(new LabeledInput("CVC", u -> this.cvc = u));
        content.add(countrySelect);
        content.add(new LabeledInput("Label (optional)", u -> this.label = u));
        var addBtn = new JButton("Add");
        var cancelBtn = new JButton("Cancel");
        addBtn.addActionListener(this::addCard);
        cancelBtn.addActionListener(this::cancel);

        content.add(addBtn);
        content.add(cancelBtn);
        this.add(content);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    private void closeAndDispose() {
        this.setVisible(false);
        this.dispose();
    }

    private JPanel createCountrySelect() {
        try {
            var sbox = new SelectBox<Country>("Select country", CountryRepository.getInstance().findAll(), c -> c.getShorthand() + " - " + c.getFullName());
            sbox.addItemSelectedListener(c -> this.country = c);
            country = sbox.getCurrentItem();
            return sbox;
        } catch (SQLException e) {
            UIUtilities.msg("Could not load countries.\nMessage:" + e.getMessage(), "Error");
            return null;
        }
    }

    private JPanel createUserSelect() {
        try {
            var sbox = new SelectBox<User>("Select card owner", UserRepository.getInstance().findAll(), u -> u.getUsername() + "-" + u.getName());
            sbox.addItemSelectedListener(u -> this.user = u);
            user = sbox.getCurrentItem();
            return sbox;
        } catch (SQLException e) {
            UIUtilities.msg("Could not load users, maybe create a user first?\nMessage:" + e.getMessage(), "Error");
            return null;
        }
    }

    private void cancel(ActionEvent actionEvent) {
        this.dispose();
        this.setVisible(false);
    }

    private void addCard(ActionEvent actionEvent) {
        try {
            if (CreditCardRepository.getInstance().createCard(new CreditCard(0, name, number, expDate, cvc, country, (label == null || label.length() == 0) ? null : label, user))) {
                UIUtilities.msg("Card successfully created", "Success");
                this.closeAndDispose();
            }
        } catch (SQLException e) {
            UIUtilities.msg(e.getMessage(), "Error creating card");
        }
    }
}
