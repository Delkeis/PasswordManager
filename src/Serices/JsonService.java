package Serices;

import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonService {
    // classe dédié à la gestion du Json Creation / ajout / suppression etc..

    private JSONArray jsonArray;


    public JsonService(){
        this.jsonArray = new JSONArray();
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public int getFirstUnusedId() {
        // on cherche la premiere id non attribué
        int res = 1;

        // si getPositionFromId() retourne -1 cela veut dire que nous ne trouvons pas
        // l'identifiant dans la liste
        while (getPositionFromId(res) != -1)
            res++;
        return res;
    }

    @Deprecated
    public int getLastId(){
        // on récupère l'id de la dernière occurrence des objet json
        if (this.jsonArray.length() <= 0)
            return 0;
        JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
        return Integer.parseInt(jsonObject.getString("id"));
    }
    public void addDatasInJsonBuffer(String name, String site, String userName, String password){
        // on ajoute les arguments en entrées pour composer un nouvel object Json
        // pour ensuite l'ajouter à la JsonArray
        int id = getFirstUnusedId();

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("name", name);
        map.put("site", site);
        map.put("user_name", userName);
        map.put("password", password);

        this.jsonArray.put(map);
    }

    public boolean removeDataFromJsonBuffer(int id)
    {
        try{
            int position = getPositionFromId(id);
            if (position != -1)
                this.jsonArray.remove(position);
            else
                return false;
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
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

    public String[][] getAllEntryFromData(){
        // le format est :
        // str[0][id(0)] = x
        // str[0][name(1)] = name
        // str[0][username(2)] = username
        // str[0][password(3)] = password
        // str[0][site(4)] = site
        // ETC ...
        String[][] str = new String[this.jsonArray.length()][5];

        for (int i = 1; i < this.jsonArray.length(); i++){
            JSONObject jsObj = this.jsonArray.getJSONObject(i);
            str[i][0] = jsObj.getString("id");
            str[i][1] = jsObj.getString("name");
            str[i][2] = jsObj.getString("user_name");
            str[i][3] = jsObj.getString("password");
            str[i][4] = jsObj.getString("site");
        }
        return str;
    }
    public String getDataFromKey(String name, String key, EncryptionService encryptionService){
        // on récupère un ou plusieurs object depuis la JsonArray grâce à une clé.
        List<Integer> idList = getIdFromKey(name, key);
        String separator = "========================";
        StringBuilder data = new StringBuilder();

        for (int id: idList){
            // on parcours tous les id trouver pour les proposer à la suppression.
            JSONObject jsonObject;

            // on recherche le bon ID dans la liste des objects
            int position = getPositionFromId(id);
            if (position != -1)
                jsonObject = this.jsonArray.getJSONObject(position);
            else
                return "";
            if (jsonObject == null)
                return "";
            data.append("name : \u001B[32m").append(jsonObject.getString("name")).append("\u001B[0m\n")
                    .append("user name : \u001B[32m").append(jsonObject.getString("user_name")).append("\u001B[0m\n")
                    .append("site : \u001B[32m").append(jsonObject.getString("site")).append("\u001B[0m\n")
                    .append("password : \u001B[32m").append(encryptionService.decrypt(jsonObject.getString("password"))).append("\u001B[0m\n")
                    .append(separator).append("\n"); // on déchiffre le mot de passe au passage
        }
        if (!data.toString().equals(""))
            return data.toString();
        else
            return "No such entry";
    }

    public String getPasswordDataFromKey(String name, String key, EncryptionService encryptionService){
        // on récupère le mot de passe depuis la clé
        List<Integer> idList = getIdFromKey(name, key);
        return encryptionService.decrypt(this.jsonArray.getJSONObject(idList.get(0) - 1).getString("password"));
    }

    public List<Integer> getIdFromKey(String name, String key){
        // on cherche les id qui correspondent à la clé
        List<Integer> idList = new ArrayList<>();

        for (int i = 0; i <= this.jsonArray.length() - 1; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString(key).equalsIgnoreCase(name))
                idList.add(Integer.parseInt(jsonObject.getString("id")));
        }
        return idList;
    }

    private int getPositionFromId(int id){
        // on recherche le bon ID dans la liste des objects
        for (int i = 0; i < this.jsonArray.length(); i++){
            if (this.jsonArray.getJSONObject(i).getString("id").equals(String.valueOf(id)))
                return i;
        }
        return -1;
    }
}
