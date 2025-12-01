package lpr.olil.view;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

public class FormulaDisplayHelper {
    private static final int FONT_SIZE = 20;

    public static JLabel createFormulaLabel(String formula, boolean visible) {
        try {
            TeXFormula texFormula = new TeXFormula(formula);
            TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, FONT_SIZE);
            JLabel label = new JLabel(icon);
            label.setVisible(visible);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        } catch (Exception e) {
            JLabel label = new JLabel(formula.replace("\\,", " ").replace("\\", ""));
            label.setVisible(visible);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }
    }

    public static void updateFormulaLabel(JLabel label, String formula) {
        try {
            TeXFormula texFormula = new TeXFormula(formula);
            TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, FONT_SIZE);
            label.setIcon(icon);
            label.setText(null);
            label.setVisible(true);
        } catch (Exception e) {
            label.setIcon(null);
            label.setText(formula.replace("\\,", " ").replace("\\", ""));
            label.setVisible(true);
        }
    }
}