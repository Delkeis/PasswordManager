package Gui;

import Controllers.EncryptionController;
import Controllers.FilesController;
import Controllers.JsonController;
import Exceptions.EmptyFileException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class AppGui {
    // variable gui
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JButton button2;

    /// variable techniques personnels
    private FilesController fileController;
    private EncryptionController encryptionController;
    private JsonController jsonController;

    volatile private boolean isLogged;


    public AppGui(FilesController fileController, EncryptionController encryptionController,
                  JsonController jsonController) {
        this.fileController = fileController;
        this.jsonController = jsonController;
        this.encryptionController = encryptionController;
        this.isLogged = false;
        this.panel1.setSize(800, 600);

        try {
            this.jsonController.getContentFromString(this.fileController.getFileContent());
        } catch (EmptyFileException e) {
            e.printStackTrace();
        }

        ActionListener btnListener = EncryptionController -> {
            checkPassword();
        };
        this.button1.addActionListener(btnListener);

        ActionListener exitBtnListener = EncryptionController -> System.exit(0);
        this.button2.addActionListener(exitBtnListener);
    }


    public AppGui() {
        this.fileController = new FilesController("./data.json");
        this.jsonController = new JsonController();
        new AppGui(this.fileController, this.encryptionController, this.jsonController);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public boolean getIsLogged(){
        return this.isLogged;
    }

    private void checkPassword(){
        this.encryptionController.setMasterPassword(new String(this.passwordField1.getPassword()));
        checkPassword(this.encryptionController);
    }
    private void checkPassword(EncryptionController encryptionController){
        this.encryptionController = encryptionController;
        System.out.println();

        if (this.jsonController.getPasswordDataFromKey("MyPasswordManager", "name", this.encryptionController).equals("azerty")) {
            System.out.println("logged in !");
            this.isLogged = true;
        }
    }
}