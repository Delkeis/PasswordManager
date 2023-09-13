package PasswordManager;

import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonController {
    // classe dédier à la gestion du Json Création / ajout / supression etc.. 

    private JSONArray jsonArray;


    public JsonController(){
        this.jsonArray = new JSONArray();
    }


    public int getLastId(){
        // on récupère l'id de la dernière occurence d'objet json
        //TODO : faire en sorte de récupérer le premier id disponible à la place
        if (this.jsonArray.length() <= 0)
            return 0;
        JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
        return Integer.parseInt(jsonObject.getString("id"));
    }
    public void addDatasInJsonBuffer(String name, String site, String userName, String password){
        // on ajoute les arguments en entrées pour composer un nouvel object Json
        // pour ensuite l'ajouter à la JsonArray
        int id = getLastId();
        id++;

        Map<String, String> map = new HashMap<>();
        map.put("id", ""+id);
        map.put("name", name);
        map.put("site", site);
        map.put("user_name", userName);
        map.put("password", password);

        this.jsonArray.put(map);
    }

    public boolean removeDataFromJsonBuffer(int id)
    {
        // on delete la drenière entrée de la JsonArray
        // TODO : faire en sorte de pouvoir choisir l'entrée à supprimé
        try{
           this.jsonArray.remove(id - 1);
           return true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return false;
        }
    }
    public void getContentFromString(String content){
        // on initialise la JsonArray avec le contenu brut de la String
        this.jsonArray = new JSONArray(content);
    }

    public String getStringFromJsonObject(){
        // on récupère le contenu brut de la JsonArray
        // TODO : la commande est voué à disparaître
        return this.jsonArray.toString();
    }


    public String getDataFromKey(String name, String key, EncryptionController encryptionController){
        // on récupère un ou plusieurs object depuis la JsonArray grâce à une clé.
        List<Integer> idList = getIdFromKey(name, key);
        String separator = "========================";
        String data = "";

        for (int id: idList){
            JSONObject jsonObject = this.jsonArray.getJSONObject(id);
            data += "name : \u001B[32m" + jsonObject.getString("name") + "\u001B[0m\n" +
                    "user name : \u001B[32m" + jsonObject.getString("user_name") + "\u001B[0m\n" +
                    "site : \u001B[32m" + jsonObject.getString("site") + "\u001B[0m\n" +
                    "password : \u001B[32m" + encryptionController.decrypt(jsonObject.getString("password")) + "\u001B[0m\n" +
                    separator + "\n"; // on déchiffre le mot de passe au passage
        }
        if (!data.equals(""))
            return data;
        else
            return key + " not found";
    }

    public String getPasswordDataFromKey(String name, String key, EncryptionController encryptionController){
        // on récupère le mot de passe deupuis la clé
        List<Integer> idList = getIdFromKey(name, key);
        return encryptionController.decrypt(this.jsonArray.getJSONObject(idList.get(0)).getString("password"));
    }

    private List<Integer> getIdFromKey(String name, String key){
        // on cherche les id qui correspondes à la clé
        List<Integer> idList = new ArrayList<>();

        for (int i = 0; i <= this.jsonArray.length() - 1; i++){
            JSONObject jsobj = jsonArray.getJSONObject(i);
            if (jsobj.getString(key).equalsIgnoreCase(name))
                idList.add(Integer.parseInt(jsobj.getString("id")) - 1);
        }
        return idList;
    }
}
