package Commands;

import PasswordManager.JsonController;

public class CommandList extends Command {
    // Commande pour affiché le contenue de la JsonArray
    // TODO : la commande est la pour le dévelopement et est vouée à disparaître

    private final JsonController jsc;
    public CommandList(JsonController jsc){
        super();
        this.jsc = jsc;
        this.name = "list";
        this.desc = "command for listing all entries";
    }

    @Override
    public boolean exec() {
        System.out.println("exec : " + this.name);
        System.out.println(jsc.getStringFromJsonObject());
        return true;
    }
}
