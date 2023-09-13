package Commands;

import PasswordManager.JsonController;

public class CommandRemove extends Command{
    // Commande pour supprimé une entrée de la JsonArray
    private final JsonController jsc;
    public CommandRemove(JsonController jsc){
        super();
        this.jsc = jsc;
        this.name = "delete";
        this.desc = "command allow to delete an entry";
    }

    @Override
    public boolean exec() {
        // on supprime la dernère entrée
        // TODO : faire en sorte de pouvoir choisir l'entrée à supprimé
        jsc.removeDataFromJsonBuffer(jsc.getLastId());
        return true;
    }
}
