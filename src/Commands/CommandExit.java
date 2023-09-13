package Commands;

import Controllers.FilesController;
import Controllers.JsonController;

public class CommandExit extends Command{
    private FilesController fileController;
    private JsonController jsonController;


    public CommandExit(FilesController fileController, JsonController jsonController){
        this.name = "exit";
        this.desc = "command to quit the program";
        this.fileController = fileController;
        this.jsonController = jsonController;
    }
    @Override
    public boolean exec() {
        this.fileController.writeInFile(this.jsonController.getStringFromJsonObject());
        return true;
    }
}
