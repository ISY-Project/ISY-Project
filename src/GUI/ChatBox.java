package src.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatBox extends JPanel {
    private final JTextArea chatView;
    private final JTextField chatArea;

    public ChatBox() {
        super(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Chatbox"));
        // this.setSize(500, 350);
        this.chatView = new JTextArea();
        this.chatView.setSize(new Dimension(250, 150));
        this.chatView.setRows(9);
        this.chatArea = new JTextField();
        this.chatArea.setPreferredSize(new Dimension(250, 20));

        chatView.setEditable(false);
        chatView.setColumns(45);
        chatView.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(chatView);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatArea.setEditable(true);
        chatArea.setColumns(45);

        chatArea.addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = chatArea.getText();
            if (!msg.isEmpty()) {
                addMessage("You", msg);
                // Main.getTelnetClient().sendMessage(msg); // TODO: create a special text field for sending commands.
            }
        });

        this.add(scroll, BorderLayout.NORTH);
        this.add(chatArea,  BorderLayout.SOUTH);
    }

    public void addMessage(String message) {
        String currentText = chatView.getText();
        if (!currentText.isEmpty()) {
            chatView.setText(currentText + "\n" + message);
        } else {
            chatView.setText(message);
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

    public void clearChat() {
        chatView.setText("");
    }

}
