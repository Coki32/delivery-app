package components;

import controllers.OrderController;
import data.CreditCardRepository;
import entities.CashPayment;
import entities.CreditCard;
import entities.CreditCardPayment;
import entities.Payment;
import util.UIUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CurrentOrderPanel extends JPanel {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final OrderController oc;

    public CurrentOrderPanel(OrderController oc) {
        this(oc, null);
    }


    /**
     * @param oc              Order controller used by the main form.
     * @param orderIdOverride Override the current order, testing purposes only.
     */
    public CurrentOrderPanel(OrderController oc, Integer orderIdOverride) {
        this.oc = oc;
        this.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new LineBorder(Color.BLACK), "Current order")));
        var content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
//        this.setLayout(new GridLayout(0, 1));
        if (orderIdOverride != null) {
            oc.setCurrentOrder(orderIdOverride);
        }
        if (oc.getCurrentOrder() == null) {
            content.add(new JLabel("<html>No order<br />Start by adding items</html>"));
            return;
        }
        var order = oc.getCurrentOrder();
        var orderView = new OrderView(order, false);
        orderView.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(orderView);

        var sendBtn = new JButton("Send order");
        var cancelBtn = new JButton("Cancel order");
        sendBtn.addActionListener(this::sendOrder);
        cancelBtn.addActionListener(this::cancelOrder);
        sendBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        cancelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(sendBtn);
        content.add(cancelBtn);

        this.add(content);

    }

    private void cancelOrder(ActionEvent actionEvent) {

    }

    //This whole part should probably be a few methods, but eeeeh
    private void sendOrder(ActionEvent actionEvent) {
        executor.execute(() -> {
            Object[] options = {"Cash", "Credit card"};
            var paymentMethod = JOptionPane.showInputDialog(this, "Choose payment type", "Payment", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            Payment payment = null;

            if (options[0].equals(paymentMethod)) {//Cash
                var userHas = JOptionPane.showInputDialog(this, formatPriceMessage());
                if (userHas == null) {
                    JOptionPane.showMessageDialog(this, "Ok then, don't order it...");
                    return;
                }
                double parsed = Double.parseDouble(userHas);
                payment = new CashPayment(0, parsed);

            } else if (options[1].equals(paymentMethod)) {//credit card
                try {
                    var cards = CreditCardRepository.getInstance().getUserCards(oc.getCurrentOrder().getUser());
                    if (cards == null || cards.size() == 0) {
                        throw new RuntimeException("You have no cards.");
                    }
                    var cardOptions = cards.stream().map(CreditCard::toString).toArray();
                    var chosenCard = JOptionPane.showInputDialog(this, "Choose your card", "Credit card selection", JOptionPane.QUESTION_MESSAGE, null, cardOptions, cardOptions[0]);
                    if (chosenCard == null) {
                        UIUtilities.msg("Ok then, don't order it...", "okay");
                        return;
                    }
                    var whichCard = List.of(cardOptions).indexOf(chosenCard);
                    payment = new CreditCardPayment(0, cards.get(whichCard));
                } catch (SQLException | RuntimeException e) {
                    UIUtilities.msg("Error fetching user cards.\nMessage:" + e.getMessage() + "\nTry the cash option.", "Error");
                    return;
                }
            }
            String address = JOptionPane.showInputDialog(this, "Enter delivery address, leave empty to use default address");
            if (address == null) {//Cancel clicked
                return;
            }
            address = "".equals(address) ? null : address;//set null if it was empty
            try {
                if (oc.addPaymentMethod(payment) && oc.sendOrder(address)) {
                    oc.finishOrder();
                }
            } catch (SQLException e) {
                UIUtilities.msg("Error sending your order to the restaurant", e.getMessage());
            }
        });
    }

    private String formatPriceMessage() {
        var sb = new StringBuilder("Your total is ");
        try {
            var total = oc.calculateCost();
            sb.append(String.format("%.2f", total)).append("KM.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            sb.append("[unknown].");
        }
        sb.append(" How much do you have on hand? (Enter 12.40 for 12.40KM, just the number, without currency)");
        return sb.toString();
    }

}
