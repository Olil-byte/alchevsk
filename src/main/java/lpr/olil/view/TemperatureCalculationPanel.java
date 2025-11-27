package lpr.olil.view;

import javax.swing.*;

public class TemperatureCalculationPanel extends JScrollPane {
    private final TemperatureParametersForm parametersForm;

    private final JPanel content;

    public TemperatureCalculationPanel(TemperatureParametersForm parametersForm) {
        this.parametersForm = parametersForm;

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        setViewportView(content);
    }

}
