package archiveComparator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TestFrame extends JFrame {
    JFileChooser fileChooser = new JFileChooser();
    File fileOne;
    File fileTwo;

    public TestFrame() {
        super("Archive Comparator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        final JLabel label1 = new JLabel("Выбранный файл zip1");
        label1.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label1);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        JButton buttonOne = new JButton("Открыть zip1");
        buttonOne.setAlignmentX(CENTER_ALIGNMENT);

        final JLabel label2 = new JLabel("Выбранный файл zip2");
        label2.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label2);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));


        JButton buttonTwo = new JButton("Открыть zip2");
        buttonTwo.setAlignmentX(CENTER_ALIGNMENT);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "zip", "zip");
        fileChooser.setFileFilter(filter);

        buttonOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JFileChooser fileopen = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open zip1");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileOne = fileChooser.getSelectedFile();
                    label1.setText(fileOne.getName());
                }
            }
        });
        buttonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JFileChooser fileChooser=new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open zip2");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileTwo = fileChooser.getSelectedFile();
                    label2.setText(fileTwo.getName());
                }
            }
        });

        panel.add(buttonOne);
        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);

        panel.add(buttonTwo);
        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);

        setPreferredSize(new Dimension(300, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        TestFrame testFrame = new TestFrame();

    }

}

