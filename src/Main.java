import Serices.EncryptionService;
import Serices.FilesService;
import Serices.JsonService;
import Gui.AppGui;
import Gui.PasswordManagerWindows;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        if (args[1].toLowerCase().equals("-nogui"))
        {
           PasswordManager passwordManager = new PasswordManager();
           passwordManager.run();
        }
        else
        {
            FilesService filesService = new FilesService("./data.json");
            JsonService jsonService = new JsonService();
            EncryptionService encryptionService = new EncryptionService("");


            AppGui appGui = new AppGui(filesService, encryptionService, jsonService);
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

                    PasswordManagerWindows passwordManagerWindows = new PasswordManagerWindows(jsonService, encryptionService, filesService);
                    frame = new JFrame("Password Manager");
                    frame.setContentPane(passwordManagerWindows.getPanel1());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            } while (!isLogged);
        }
    }
}