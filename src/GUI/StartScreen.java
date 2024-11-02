package src.GUI;

import javax.swing.JPanel;


public class StartScreen extends JPanel {
    private final WelcomePanel WelcomePanel;

    public StartScreen(MainFrame mainFrame) {
        this.WelcomePanel = new WelcomePanel(mainFrame);

        // Add components to the frame
        add(this.WelcomePanel);

        setVisible(true);
    }
}
