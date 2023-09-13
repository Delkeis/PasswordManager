package Commands;

import PasswordManager.EncryptionController;
import PasswordManager.JsonController;

import java.util.Scanner;

public class CommandAddEntry extends Command {
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
    public void exec() {
        System.out.printf("name = ");
        String tmpName = this.userCommandScanner.nextLine();
        System.out.printf("user name = ");
        String tmpUserName = this.userCommandScanner.nextLine();
        System.out.printf("site = ");
        String tmpSite = this.userCommandScanner.nextLine();
        System.out.printf("password = ");
        String tmpPassword = this.userCommandScanner.nextLine();
        tmpPassword = ec.encrypt(tmpPassword);
        this.jsc.addDatasInJsonBuffer(tmpName, tmpSite, tmpUserName, tmpPassword);
    }
}
