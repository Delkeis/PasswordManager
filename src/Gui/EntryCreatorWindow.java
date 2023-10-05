package Gui;

import Controllers.EncryptionController;
import Controllers.FilesController;
import Controllers.JsonController;

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
    private FilesController filesController;
    private JsonController jsonController;
    private EncryptionController encryptionController;

    public JButton getCancelButton(){
        return this.cancelButton;
    }
    public JButton getValidateButton(){ return this.validateButton; }
    public JPanel getPanel1(){
        return this.panel1;
    }

    public EntryCreatorWindow(JsonController jsonController, FilesController filesController, EncryptionController encryptionController){
        this.jsonController = jsonController;
        this.filesController = filesController;
        this.encryptionController = encryptionController;

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

            this.jsonController.addDatasInJsonBuffer(this.nameField.getText().toString(),
                    this.webSiteField.getText().toString(),
                    this.userNameField.getText().toString(),
                    this.encryptionController.encrypt(new String(this.passwordField.getPassword())));

            this.filesController.writeInFile(this.jsonController.getStringFromJsonObject());
        }
    }
}
