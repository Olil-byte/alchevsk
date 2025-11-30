package lpr.olil.view;

import javax.swing.*;

public class CoolingTab extends JTabbedPane {

    private final JSplitPane calculationTab;

    private final CoolingHistoryPanel historyTab;

    public CoolingTab() {
        super(JTabbedPane.TOP);

        historyTab = new CoolingHistoryPanel();

        CoolingParametersForm parameters = new CoolingParametersForm();
        CoolingCalculationPanel calculator = new CoolingCalculationPanel(parameters, historyTab);

        calculationTab = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                parameters,
                calculator
        );

        addTab("Расчёт", calculationTab);
        addTab("История", historyTab);
    }
}
