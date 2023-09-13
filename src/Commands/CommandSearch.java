package Commands;

import PasswordManager.EncryptionController;
import PasswordManager.JsonController;

import java.util.Scanner;

public class CommandSearch extends Command{
    // Commande pour cherchez de la donnée parmis celles présentes dans la JsonArray
    private final JsonController jsc;
    private final Scanner userCommandScanner;
    private final EncryptionController ec;

    public CommandSearch(Scanner userCommandScanner, JsonController jsc, EncryptionController ec){
        super();
        this.jsc = jsc;
        this.userCommandScanner = userCommandScanner;
        this.ec = ec;
        this.name = "search";
        this.desc = "command for searching an entry with name / user name / site";
    }

    @Override
    public boolean exec() {
        // on demande quel clé doit servir à faire la recherche
        System.out.printf("do you want to search with : (name / user_name / site)\n" +
                "-> ");
        // on switch sur le choix de l'utilisateur
        // et on lance la recherche grâce à la clé
        switch (this.userCommandScanner.nextLine().toLowerCase()) {
            case "name":
                System.out.printf("the name : ");
                System.out.printf(jsc.getDataFromKey(this.userCommandScanner.nextLine().toLowerCase(), "name", ec));
                break;
            case "username":
            case "user name":
            case "user_name":
                System.out.printf("the user name : ");
                System.out.printf(jsc.getDataFromKey(this.userCommandScanner.nextLine().toLowerCase(), "user_name", ec));
                break;
            case "site":
                System.out.printf("the site : ");
                System.out.printf(jsc.getDataFromKey(this.userCommandScanner.nextLine().toLowerCase(), "site", ec));
                break;
            default:
                // TODO : faire en sorte que l'utilisateur ne soit pas ramené dans le menu princupal en cas d'erreur
                System.out.println("choice not recognize -- back to menu");
                break;
        }
        return true;
    }
}
