package Commands;

import Serices.JsonService;

public class CommandList extends Command {
    // Commande pour afficher le contenue de la JsonArray
    // TODO : la commande est la pour le dévelopement et est vouée à disparaître

    private final JsonService jsonService;
    public CommandList(JsonService jsonService){
        super();
        this.jsonService = jsonService;
        this.name = "list";
        this.desc = "command for listing all entries";
    }

    @Override
    public boolean exec() {
        System.out.println("exec : " + this.name);
        System.out.println(jsonService.getStringFromJsonObject());
        return true;
    }
}
