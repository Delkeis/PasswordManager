import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Commands.*;
import PasswordManager.EncryptionController;
import PasswordManager.FilesController;
import PasswordManager.JsonController;

public class Main {

    public static void main(String[] args) {

        // initialisation de la classe FileController qui contient les méthodes
        // pour récupérer et manipuler les fichiers.
        FilesController filesController = new FilesController("./data.json");
        // JsonController permet de manipulé la matière json
        JsonController jsonController = new JsonController();
        Scanner userCommandScanner = new Scanner(System.in);
        System.out.printf("Master Password : ");
        // Encryption Controller me permet de chiffrer et déchiffrer de chaîne de charactères
        EncryptionController encryptionController = new EncryptionController(userCommandScanner.nextLine());

        boolean stop = false;
        String userCommand;

        try{
            // on transcrit le contenu de fileController.fileName en format Json
            jsonController.getContentFromString(filesController.getFileContent());
        } catch (Exception e){ // TODO : une exeption personalisé pour bien catch le fait que le contenue du fichier soit vide
            System.out.println("Fichier Vide");
            //Si aucun fichier n'est présent on créer une entrée avec des paramètres techniques
            jsonController.addDatasInJsonBuffer("MyPasswordManager", "delkeis.fr", "passwordChecker", encryptionController.encrypt("azerty"));
            // on écrit dans le fichier fileController.fileName
            filesController.writeInFile(jsonController.getStringFromJsonObject());
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
        commandIndexing(commands, jsonController, filesController, userCommandScanner, encryptionController);

        // boucle pricipale du prompt
        while (!stop){

            System.out.printf(" -> ");
            // récupération de l'entrée utilisateur
            userCommand = userCommandScanner.nextLine().split(" ")[0];


            // switch sur les commandes basic et puis sur la liste
            switch (userCommand.toLowerCase()){
                case "exit":
                    stop = true;
                    break;
                case "help":
                    // promt d'aide / liste des commandes
                    helper(commands);
                    break;
                default:
                    boolean checkExec = false;
                    // on parcours toutes les commandes dans la liste pour trouver la bonne
                    for (Command c: commands){
                        if (c.getName().equals(userCommand.toLowerCase()))
                            // TODO : par défault exec() retourne toujours vrais, faire en sorte qu'il retourne faux en cas de problème
                            checkExec = c.exec();
                    }
                    if (!checkExec)
                        // aucune commande trouvé.
                        System.out.println("Wrong Command you can type -> help");
                    break;
            }
        }
        filesController.close();
    }

    private static void commandIndexing(List<Command> commands, JsonController jsc, FilesController fc, Scanner userCommandScanner, EncryptionController ec){
        // chaque nouvelle commande doit être ajouté ici à la liste initialisé uniquement par son contructeur.
        commands.add(new CommandList(jsc));
        commands.add(new CommandSave(fc, jsc));
        commands.add(new CommandAddEntry(jsc, userCommandScanner, ec));
        commands.add(new CommandRemove(jsc));
        commands.add(new CommandSearch(userCommandScanner, jsc, ec));
    }

    public static void helper(List<Command> cmdList){
        // on liste toutes les commandes qui existe pour l'imprimer à l'utilisateur.
        String helpString = "type :\n" +
                "help -> command for other command informations\n" +
                "exit -> command t o stop the program\n";
        for (Command c:cmdList) {
            helpString += c.helpString() + "\n";
        }
        System.out.printf(helpString);
    }
}