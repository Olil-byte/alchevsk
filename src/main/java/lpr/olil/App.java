package lpr.olil;

import lpr.olil.calculator.*;
import lpr.olil.model.*;
import lpr.olil.user.User;
import lpr.olil.view.MainFrame;

import javax.swing.*;

public class App {
    // main frame
    private static JFrame frame;

    private static void createAndShowGUI() {
        frame = new MainFrame("Алчевск: металлургический калькулятор");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300, 300);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
