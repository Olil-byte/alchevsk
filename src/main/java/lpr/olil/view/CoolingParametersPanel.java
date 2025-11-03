package lpr.olil.view;

import javax.swing.*;

public class CoolingParametersPanel extends JScrollPane {

    private JPanel content;

    private BoxLayout layout;

    private CcmHelper ccmHelper;

    private SlabHelper slabHelper;

    private WallHelper wallHelper;

    private DuctHelper ductHelper;

    private WaterFlowHelper waterFlowHelper;

    public CoolingParametersPanel() {
        content = new JPanel();

        layout = new BoxLayout(content, BoxLayout.Y_AXIS);

        content.setLayout(layout);

        ccmHelper = new CcmHelper();
        content.add(ccmHelper);

        slabHelper = new SlabHelper();
        content.add(slabHelper);

        wallHelper = new WallHelper();
        content.add(wallHelper);

        ductHelper = new DuctHelper();
        content.add(ductHelper);

        waterFlowHelper = new WaterFlowHelper();
        content.add(waterFlowHelper);

        setViewportView(content);

        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public SlabHelper getSlabHelper() {
        return slabHelper;
    }

    public WallHelper getWallHelper() {
        return wallHelper;
    }

    public DuctHelper getDuctHelper() {
        return ductHelper;
    }

    public CcmHelper getCcmHelper() {
        return ccmHelper;
    }
}
