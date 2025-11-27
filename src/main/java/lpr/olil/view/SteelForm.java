package lpr.olil.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SteelForm extends JPanel {

    private final GroupLayout layout;

    private final NumberField hydrogenField;
    private final NumberField nitrogenField;
    private final NumberField oxygenField;
    private final NumberField carbonField;
    private final NumberField phosphorField;
    private final NumberField sulfurField;
    private final NumberField arsenicField;
    private final NumberField leadField;
    private final NumberField siliconField;
    private final NumberField manganeseField;
    private final NumberField copperField;
    private final NumberField nickelField;
    private final NumberField molybdenumField;
    private final NumberField vanadiumField;
    private final NumberField chromiumField;
    private final NumberField aluminumField;
    private final NumberField tungstenField;

    public SteelForm() {

        layout = new GroupLayout(this);
        setLayout(layout);

        hydrogenField = new NumberField("Водород (%)");
        nitrogenField = new NumberField("Азот (%)");
        oxygenField = new NumberField("Кислород (%)");
        carbonField = new NumberField("Карбон (%)");
        phosphorField = new NumberField("Фосфор (%)");
        sulfurField = new NumberField("Сера (%)");
        arsenicField = new NumberField("Мышьяк (%)");
        leadField = new NumberField("Олово (%)");
        siliconField = new NumberField("Кремний (%)");
        manganeseField = new NumberField("Марганец (%)");
        copperField = new NumberField("Медь (%)");
        nickelField = new NumberField("Никель (%)");
        molybdenumField = new NumberField("Молибдений (%)");
        vanadiumField = new NumberField("Ванадий (%)");
        chromiumField = new NumberField("Хром (%)");
        aluminumField = new NumberField("Алюминий (%)");
        tungstenField = new NumberField("Вольфрам (%)");

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(hydrogenField)
                        .addComponent(nitrogenField)
                        .addComponent(oxygenField)
                        .addComponent(carbonField)
                        .addComponent(phosphorField)
                        .addComponent(sulfurField)
                        .addComponent(arsenicField)
                        .addComponent(leadField)
                        .addComponent(siliconField)
                        .addComponent(manganeseField)
                        .addComponent(copperField)
                        .addComponent(nickelField)
                        .addComponent(molybdenumField)
                        .addComponent(vanadiumField)
                        .addComponent(chromiumField)
                        .addComponent(aluminumField)
                        .addComponent(tungstenField)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(hydrogenField)
                        .addComponent(nitrogenField)
                        .addComponent(oxygenField)
                        .addComponent(carbonField)
                        .addComponent(phosphorField)
                        .addComponent(sulfurField)
                        .addComponent(arsenicField)
                        .addComponent(leadField)
                        .addComponent(siliconField)
                        .addComponent(manganeseField)
                        .addComponent(copperField)
                        .addComponent(nickelField)
                        .addComponent(molybdenumField)
                        .addComponent(vanadiumField)
                        .addComponent(chromiumField)
                        .addComponent(aluminumField)
                        .addComponent(tungstenField)
        );

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Сталь"
        ));
    }



    public double getHydrogenValue() {
        if (hydrogenField.hasValidValue()) {
            return hydrogenField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getNitrogenValue() {
        if (nitrogenField.hasValidValue()) {
            return nitrogenField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getOxygenValue() {
        if (oxygenField.hasValidValue()) {
            return oxygenField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getCarbonValue() {
        if (carbonField.hasValidValue()) {
            return carbonField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getPhosphorValue() {
        if (phosphorField.hasValidValue()) {
            return phosphorField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getSulfurValue() {
        if (sulfurField.hasValidValue()) {
            return sulfurField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getArsenicValue() {
        if (arsenicField.hasValidValue()) {
            return arsenicField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getLeadValue() {
        if (leadField.hasValidValue()) {
            return leadField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getSiliconValue() {
        if (siliconField.hasValidValue()) {
            return siliconField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getManganeseValue() {
        if (manganeseField.hasValidValue()) {
            return manganeseField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getCopperValue() {
        if (copperField.hasValidValue()) {
            return copperField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getNickelValue() {
        if (nickelField.hasValidValue()) {
            return nickelField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getMolybdenumValue() {
        if (molybdenumField.hasValidValue()) {
            return molybdenumField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getVanadiumValue() {
        if (vanadiumField.hasValidValue()) {
            return vanadiumField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getChromiumValue() {
        if (chromiumField.hasValidValue()) {
            return chromiumField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getAluminumValue() {
        if (aluminumField.hasValidValue()) {
            return aluminumField.getValue();
        } else {
            return 0.0;
        }
    }

    public double getTungstenValue() {
        if (tungstenField.hasValidValue()) {
            return tungstenField.getValue();
        } else {
            return 0.0;
        }
    }
}
