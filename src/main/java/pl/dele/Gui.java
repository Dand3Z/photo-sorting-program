package pl.dele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui {

    public static void main(String[] args) {
        new Gui();
    }

    public static final String SOURCE = "Source";
    public static final String DESTINATION = "Destination";

    private JFrame frame;
    private JPanel panel;

    private String srcDirectoryPath = null;
    private String dstDirectoryPath = null;

    public Gui() {
        createWindow();
    }

    private void createWindow() {
        frame = new JFrame("Photo-sorting-program");
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(470, 200);
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        createTitleLabel();

        createJFileChooser(SOURCE);
        createJFileChooser(DESTINATION);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createTitleLabel() {
        JLabel titleLabel = new JLabel("A program for sorting photos by the date they were taken ");
        titleLabel.setBounds(0, 10, 100,10);
        panel.add(titleLabel);
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
                    selectedFolderLabel.setText("Folder Selected: " + file.getName());
                }else{
                    selectedFolderLabel.setText("Open command canceled");
                }
            }
        });
        panel.add(kindLabel, BorderLayout.AFTER_LINE_ENDS);
        panel.add(button);
        panel.add(selectedFolderLabel, BorderLayout.LINE_END);
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