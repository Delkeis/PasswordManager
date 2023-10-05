package Gui;

import Serices.EncryptionService;
import Serices.FilesService;
import Serices.JsonService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryCreatorWindow extends JFrame{
    private JTextField nameField;
    private JPanel panel1;
    private JTextField userNameField;
    private JTextField webSiteField;
    private JPasswordField passwordField;
    private JButton validateButton;
    private JButton cancelButton;

    /// variables techniques personnels
    private FilesService filesService;
    private JsonService jsonService;
    private EncryptionService encryptionService;

    public JButton getCancelButton(){
        return this.cancelButton;
    }
    public JButton getValidateButton(){ return this.validateButton; }
    public JPanel getPanel1(){
        return this.panel1;
    }

    public EntryCreatorWindow(JsonService jsonService, FilesService filesService, EncryptionService encryptionService){
        this.jsonService = jsonService;
        this.filesService = filesService;
        this.encryptionService = encryptionService;

        this.validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry();
            }
        });
    }
    public void addEntry(){
        if (this.nameField != null && this.userNameField != null &&
            this.webSiteField != null && this.passwordField != null){

            this.jsonService.addDatasInJsonBuffer(this.nameField.getText().toString(),
                    this.webSiteField.getText().toString(),
                    this.userNameField.getText().toString(),
                    this.encryptionService.encrypt(new String(this.passwordField.getPassword())));

            this.filesService.writeInFile(this.jsonService.getStringFromJsonObject());
        }
    }
}
