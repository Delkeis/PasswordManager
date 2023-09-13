package Commands;

import PasswordManager.EncryptionController;
import PasswordManager.JsonController;

import java.util.Scanner;

public class CommandAddEntry extends Command {
    // la classe permet d'ajouter une entrée dans la JsonArray

    private final JsonController jsc;
    private final Scanner userCommandScanner;
    private final EncryptionController ec;

    public CommandAddEntry(JsonController jsc, Scanner userCommandScanner, EncryptionController ec){
        super();
        this.jsc = jsc;
        this.userCommandScanner = userCommandScanner;
        this.ec = ec;
        this.name = "addentry";
        this.desc = "command that permit to add an entry with name / site / password";
    }

    @Override
    public boolean exec() {
        // on récupère toutees les info nécéssaire pour complété l'entrée
        System.out.printf("name = ");
        String tmpName = this.userCommandScanner.nextLine();
        System.out.printf("user name = ");
        String tmpUserName = this.userCommandScanner.nextLine();
        System.out.printf("site = ");
        String tmpSite = this.userCommandScanner.nextLine();
        System.out.printf("password = ");
        String tmpPassword = this.userCommandScanner.nextLine();
        // on chiffre le mot de passe.
        tmpPassword = this.ec.encrypt(tmpPassword);
        // on enregistre l'entrée dans la JsonArray
        this.jsc.addDatasInJsonBuffer(tmpName, tmpSite, tmpUserName, tmpPassword);
        return true;
    }
}
