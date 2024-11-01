package src.GUI;

import javax.swing.JPanel;


public class StartScreen extends JPanel {
    private final WelcomePanel WelcomePannel;

    public StartScreen(MainFrame mainFrame) {
        this.WelcomePannel = new WelcomePanel(mainFrame);

        // Add components to the frame
        add(this.WelcomePannel);

        setVisible(true);
    }
}
