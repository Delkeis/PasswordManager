package Gui;

import Serices.EncryptionService;
import Serices.FilesService;
import Serices.JsonService;
import Exceptions.EmptyFileException;

import javax.swing.*;
import java.awt.event.ActionListener;



public class AppGui {
    // variable gui
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JButton button2;

    /// variable techniques personnels
    private FilesService fileController;
    private EncryptionService encryptionService;
    private JsonService jsonService;

    volatile private boolean isLogged;


    public AppGui(FilesService fileController, EncryptionService encryptionService,
                  JsonService jsonService) {
        this.fileController = fileController;
        this.jsonService = jsonService;
        this.encryptionService = encryptionService;
        this.isLogged = false;
        this.panel1.setSize(800, 600);

        try {
            this.jsonService.getContentFromString(this.fileController.getFileContent());
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
        this.fileController = new FilesService("./data.json");
        this.jsonService = new JsonService();
        new AppGui(this.fileController, this.encryptionService, this.jsonService);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public boolean getIsLogged(){
        return this.isLogged;
    }

    private void checkPassword(){
        this.encryptionService.setMasterPassword(new String(this.passwordField1.getPassword()));
        checkPassword(this.encryptionService);
    }
    private void checkPassword(EncryptionService encryptionService){
        this.encryptionService = encryptionService;
        System.out.println();

        if (this.jsonService.getPasswordDataFromKey("MyPasswordManager", "name", this.encryptionService).equals("azerty")) {
            System.out.println("logged in !");
            this.isLogged = true;
        }
    }
}