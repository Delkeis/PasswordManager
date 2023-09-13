import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Commands.*;
import PasswordManager.EncryptionController;
import PasswordManager.FilesController;
import PasswordManager.JsonController;

public class Main {

    public static void main(String[] args) {
        FilesController fc = new FilesController("./data.json");
        JsonController jsc = new JsonController();
        Scanner userCommandScanner = new Scanner(System.in);
        System.out.printf("Master Password : ");
        EncryptionController ec = new EncryptionController(userCommandScanner.nextLine());

        boolean stop = false;
        String userCommand;

        try{
            jsc.getContentFromString(fc.getFileContent());
        } catch (Exception e){
            jsc.addDatasInJsonBuffer("MyPasswordManager", "delkeis.fr", "passwordChecker", ec.encrypt("azerty"));
            fc.writeInFile(jsc.getStringFromJsonObject());
            System.out.println("Fichier Vide");
        } finally {
            if (!jsc.getUniqueDataFromKey("MyPasswordManager", "name", ec).equals("azerty")) {
                System.out.println("BAD PASSWORD");
                return;
            }
        }

        List<Command> commands = new ArrayList<>();
        commands.add(new CommandList(jsc));
        commands.add(new CommandSave(fc, jsc));
        commands.add(new CommandAddEntry(jsc, userCommandScanner, ec));
        commands.add(new CommandRemove(jsc));
        commands.add(new CommandSearch(userCommandScanner, jsc, ec));

        while (!stop){
            System.out.printf(" -> ");
            userCommand = userCommandScanner.nextLine().split(" ")[0];

            switch (userCommand.toLowerCase()){
                case "exit":
                    stop = true;
                    break;
                case "help":
                    helper(commands);
                    break;
                default:
                    for (Command c: commands){
                        if (c.getName().equals(userCommand.toLowerCase()))
                            c.exec();
                    }
                    break;
            }
        }
        fc.close();
    }
    public static void helper(List<Command> cmdList){
        String helpString = "type :\n" +
                "help -> command for other command informations\n" +
                "exit -> command t o stop the program\n";
        for (Command c:cmdList) {
            helpString += c.helpString() + "\n";
        }
        System.out.printf(helpString);
    }
}