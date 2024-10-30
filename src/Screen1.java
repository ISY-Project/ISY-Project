import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen1 extends JPanel {
    public Screen1(MainFrameOld frame) {
        setBackground(Color.BLUE);
        add(new JLabel("This is Screen 1"));

        // Add button to switch to Screen 2 and resize window
        JButton switchButton = new JButton("Go to Screen 2");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resize the frame before switching screens
                frame.setSize(600, 400);
                frame.showScreen("Screen2");
            }
        });
        add(switchButton);
    }
}

class Screen2 extends JPanel {
    public Screen2(MainFrameOld frame) {
        setBackground(Color.GREEN);
        add(new JLabel("This is Screen 2"));

        // Optional: Button to change the size again or return to Screen1
        JButton backButton = new JButton("Back to Screen 1");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(400, 300);  // Resize back if desired
                frame.showScreen("Screen1");
            }
        });
        add(backButton);
    }
}

class MainFrameOld extends JFrame {
    private Screen1 screen1;
    private Screen2 screen2;

    public MainFrameOld() {
        screen1 = new Screen1(this);
        screen2 = new Screen2(this);

        setLayout(new CardLayout());
        add(screen1, "Screen1");
        add(screen2, "Screen2");

        showScreen("Screen1");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showScreen(String screenName) {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrameOld());
    }
}
