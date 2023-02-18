import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WorkWindow window = new WorkWindow();
        window.makeWindow();
    }

    public static class WorkWindow implements ActionListener {
        JFrame frame = new JFrame("Text Editor");

        JTextField filenameBox = new JTextField("default.txt");
        JLabel filenameLabel   = new JLabel("Filename:");
        JLabel chars           = new JLabel("Characters: -");
        JLabel errLabel        = new JLabel("ERROR: -");
        JButton save = new JButton("Save");
        JButton open = new JButton("Open");
        JTextArea textBox = new JTextArea(14, 60);
        JScrollPane scroll = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        void makeWindow() {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(500, 300);
            frame.setLayout(null);

            open.setBounds(330, 5, 75, 20);
            save.setBounds(410, 5, 75, 20);
            open.setFocusable(false);
            save.setFocusable(false);
            open.addActionListener(this);
            save.addActionListener(this);

            filenameLabel.setBounds(5, 5, 70, 20);
            filenameBox.setBounds(75, 5, 80, 20);
            chars.setBounds(165, 5, 130, 20);
            errLabel.setBounds(5, 245, 250, 20);
            scroll.setBounds(5, 30, 480, 215);

            frame.add(filenameLabel);
            frame.add(filenameBox);
            frame.add(errLabel);
            frame.add(scroll);
            frame.add(chars);
            frame.add(open);
            frame.add(save);
            frame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            errLabel.setText("ERROR: -");
            if (event.getSource() == save) {
                try {
                    FileWriter saveFile = new FileWriter(filenameBox.getText());
                    saveFile.write(textBox.getText());
                    saveFile.close();
                } catch (IOException e) {
                    errLabel.setText("ERROR: No filename.");
                }
            }
            if (event.getSource() == open) {
                try {
                    textBox.setText("");
                    File openFile = new File(filenameBox.getText());
                    Scanner openScanner = new Scanner(openFile);
                    while (openScanner.hasNextLine()) { textBox.append(openScanner.nextLine()); textBox.append("\n"); }
                } catch (FileNotFoundException e) {
                    errLabel.setText("ERROR: File not found.");
                }
            }

            int charLen = textBox.getText().length();
            chars.setText("Characters: " + (charLen > 9999 ? Math.round(charLen*0.001)+"kB" : charLen+"B"));
        }
    }
}
