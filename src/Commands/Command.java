package Commands;

public abstract class Command {
    // classe parents de toutes les commandes
    // nécésitent obligatoirement un nom pour être appelé par l'utilisateur
    // et une description pour imprimer dans le /help

    // le nom nom doit être sous forme name = "commande"
    String name = null;
    // la description doit être sous la forme desc = "ce que fait la commande."
    String desc = null;
    // helpString retourne la ligne de la commande pour le /help du prompt
    public String helpString(){return this.name + " -> " + this.desc;}
    public abstract boolean exec();
    public String getName(){return this.name;}

    public void onFailed(){System.out.println("Erreur avec la commande : "+this.name);}
}
