package lpr.olil.view;

import javax.swing.*;

public class CoolingParametersForm extends JScrollPane implements ValidatableForm {

    private final JPanel content;

    private final BoxLayout layout;

    private final CcmForm ccmForm;

    private final SlabForm slabForm;

    private final WallForm wallForm;

    private final DuctForm ductForm;

    private final WaterFlowForm waterFlowForm;

    public CoolingParametersForm() {
        content = new JPanel();

        layout = new BoxLayout(content, BoxLayout.Y_AXIS);

        content.setLayout(layout);

        ccmForm = new CcmForm();
        content.add(ccmForm);

        slabForm = new SlabForm();
        content.add(slabForm);

        wallForm = new WallForm();
        content.add(wallForm);

        ductForm = new DuctForm();
        content.add(ductForm);

        waterFlowForm = new WaterFlowForm();
        content.add(waterFlowForm);

        setViewportView(content);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public SlabForm getSlabForm() {
        return slabForm;
    }

    public WallForm getWallForm() {
        return wallForm;
    }

    public DuctForm getDuctForm() {
        return ductForm;
    }

    public CcmForm getCcmForm() {
        return ccmForm;
    }

    public WaterFlowForm getWaterFlowForm() {
        return waterFlowForm;
    }

    @Override
    public void validateForm() {
        slabForm.validateForm();
        wallForm.validateForm();
        ductForm.validateForm();
        ccmForm.validateForm();
    }

    @Override
    public boolean isValidForm() {
        return slabForm.isValidForm() && wallForm.isValidForm() && ductForm.isValidForm() && ccmForm.isValidForm();
    }
}
