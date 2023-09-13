package Commands;

import Controllers.EncryptionController;
import Controllers.JsonController;

import java.util.List;
import java.util.Scanner;

public class CommandRemove extends Command{
    // Commande pour supprimé une entrée de la JsonArray
    private final JsonController jsonController;
    private Scanner userCommandScanner;
    private EncryptionController encryptionController;

    public CommandRemove(JsonController jsonController, Scanner userCommandScanner, EncryptionController encryptionController){
        super();
        this.jsonController = jsonController;
        this.userCommandScanner = userCommandScanner;
        this.encryptionController = encryptionController;
        this.name = "delete";
        this.desc = "command allow to delete an entry";
    }

    private void deleteAll(String key){
        String content;

        System.out.print("the "+key+" : ");
        content = this.userCommandScanner.nextLine().trim().toLowerCase();
        System.out.print("Do you want to delete all of there entry ? : \n" +
                this.jsonController.getDataFromKey(content, "name", this.encryptionController)+
                "\n(Y/N) :");
        if (this.userCommandScanner.nextLine().trim().toLowerCase().equals("y")){
            List<Integer> intlist =  this.jsonController.getIdFromKey(content, "name");
            for (int index: intlist){
                this.jsonController.removeDataFromJsonBuffer(index);
            }
        }
    }

    @Override
    public boolean exec() {
        // on demande quel clé doit servir à faire la recherche
        System.out.print("do you want to search with : (name / user_name / site ) ?\n" +
                "$> ");
        // on switch sur le choix de l'utilisateur
        // et on lance la recherche grâce à la clé
        switch (this.userCommandScanner.nextLine().trim().toLowerCase()) {
            case "name":
                deleteAll("name");
                break;
            case "username":
            case "user name":
            case "user_name":
                deleteAll("user_name");
                break;
            case "site":
                deleteAll("site");
                break;
            default:
                return true;
        }
       return true;
    }
}
