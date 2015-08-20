package textTranslator;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/*
 * This is file Translator.java in project AbstractTranslator, created on Oct 3, 2006
 */

public class Translator extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem quitMenuItem;
    private JMenu translateMenu;
    private JPanel descriptionPanel;
    private JTextArea descriptionTextArea;
    private JPanel contentPanel;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JPanel buttonPanel;
    private JButton translateButton;

    private Translator thisGui;
    private TranslatorInterface currentTranslator;

    private int width;
    private int height;

    public Translator(String title) {
        width = 200;
        height = 300;
        createWidgets();
        createGui();
        attachListeners();
        setTitle(title);
        thisGui = this;
        thisGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTranslator(TranslatorInterface t) {
        currentTranslator = t;
        setTitle(currentTranslator.getName());
        descriptionTextArea.setText(currentTranslator.getDescription());
    }

    private void createWidgets() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        loadMenuItem = new JMenuItem("Load...");
        saveAsMenuItem = new JMenuItem("Save As...");
        quitMenuItem = new JMenuItem("Quit");

        translateMenu = new JMenu("Translate");

        descriptionPanel = new JPanel();
        descriptionTextArea = new JTextArea(2, 0);
        contentPanel = new JPanel();
        buttonPanel = new JPanel();
        inputTextArea = new JTextArea();
        outputTextArea = new JTextArea();
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        inputTextArea.setFont(font);
        outputTextArea.setFont(font);
        translateButton = new JButton("Translate");
    }

    private void createGui() {
        createMenus();
        createDescriptionArea();
        createContentArea();
        createButtonArea();
        setSize(width, height);
    }

    private void createMenus() {
        menuBar.add(fileMenu);
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(quitMenuItem);

        setTranslator(new IdentityTranslator());
        addTranslateItem(currentTranslator);
        createTranslateMenu();
        menuBar.add(translateMenu);

        this.setJMenuBar(menuBar);
    }

    private void createTranslateMenu() {
        addTranslateItem(new TextWrapTranslator());
        addTranslateItem(new UnwrapTranslator());
        addTranslateItem(new IndentationFixer());
        addTranslateItem(new ParenthesisChecker());
        addTranslateItem(new ZapGremlinsTranslator());
        addTranslateItem(new TextToHtmlTranslator());
        addTranslateItem(new HtmlToTextTranslator());
        addTranslateItem(new TextToPowerpoint());
        addTranslateItem(new PowerpointToText());
        addTranslateItem(new ShowHex());
        addTranslateItem(new TokenizerTranslator());
        addTranslateItem(new PigLatinTranslator());

        addTranslateItem(new DetabTranslator());
        addTranslateItem(new EntabTranslator());
        addTranslateItem(new UnixNewlinesTranslator());
        addTranslateItem(new WindowsNewlinesTranslator());
        addTranslateItem(new SingleSpaceTranslator());
        addTranslateItem(new DoubleSpaceTranslator());
        addTranslateItem(new UnderscoresToCamelCaseTranslator());
        addTranslateItem(new WrapTextTranslator());
        addTranslateItem(new FlowTextTranslator());
        addTranslateItem(new BalanceChecker());
        addTranslateItem(new NestingChecker());
        addTranslateItem(new UnSmartQuoteTranslator());
        addTranslateItem(new DedentEvenlyTranslator());
    }

    private void addTranslateItem(TranslatorInterface translator) {
        JMenuItem menuItem = new JMenuItem(translator.getName());
        translateMenu.add(menuItem);
        menuItem.addActionListener(new TranslateListener(translator));
    }

    private void createDescriptionArea() {
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.add(descriptionTextArea, BorderLayout.CENTER);
        descriptionTextArea.setBackground(new Color(255, 255, 220));
        descriptionTextArea.setForeground(Color.BLUE);
    }

    private void createContentArea() {
        setLayout(new BorderLayout());
        add(descriptionPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.setLayout(new GridLayout(2, 1));
        contentPanel.add(makeScrollableArea(inputTextArea,
            "Text to be translated"));
        contentPanel.add(makeScrollableArea(outputTextArea,
            "Translated text"));
        inputTextArea.selectAll();
    }

    private JScrollPane makeScrollableArea(JTextArea textArea,
                                           String title) {
        Border lineBorder;
        TitledBorder titledBorder;
        
        // Make a panel containing a text area
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textArea);
        
        // Make a titled scroll pane containing the panel
        JScrollPane scrollPane = new JScrollPane(panel);
        
        // Put the border and title around the scroll pane
        lineBorder = BorderFactory.createLineBorder(Color.black);
        titledBorder = BorderFactory.createTitledBorder(lineBorder, title);
        scrollPane.setBorder(titledBorder);
        scrollPane.setBackground(Color.WHITE);

        return scrollPane;
    }



    private void createButtonArea() {
        buttonPanel.add(translateButton);
    }

    private void attachListeners() {
        loadMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                loadFile();
            }
        });
        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                saveFileAs();
            }
        });
        quitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                thisGui.dispose();
                System.exit(0);
            }
        });
        translateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                translate();
            }
        });
    }

    protected void translate() {
        String input = inputTextArea.getText();
        String output = currentTranslator.translate(input);
        outputTextArea.setText(output);
    }

    protected void loadFile() {
        // Read file as binary so that "Text to Hex" conversion is correct
        byte[] wholeThing = loadBinaryFile();
        inputTextArea.setText(new String(wholeThing));
    }

    /**
     * Loads a file as binary data. If the file is not binary data,
     * no harm is done.
     * 
     * @return The contents of the file.
     */
    public static byte[] loadBinaryFile() {
        File file = BasicFileIO.getInputFile();
        long length = file.length();
        assert length <= Integer.MAX_VALUE;
        byte[] buffer = new byte[(int) length];
        try {
            FileInputStream fin = new FileInputStream(file);
            synchronized (fin) {
                fin.read(buffer);
                fin.close();
            }
        }
        catch (FileNotFoundException e) {
            JOptionPane
                    .showMessageDialog(null,
                                       "Could not find file " + file,
                                       "Missing File",
                                       JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                                          "Error reading file " + file,
                                          "I/O Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        return buffer;
    }
    
    protected void saveFileAs() {
        PrintWriter writer = BasicFileIO.getWriter();
        String text = outputTextArea.getText();
        writer.print(text);
        writer.flush();
    }

    public static void main(String[] args) {
        Translator gui = new Translator("Identity Translator");
        gui.pack();
        gui.setSize(500, 600);
        gui.setVisible(true);
    }

    class TranslateListener implements ActionListener {
        TranslatorInterface translator;

        public TranslateListener(TranslatorInterface translator) {
            this.translator = translator;
        }

        public void actionPerformed(ActionEvent arg0) {
            setTranslator(translator);
            thisGui.setTitle(translator.getName());
            descriptionTextArea.setText(translator.getDescription());
            translate();
        }
    }
}
