import Controllers.EncryptionController;
import Controllers.FilesController;
import Controllers.JsonController;
import Gui.AppGui;
import Gui.PasswordManagerWindows;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        FilesController filesController = new FilesController("./data.json");
        JsonController jsonController = new JsonController();
        EncryptionController encryptionController = new EncryptionController("");


        AppGui appGui = new AppGui(filesController, encryptionController, jsonController);
        JFrame frame = new JFrame("Password Manager");
        frame.setContentPane(appGui.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        boolean isLogged = false;
        do {
            isLogged = appGui.getIsLogged();
            if (isLogged){
                System.out.println("in !!");
                frame.setVisible(false);

                PasswordManagerWindows passwordManagerWindows = new PasswordManagerWindows(jsonController, encryptionController, filesController);
                frame = new JFrame("Password Manager");
                frame.setContentPane(passwordManagerWindows.getPanel1());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }

        } while (!isLogged);

        //PasswordManager passwordManager = new PasswordManager();
        //passwordManager.run();


    }
}