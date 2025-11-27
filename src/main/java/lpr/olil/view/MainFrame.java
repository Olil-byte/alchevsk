package lpr.olil.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final JTabbedPane tabs;

    private final CoolingTab coolingTab;

    private final JSplitPane temperatureTab;

    public MainFrame(String title) {
        super(title);

        tabs = new JTabbedPane(JTabbedPane.TOP);

        coolingTab = new CoolingTab();

        tabs.addTab("Охлаждение", coolingTab);

        TemperatureParametersForm temperatureParameters = new TemperatureParametersForm();

        JScrollPane temperatureCalculator = new TemperatureCalculationPanel(temperatureParameters);

        temperatureTab = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                temperatureParameters,
                temperatureCalculator
        );

        tabs.addTab("Температура", temperatureTab);

        add(tabs);
    }
}
