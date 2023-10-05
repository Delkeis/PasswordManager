import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import Controllers.*;
import Exceptions.*;
import Commands.*;

public class PasswordManager {
    // initialisation de la classe FileController qui contient les méthodes
    // pour récupérer et manipulé les fichiers.
    private FilesController fileController;
    // JsonController permet de manipulé la matière json
    private JsonController jsonController;
    private Scanner userCommandScanner;
    private EncryptionController encryptionController;
    private List<Command> commands;
    private String userCommand;
    private boolean stop;

    public PasswordManager() {
        // initialisation de la classe FileController qui contient les méthodes
        // pour récupérer et manipulé les fichiers.
        this.fileController = new FilesController("./data.json");
        // JsonController permet de manipulé la matière json
        this.jsonController = new JsonController();
        this.userCommandScanner = new Scanner(System.in);
        this.commands = new ArrayList<>();
        this.stop = false;
    }

    public void run(){
        System.out.print("Master Password : ");
        // Encryption Controller me permet de chiffrer et déchiffrer de chaîne de characters
        this.encryptionController = new EncryptionController(this.userCommandScanner.nextLine());

        try{
            // on transcrit le contenue de fileController.fileName en format Json
            String tmpString = this.fileController.getFileContent();
            this.jsonController.getContentFromString(tmpString);
        } catch (EmptyFileException e){
            System.out.println("Fichier Vide");
            //Si aucun fichier n'est présent on créer une entrée avec des paramètres techniques
            this.jsonController.addDatasInJsonBuffer("MyPasswordManager", "delkeis.fr", "passwordChecker", this.encryptionController.encrypt("azerty"));
            // on écrit dans le fichier fileController.fileName
            this.fileController.writeInFile(this.jsonController.getStringFromJsonObject());
        } finally {
            // on vérifie que le mot de passe à bien correctement déchiffrer le contenue de l'entrée technique
            if (!this.jsonController.getPasswordDataFromKey("MyPasswordManager", "name", this.encryptionController).equals("azerty")) {
                // si non on considère que le mot de passe n'est pas bon
                System.out.println("BAD PASSWORD");
                // fin PGM
                System.exit(0);
            }
        }

        // On créer une liste de Command : classe parent pour toutes les commandes du prompt
        commandIndexing(this.commands, this.jsonController, this.fileController, this.userCommandScanner, this.encryptionController);

        // boucle pricipale du prompt
        while (!this.stop){
            System.out.print(" -> ");
            // récupération de l'entrée utilisateur
            this.userCommand = this.userCommandScanner.nextLine().trim().toLowerCase();

            // switch sur les commandes basic et puis sur la liste
            switch (this.userCommand.toLowerCase()){
                case "exit":
                case "stop":
                    Objects.requireNonNull(getCommandFromString(this.commands, "exit")).exec();
                    this.stop = true;
                    break;
                case "help":
                    // prompt d'aide / liste des commandes
                    helper(this.commands);
                    break;
                default:
                    Command c;
                    // on parcours toutes les commandes dans la liste pour trouver la bonne
                    c = getCommandFromString(this.commands, this.userCommand.toLowerCase());
                    if  (c != null){
                        if (!c.exec())
                            c.onFailed();
                    }
                    else // aucune commande trouvé.
                        System.out.println("Wrong Command you can type -> help");
                    break;
           }
        }
        this.fileController.close();
    }

    private static Command getCommandFromString(List<Command> commands, String commandName){
        for (Command c: commands){
            if (c.getName().equals(commandName))
                // TODO : par défaut exec() retourne toujour vrais, faire en sorte qu'il retourne faux en cas de problème
                return c;
        }
        return null;
    }

    private static void commandIndexing(List<Command> commands, JsonController jsonController, FilesController filesController,
                                        Scanner userCommandScanner, EncryptionController encryptionController){
        // chaque nouvelle commande doit être ajouté ici à la liste initialisé uniquement par son contructeur.
        commands.add(new CommandExit(filesController, jsonController));
        commands.add(new CommandList(jsonController));
        commands.add(new CommandSave(filesController, jsonController));
        commands.add(new CommandAddEntry(jsonController, userCommandScanner, encryptionController));
        commands.add(new CommandRemove(jsonController, userCommandScanner, encryptionController));
        commands.add(new CommandSearch(userCommandScanner, jsonController, encryptionController));
    }

    public static void helper(List<Command> cmdList){
        // on liste toutes les commandes qui éxiste pour l'imprimé à l'utilisateur.
        StringBuilder helpString = new StringBuilder("type :\n" +
                "help -> command for other command information\n");
        for (Command c:cmdList) {
            helpString.append(c.helpString()).append("\n");
        }
        System.out.printf(helpString.toString());
    }
}
