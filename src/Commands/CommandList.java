package Commands;

import PasswordManager.JsonController;

public class CommandList extends Command {
    private final JsonController jsc;
    public CommandList(JsonController jsc){
        super();
        this.jsc = jsc;
        this.name = "list";
        this.desc = "command for listing all entries";
    }

    @Override
    public void exec() {
        System.out.println("exec : " + this.name);
        System.out.println(jsc.getStringFromJsonObject());
    }
}
