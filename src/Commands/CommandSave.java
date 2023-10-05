package Commands;

import Controllers.FilesController;
import Controllers.JsonController;
public class CommandSave extends Command {
    // commande pour enregistrer la JsonArray dans le fichier fileController.filename
    private final FilesController fileController;
    private final JsonController jsonController;

    public CommandSave(FilesController fileController, JsonController jsonController)
    {
        this.fileController = fileController;
        this.jsonController = jsonController;
        this.name = "save";
        this.desc = "command that save all data's in files";
    }
    @Override
    public boolean exec() {
        this.fileController.writeInFile(this.jsonController.getStringFromJsonObject());
        return true;
    }
}
