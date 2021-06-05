package pl.dele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class Gui {

    public static void main(String[] args) {
        new Gui();
    }

    public static final String SOURCE = "Source";
    public static final String DESTINATION = "Destination";

    private JFrame frame;
    private JPanel titlePanel;
    private JPanel fileChooserPanel;
    private JPanel threadPanel;
    private JPanel executePanel;

    private String srcDirectoryPath = null;
    private String dstDirectoryPath = null;
    private int threadAmount = 5;

    public Gui() {
        createWindow();
    }

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

        // core panel
        fileChooserPanel = new JPanel();
        fileChooserPanel.setLayout(new GridLayout(2,3));
        createJFileChooser(SOURCE);
        createJFileChooser(DESTINATION);
        frame.add(fileChooserPanel);

        // Thread panel
        threadPanel = new JPanel();
        createThreadPanel();
        frame.add(threadPanel);

        // Execute panel
        executePanel = new JPanel();
        createExecutePanel();
        frame.add(executePanel);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createExecutePanel() {
        executePanel.setLayout(new GridLayout(1,4));
        JButton button = new JButton("Execute");

        executePanel.add(button);
    }

    private void createThreadPanel() {
        JLabel label = new JLabel("Number of threads:");
        JTextField textField = new JTextField(3);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setText("5");

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

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

    private void createTitleLabel() {
        JLabel titleLabel = new JLabel("A program for sorting photos by the date they were taken ");
        //titleLabel.setBounds(0, 10, 100,10);
        titlePanel.add(titleLabel);
    }

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


/*
        frame = new JFrame();



        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));

        title = new JLabel("Label");
        panel.add(title);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Photo-sorting-program");
        frame.pack();
        frame.setSize(400,250);
        frame.setVisible(true);

        // File dialog
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }

*/