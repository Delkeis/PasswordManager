package Commands;

import Controllers.EncryptionController;
import Controllers.JsonController;

import java.util.Scanner;

public class CommandSearch extends Command{
    // Commande pour cherchez de la donnée parmi celles présentes dans la JsonArray
    private final JsonController jsonController;
    private final Scanner userCommandScanner;
    private final EncryptionController encryptionController;

    public CommandSearch(Scanner userCommandScanner, JsonController jsonController, EncryptionController encryptionController){
        super();
        this.jsonController = jsonController;
        this.userCommandScanner = userCommandScanner;
        this.encryptionController = encryptionController;
        this.name = "search";
        this.desc = "command for searching an entry with name / user name / site";
    }

    @Override
    public boolean exec() {
        // on demande quel clé doit servire à faire la recherche
        System.out.print("do you want to search with : (name / user_name / site ) ?\n" +
                "$> ");
        // on switch sur le choix de l'utilisateur
        // et on lance la recherche grâce à la clé
        switch (this.userCommandScanner.nextLine().trim().toLowerCase()) {
            case "name":
                System.out.print("the name : ");
                System.out.println(jsonController.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "name", encryptionController));
                break;
            case "username":
            case "user name":
            case "user_name":
                System.out.print("the user name : ");
                System.out.println(jsonController.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "user_name", encryptionController));
                break;
            case "site":
                System.out.print("the site : ");
                System.out.println(jsonController.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "site", encryptionController));
                break;
            case "back":
                return true;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onFailed(){
        System.out.println("type BACK to get back to menu");
        if (!this.exec())
            this.onFailed();
    }
}
