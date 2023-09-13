package Commands;

import PasswordManager.JsonController;

public class CommandRemove extends Command{
    // Commande pour supprimé une entrée de la JsonArray
    private final JsonController jsonController;
    public CommandRemove(JsonController jsonController){
        super();
        this.jsonController = jsonController;
        this.name = "delete";
        this.desc = "command allow to delete an entry";
    }

    @Override
    public boolean exec() {
        // on supprime la dernère entrée
        // TODO : faire en sorte de pouvoir choisir l'entrée à supprimé
        jsonController.removeDataFromJsonBuffer(jsonController.getLastId());
        return true;
    }
}
