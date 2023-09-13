package Commands;

import PasswordManager.FilesController;
import PasswordManager.JsonController;
public class CommandSave extends Command {
    private final FilesController fc;
    private final JsonController jsc;

    public CommandSave(FilesController fc, JsonController jsc)
    {
        this.fc = fc;
        this.jsc = jsc;
        this.name = "save";
        this.desc = "command that save all datas in files";
    }
    @Override
    public void exec() {
        this.fc.writeInFile(this.jsc.getStringFromJsonObject());
    }
}