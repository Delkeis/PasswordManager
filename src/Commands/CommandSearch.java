package Commands;

import Serices.EncryptionService;
import Serices.JsonService;

import java.util.Scanner;

public class CommandSearch extends Command{
    // Commande pour cherchez de la donnée parmi celles présentes dans la JsonArray
    private final JsonService jsonService;
    private final Scanner userCommandScanner;
    private final EncryptionService encryptionService;

    public CommandSearch(Scanner userCommandScanner, JsonService jsonService, EncryptionService encryptionService){
        super();
        this.jsonService = jsonService;
        this.userCommandScanner = userCommandScanner;
        this.encryptionService = encryptionService;
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
                System.out.println(jsonService.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "name", encryptionService));
                break;
            case "username":
            case "user name":
            case "user_name":
                System.out.print("the user name : ");
                System.out.println(jsonService.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "user_name", encryptionService));
                break;
            case "site":
                System.out.print("the site : ");
                System.out.println(jsonService.getDataFromKey(this.userCommandScanner.nextLine().trim().toLowerCase(), "site", encryptionService));
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
