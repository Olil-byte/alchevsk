package lpr.olil.view;

import javax.swing.*;

public class TemperatureParametersForm extends JScrollPane {
    private final JPanel content;

    private final BoxLayout layout;

    private final SteelForm steelForm;

    public TemperatureParametersForm() {
        content = new JPanel();

        layout = new BoxLayout(content, BoxLayout.Y_AXIS);

        content.setLayout(layout);

        steelForm = new SteelForm();
        content.add(steelForm);

        setViewportView(content);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public SteelForm getSteelForm() {
        return steelForm;
    }

}
