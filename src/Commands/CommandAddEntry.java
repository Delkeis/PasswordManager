package Commands;

import Controllers.EncryptionController;
import Controllers.JsonController;

import java.util.Scanner;

public class CommandAddEntry extends Command {
    // la classe permet d'ajouter une entrée dans la JsonArray

    private final JsonController jsonController;
    private final Scanner userCommandScanner;
    private final EncryptionController encryptionController;

    public CommandAddEntry(JsonController jsonController, Scanner userCommandScanner, EncryptionController encryptionController){
        super();
        this.jsonController = jsonController;
        this.userCommandScanner = userCommandScanner;
        this.encryptionController = encryptionController;
        this.name = "add entry";
        this.desc = "command that permit to add an entry with name / site / password";
    }

    @Override
    public boolean exec() {
        // on récupère toutes les info nécéssaires pour compléter l'entrée
        System.out.print("name = ");
        String tmpName = this.userCommandScanner.nextLine();
        System.out.print("user name = ");
        String tmpUserName = this.userCommandScanner.nextLine();
        System.out.print("site = ");
        String tmpSite = this.userCommandScanner.nextLine();
        System.out.print("password = ");
        String tmpPassword = this.userCommandScanner.nextLine();
        // on chiffre le mot de passe.
        tmpPassword = this.encryptionController.encrypt(tmpPassword);
        // on enregistre l'entrée dans la JsonArray
        this.jsonController.addDatasInJsonBuffer(tmpName, tmpSite, tmpUserName, tmpPassword);
        return true;
    }
}
