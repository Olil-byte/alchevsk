package lpr.olil.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);

        CoolingParametersForm parameters = new CoolingParametersForm();

        JScrollPane calculator = new CoolingCalculationPanel(parameters);

        JSplitPane splitPanel = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                parameters,
                calculator
        );

        add(splitPanel);
    }
}
