package com.arduinoSerialJ;

import javax.swing.*;
import java.awt.*;

public class AlertBox {

    private JFrame alertWindow;

    AlertBox(final Dimension dimension, final String title, final String message) {
        alertWindow = new JFrame();
        alertWindow.setTitle(title);
        alertWindow.setSize(dimension);
        alertWindow.setPreferredSize(dimension);
        alertWindow.setLayout(new BorderLayout());
        alertWindow.setLocationRelativeTo(null);

        final JLabel lblMessage = new JLabel(message, SwingConstants.CENTER);

        alertWindow.add(initButton(), BorderLayout.SOUTH);
        alertWindow.add(lblMessage, BorderLayout.CENTER);
    }

    public void display() {
        alertWindow.setVisible(true);
    }

    private JButton initButton() {
        final JButton btnOk = new JButton("Ok");

        btnOk.addActionListener(e -> {
            alertWindow.setVisible(false);
            alertWindow.dispose();
        });
        return btnOk;
    }

}
