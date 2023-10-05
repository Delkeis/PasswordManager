package Commands;

import Serices.EncryptionService;
import Serices.JsonService;

import java.util.List;
import java.util.Scanner;

public class CommandRemove extends Command{
    // Commande pour supprimé une entrée de la JsonArray
    private final JsonService jsonService;
    private final Scanner userCommandScanner;
    private final EncryptionService encryptionService;

    public CommandRemove(JsonService jsonService, Scanner userCommandScanner, EncryptionService encryptionService){
        super();
        this.jsonService = jsonService;
        this.userCommandScanner = userCommandScanner;
        this.encryptionService = encryptionService;
        this.name = "delete";
        this.desc = "command allow to delete an entry";
    }

    private void deleteAll(String key){
        String content;
        String result;


        System.out.print("the "+key+" : ");
        content = this.userCommandScanner.nextLine().trim().toLowerCase();
        result = this.jsonService.getDataFromKey(content, "name", this.encryptionService);

        if (result.equals(""))
        {
            System.out.println(key + " : " + content + " not Found");
            return;
        }

        System.out.print("Do you want to delete all of there entry ? : \n" + result + "\n(Y/N) :");
        if (this.userCommandScanner.nextLine().trim().equalsIgnoreCase("y")){
            List<Integer> intlist =  this.jsonService.getIdFromKey(content, "name");
            for (int id: intlist){
                if (id != 1) // protection de l'entrée technique
                    this.jsonService.removeDataFromJsonBuffer(id);
                else
                    System.out.println("Impossible to delete this entry !");
            }
        }
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
