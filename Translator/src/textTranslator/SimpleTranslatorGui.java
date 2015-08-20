package textTranslator;
import java.awt.*;
import javax.swing.*;

public class SimpleTranslatorGui extends JFrame {

    public static void main(String[] args) {
        new SimpleTranslatorGui().run();
    }

    private void run() {
        createGui();
    }

    private void createGui() {
        setLayout(new BorderLayout());
        
        JMenuBar jMenuBar1 = new JMenuBar();
        setJMenuBar(jMenuBar1);
        
        JMenu jMenu1 = new JMenu("File");
        jMenuBar1.add(jMenu1);
        
        JMenuItem jMenuItem1 = new JMenuItem("Load...");
        jMenu1.add(jMenuItem1);
        JMenuItem jMenuItem2 = new JMenuItem("Save As...");
        jMenu1.add(jMenuItem2);
        
        JPanel mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(2, 1));
        
        JScrollPane inputText = new JScrollPane(new JTextArea(10, 40));
        mainPanel.add(inputText);
        
        JScrollPane outputText = new JScrollPane(new JTextArea(10, 40));
        mainPanel.add(outputText);
        
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new FlowLayout());
        
        JButton translateButton = new JButton("Translate");
        buttonPanel.add(translateButton);
        
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(quitButton);
        
        pack();
        setVisible(true);
    }

}
