package lpr.olil.view;

import lpr.olil.user.UserDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CoolingTab extends JTabbedPane implements ChangeListener {

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

        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        onTabChanged();
    }

    private void onTabChanged() {
        int selectedIndex = getSelectedIndex();

        if (selectedIndex == 1) {
            if (UserDb.INSTANCE == null) {
                SwingUtilities.invokeLater(() -> {
                    LoginDialog loginDialog = new LoginDialog(null);
                    loginDialog.setVisible(true);

                    if (UserDb.INSTANCE == null) {
                        setSelectedIndex(0);
                    } else {
                        historyTab.refresh();
                    }
                });
            }
        }
    }
}
