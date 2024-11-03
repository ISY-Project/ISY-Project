package src.GUI;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import src.GameEngine.Engine;

public class AlgCheckbox extends JCheckBox {
    private Engine engine;

    public AlgCheckbox(Engine engine, String name) {
        super(name);
        this.engine = engine;
        this.setSelected(false);

        addActionListener(AlgorithmToggleListener(engine));
    }

    public AlgCheckbox(Engine engine) {
        super("Algorithm");
        this.engine = engine;
        this.setSelected(false);

        addActionListener(AlgorithmToggleListener(engine));
    }

    private void updateAlgorithmStatus(Engine engine) {
        if (isSelected()) {
            engine.setAlgorithmOn(true);
        } else {
            engine.setAlgorithmOn(false);
        }
    }

    private ActionListener AlgorithmToggleListener(Engine engine) {
        return actionListener -> {
            if (engine == null) {
                return;
            }
            updateAlgorithmStatus(engine);
        };
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        updateAlgorithmStatus(engine);
    }
}
