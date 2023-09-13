import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Commands.*;
import Exceptions.EmptyFileException;
import Controllers.EncryptionController;
import Controllers.FilesController;
import Controllers.JsonController;

public class Main {

    public static void main(String[] args) {

        // initialisation de la classe FileController qui contient les méthodes
        // pour récupérer et manipuler les fichiers.
        FilesController fileController = new FilesController("./data.json");
        // JsonController permet de manipulé la matière json
        JsonController jsonController = new JsonController();
        Scanner userCommandScanner = new Scanner(System.in);
        System.out.print("Master Password : ");
        // Encryption Controller me permet de chiffrer et déchiffrer de chaîne de charactères
        EncryptionController encryptionController = new EncryptionController(userCommandScanner.nextLine());

        Boolean stop = false;
        String userCommand;

        try{
            // on transcrit le contenu de fileController.fileName en format Json
            String tmpString = fileController.getFileContent();
            jsonController.getContentFromString(tmpString);
        } catch (EmptyFileException e){
            System.out.println("Fichier Vide");
            //Si aucun fichier n'est présent on créer une entrée avec des paramètres techniques
            jsonController.addDatasInJsonBuffer("MyPasswordManager", "delkeis.fr", "passwordChecker", encryptionController.encrypt("azerty"));
            // on écrit dans le fichier fileController.fileName
            fileController.writeInFile(jsonController.getStringFromJsonObject());
        } finally {
            // on vérifie que le mot de passe à bien correctement déchffrer le contenue de l'entrée technique
            if (!jsonController.getPasswordDataFromKey("MyPasswordManager", "name", encryptionController).equals("azerty")) {
                // si non on considère que le mot de passe n'est pas bon
                System.out.println("BAD PASSWORD");
                // fin PGM
                return;
            }
        }

        // On créer une liste de Command : classe parent pour toutes les commandes du prompt
        List<Command> commands = new ArrayList<>();
        commandIndexing(commands, jsonController, fileController, userCommandScanner, encryptionController);

        // boucle pricipale du prompt
        while (!stop){
            System.out.print(" -> ");
            // récupération de l'entrée utilisateur
            userCommand = userCommandScanner.nextLine().trim().toLowerCase();

            // switch sur les commandes basic et puis sur la liste
            switch (userCommand.toLowerCase()){
                case "exit":
                case "stop":
                    getCommandFromString(commands, "exit").exec();
                    stop = true;
                    break;
                case "help":
                    // promt d'aide / liste des commandes
                    helper(commands);
                    break;
                default:
                    Command c = null;
                    // on parcours toutes les commandes dans la liste pour trouver la bonne
                    c = getCommandFromString(commands, userCommand.toLowerCase());
                    if  (c != null){
                        if (!c.exec())
                            c.onFailed();
                    }
                    else // aucune commande trouvé.
                        System.out.println("Wrong Command you can type -> help");
                    break;
            }
        }
        fileController.close();
    }

    private static Command getCommandFromString(List<Command> commands, String commandName){
        for (Command c: commands){
            if (c.getName().equals(commandName))
                // TODO : par défault exec() retourne toujours vrais, faire en sorte qu'il retourne faux en cas de problème
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
        // on liste toutes les commandes qui existe pour l'imprimer à l'utilisateur.
        String helpString = "type :\n" +
                "help -> command for other command informations\n";
        for (Command c:cmdList) {
            helpString += c.helpString() + "\n";
        }
        System.out.printf(helpString);
    }
}