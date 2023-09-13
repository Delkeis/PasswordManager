package Commands;

import PasswordManager.EncryptionController;
import PasswordManager.JsonController;

import java.util.Scanner;

public class CommandSearch extends Command{
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
    public void exec() {
        System.out.printf("do you want to search with : (name / user_name / site)\n" +
                "-> ");
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
                System.out.println("choice not recognize -- back to menu");
                break;
        }
    }
}
