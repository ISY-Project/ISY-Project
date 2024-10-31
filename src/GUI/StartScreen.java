package GUI;

import javax.swing.JPanel;


public class StartScreen extends JPanel {
    private final WelcomePannel WelcomePannel;

    public StartScreen(MainFrame mainFrame) {
        this.WelcomePannel = new WelcomePannel(mainFrame);

        // Add components to the frame
        add(this.WelcomePannel);

        setVisible(true);
    }
}
