package Commands;

import Serices.FilesService;
import Serices.JsonService;

public class CommandExit extends Command{
    private final FilesService fileController;
    private final JsonService jsonService;


    public CommandExit(FilesService fileController, JsonService jsonService){
        this.name = "exit";
        this.desc = "command to quit the program";
        this.fileController = fileController;
        this.jsonService = jsonService;
    }
    @Override
    public boolean exec() {
        this.fileController.writeInFile(this.jsonService.getStringFromJsonObject());
        return true;
    }
}
