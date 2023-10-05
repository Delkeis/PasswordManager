package Gui;

import Controllers.EncryptionController;
import Controllers.FilesController;
import Controllers.JsonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PasswordManagerWindows {
    private JPanel panel1;
    private JScrollPane entryScrollPane;
    private JPanel infoPanel;
    private JButton addButton;
    private boolean result;

    private JsonController jsonController;
    private EncryptionController encryptionController;
    private FilesController filesController;
    public PasswordManagerWindows(JsonController jsonController, EncryptionController encryptionController, FilesController filesController){
        this.jsonController = jsonController;
        this.encryptionController = encryptionController;
        this.filesController = filesController;

        this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
        this.infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addEntryToScrollPanel("scroll");

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { createInfoPanel(); }
        });
    }

    public void addEntryToScrollPanel(String label) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        for (String[] entry: this.jsonController.getAllEntryFromData()){
            JPanel entrypane = new JPanel();

            entrypane.setLayout(new BoxLayout(entrypane, BoxLayout.Y_AXIS));
            entrypane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            entrypane.add(new JLabel(entry[4]));
            entrypane.add(new JLabel(entry[2]));
            entrypane.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    changeLateralPannel(entry);
                }
            });
            entrypane.setVisible(true);

            container.add(entrypane);
            container.setVisible(true);
        }
        if (label == "refresh")
            refreshPanel(1);
        this.entryScrollPane.getViewport().add(container);
        this.entryScrollPane.setVisible(true);
        if (label == "refresh")
            refreshPanel(2);
    }
    public void addEntryToScrollPanel(){
        addEntryToScrollPanel("refresh");
    }
    private void refreshPanel(int passage){
        if (passage == 1) {
            this.entryScrollPane.getViewport().removeAll();
        } else if (passage == 2) {
            this.entryScrollPane.revalidate();
            this.entryScrollPane.repaint();
        }
    }
    public JPanel getPanel1() {
        return panel1;
    }

    private void changeLateralPannel(String[] content){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton delButton = new JButton("Delete entry");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFrame()){
                    deleteEntry(content);
                    addEntryToScrollPanel();
                }
            }
        });
        JPanel contentPanel = new JPanel(){
            @Override
            public Dimension getPreferredSize(){return new Dimension(600, super.getPreferredSize().height);}
        };

        contentPanel.setLayout(new GridBagLayout());

        contentPanel.add(new JLabel("Site : "+content[4]), gbc);
        contentPanel.add(new JLabel("User Name : "+content[2]), gbc);
        contentPanel.add(new JLabel("Name : "+content[1]), gbc);
        contentPanel.add(new JLabel("Password : "+this.encryptionController.decrypt(content[3])), gbc);
        contentPanel.add(delButton);

        contentPanel.setVisible(true);

        this.infoPanel.removeAll();
        this.infoPanel.add(contentPanel);

        this.infoPanel.revalidate();
        this.infoPanel.repaint();
    }
    private void deleteEntry(String[] content){
        this.jsonController.removeDataFromJsonBuffer(Integer.parseInt(content[0]));
        this.filesController.writeInFile(this.jsonController.getStringFromJsonObject());
    }
    private void createInfoPanel(){
        System.out.println("Appel dé l'écran de création !");
        EntryCreatorWindow newEntryWindow = new EntryCreatorWindow(this.jsonController, this.filesController, this.encryptionController);
        JFrame frame = new JFrame();
        frame.setContentPane(newEntryWindow.getPanel1());
        frame.pack();
        frame.setVisible(true);
        newEntryWindow.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        newEntryWindow.getValidateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEntryWindow.addEntry();
                frame.setVisible(false);
                frame.dispose();
                addEntryToScrollPanel();
            }
        });
    }

    public boolean validateFrame(){
        this.result = false;

        JDialog vFrame = new JDialog();
        vFrame.setModal(true);
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Are you sure ?");
        panel.add(label);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("no");

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sayResult(true);
                vFrame.setVisible(false);
                vFrame.dispose();
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sayResult(false);
                vFrame.setVisible(false);
                vFrame.dispose();
            }
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.setVisible(true);
        panel.add(buttonPanel);
        panel.setVisible(true);

        vFrame.setContentPane(panel);
        vFrame.pack();
        vFrame.setVisible(true);

        return this.result;

    }
    private void sayResult(boolean res){
        this.result = res;
    }
}
