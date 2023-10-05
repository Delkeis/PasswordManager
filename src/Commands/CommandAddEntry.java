package Commands;

import Serices.EncryptionService;
import Serices.JsonService;

import java.util.Scanner;

public class CommandAddEntry extends Command {
    // la classe permet d'ajouter une entrée dans la JsonArray

    private final JsonService jsonService;
    private final Scanner userCommandScanner;
    private final EncryptionService encryptionService;

    public CommandAddEntry(JsonService jsonService, Scanner userCommandScanner, EncryptionService encryptionService){
        super();
        this.jsonService = jsonService;
        this.userCommandScanner = userCommandScanner;
        this.encryptionService = encryptionService;
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
        tmpPassword = this.encryptionService.encrypt(tmpPassword);
        // on enregistre l'entrée dans la JsonArray
        this.jsonService.addDatasInJsonBuffer(tmpName, tmpSite, tmpUserName, tmpPassword);
        return true;
    }
}
