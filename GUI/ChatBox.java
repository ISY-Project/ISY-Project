import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatBox extends JPanel {
    private static final BorderLayout LAYOUT = new BorderLayout();
    private JTextArea chatView;
    private JTextField chatArea;

    public ChatBox() {
        super(LAYOUT);
        this.setBorder(BorderFactory.createTitledBorder("Chatbox"));
        this.chatView = new JTextArea();
        this.chatArea = new JTextField();

        chatView.setEditable(false);
        chatView.setColumns(45);
        chatArea.setEditable(true);
        chatArea.setColumns(45);

        chatArea.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                addMessage("You", chatArea.getText());
            }
        });

        this.add(chatView, BorderLayout.WEST);
        this.add(chatArea,  BorderLayout.EAST);
    }

    public void addMessage(String message) {
        String currentText = chatView.getText();
        if (currentText.isEmpty()) {
            chatView.setText(message);
        } else {
            chatView.setText(currentText + "\n" + message);
        }
        chatArea.setText("");
    }

    public void addMessage(String name, String message) {
        addMessage(name + ": " + message);
    }

    public JTextField getChatArea() {
        return chatArea;
    }

    public JTextArea getChatView() {
        return chatView;
    }

}
