package src.GUI;

import javax.swing.JCheckBox;

import src.GameEngine.Engine;

public class AlgCheckbox extends JCheckBox {
    private Engine engine;

    public AlgCheckbox(Engine engine, String name) {
        super("Algorithm " + name);
        this.engine = engine;
        this.setSelected(false);

        addActionListener(actionListener -> {
            if (isSelected()) {
                engine.setAlgorithmOn(true);
            } else {
                engine.setAlgorithmOn(false);
            }
        });
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public AlgCheckbox(Engine engine) {
        super("Algorithm");
        this.engine = engine;
        this.setSelected(false);

        addActionListener(actionListener -> {
            if (isSelected()) {
                engine.setAlgorithmOn(true);
            } else {
                engine.setAlgorithmOn(false);
            }
        });
    }
}
