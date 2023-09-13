package Commands;

import PasswordManager.JsonController;

public class CommandRemove extends Command{
    private final JsonController jsc;
    public CommandRemove(JsonController jsc){
        super();
        this.jsc = jsc;
        this.name = "delete";
        this.desc = "command allow to delete an entry";
    }

    @Override
    public void exec() {
        jsc.removeDataFromJsonBuffer(jsc.getLastId());
    }
}
