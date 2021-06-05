package pl.dele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Obiekt o nazwie Gui implementujący proste GUI.
 * @author Daniel Delegacz
 */
public class Gui {

    // stała przechowująca tekst Source
    public static final String SOURCE = "Source";
    // stała przechowująca tekst Destination
    public static final String DESTINATION = "Destination";

    // główny obiekt JFrame
    private JFrame frame;
    // panel z tekstem powitalnym
    private JPanel titlePanel;
    // panel z dwoma obiektami FileChooserPanel
    private JPanel fileChooserPanel;
    // panel z polem tekstowym do wpisania liczby wątków
    private JPanel threadPanel;
    // panel z przyciskiem execute
    private JPanel executePanel;
    // panel z informacją o wykonaniu programu
    private JLabel finishedLabel;

    // ścieżka do katalogu źródłowego
    private String srcDirectoryPath = null;
    // ścieżka do katalogu docelowego
    private String dstDirectoryPath = null;
    // liczba wykorzystywanych wątków, domyślnie 5
    private int threadAmount = 5;

    /**
     * Konstruktor tworzący okno
     */
    public Gui() {
        createWindow();
    }

    /**
     * Główna metoda odpowiedzialna za utworzenie okna progamu
     */
    private void createWindow() {
        frame = new JFrame("Photo-sorting-program");
        frame.setLayout(new FlowLayout());
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        createTitleLabel();
        frame.add(titlePanel);

        // panel pobierający ścieżkę źródłową i docelową
        fileChooserPanel = new JPanel();
        fileChooserPanel.setLayout(new GridLayout(2,3));
        createJFileChooser(SOURCE);
        createJFileChooser(DESTINATION);
        frame.add(fileChooserPanel);

        // panel do ustawiania ilości wątków
        threadPanel = new JPanel();
        createThreadPanel();
        frame.add(threadPanel);

        // panel odpowiedzialny za uruchominie programu
        executePanel = new JPanel();
        createExecutePanel();
        frame.add(executePanel);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Utwórz panel odpowiedzialny za uruchomienie programu
     */
    private void createExecutePanel() {
        executePanel.setLayout(new GridLayout(1,4));
        JButton button = new JButton("Execute");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (srcDirectoryPath == null || dstDirectoryPath == null || threadAmount < 0) return;
                CatalogAnalyzer catalogAnalyzer = new CatalogAnalyzer(srcDirectoryPath, dstDirectoryPath, threadAmount);
                try {
                    catalogAnalyzer.analysis();
                    while (!catalogAnalyzer.isExecutorTerminated());
                    finishedPanel();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        executePanel.add(button);
    }

    /**
     * Utwórz panel z informacją o zakończeniu programu
     */
    private void finishedPanel(){
        if (finishedLabel == null) {
            finishedLabel = new JLabel("Process finished");
            frame.add(finishedLabel);
            finishedLabel.setForeground(Color.GREEN);
        }
        frame.setVisible(true);
    }

    /**
     * Utwórz panel umożliwiający wpisanie liczby wątków
     */
    private void createThreadPanel() {
        JLabel label = new JLabel("Number of threads:");
        JTextField textField = new JTextField(3);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setText("5");

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();
                try {
                    int amount = Integer.parseInt(text);
                    if (amount > 0) {
                        threadAmount = amount;
                    }
                } catch (NumberFormatException ex) {ex.getMessage();}
            }
        });
        threadPanel.add(label);
        threadPanel.add(textField);
    }

    /**
     * Utwórz tytułowy label
     */
    private void createTitleLabel() {
        JLabel titleLabel = new JLabel("A program for sorting photos by the date they were taken ");
        titlePanel.add(titleLabel);
    }

    /**
     * Utwórz JFileChooser
     * @param kind
     */
    private void createJFileChooser(String kind) {

        JLabel kindLabel = new JLabel(kind +" directory: ");
        JButton button = new JButton(kind);
        final JLabel selectedFolderLabel = new JLabel("Directory not selected");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if (kind == SOURCE) srcDirectoryPath = file.getPath();
                    else if (kind == DESTINATION) dstDirectoryPath = file.getPath();
                    selectedFolderLabel.setText(file.getName());
                }
            }
        });
        fileChooserPanel.add(kindLabel, BorderLayout.AFTER_LINE_ENDS);
        fileChooserPanel.add(button);
        fileChooserPanel.add(selectedFolderLabel, BorderLayout.LINE_END);
    }
}
