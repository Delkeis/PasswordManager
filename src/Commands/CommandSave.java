package Commands;

import Serices.FilesService;
import Serices.JsonService;
public class CommandSave extends Command {
    // commande pour enregistrer la JsonArray dans le fichier fileController.filename
    private final FilesService fileController;
    private final JsonService jsonService;

    public CommandSave(FilesService fileController, JsonService jsonService)
    {
        this.fileController = fileController;
        this.jsonService = jsonService;
        this.name = "save";
        this.desc = "command that save all data's in files";
    }
    @Override
    public boolean exec() {
        this.fileController.writeInFile(this.jsonService.getStringFromJsonObject());
        return true;
    }
}
