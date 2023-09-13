package Commands;

import PasswordManager.JsonController;

public class CommandList extends Command {
    // Commande pour affiché le contenue de la JsonArray
    // TODO : la commande est la pour le dévelopement et est vouée à disparaître

    private final JsonController jsonController;
    public CommandList(JsonController jsonController){
        super();
        this.jsonController = jsonController;
        this.name = "list";
        this.desc = "command for listing all entries";
    }

    @Override
    public boolean exec() {
        System.out.println("exec : " + this.name);
        System.out.println(jsonController.getStringFromJsonObject());
        return true;
    }
}
