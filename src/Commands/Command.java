package Commands;

public abstract class Command {
    String name = null;
    String desc = null;
    public String helpString(){return this.name + " -> " + this.desc;}
    public abstract void exec();
    public String getName(){return this.name;}

}
