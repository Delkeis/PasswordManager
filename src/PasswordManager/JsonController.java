package PasswordManager;

import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonController {

    private JSONArray jsonA;


    public JsonController(){
        this.jsonA = new JSONArray();
    }


    public int getLastId(){
        if (this.jsonA.length() <= 0)
            return 0;
        JSONObject jsobj = jsonA.getJSONObject(jsonA.length() - 1);
        return Integer.parseInt(jsobj.getString("id"));
    }
    public void addDatasInJsonBuffer(String name, String site, String userName, String password){
        int id = getLastId();
        id++;

        Map<String, String> map = new HashMap<>();
        map.put("id", ""+id);
        map.put("name", name);
        map.put("site", site);
        map.put("user_name", userName);
        map.put("password", password);

        this.jsonA.put(map);
    }

    public boolean removeDataFromJsonBuffer(int id)
    {
        try{
           this.jsonA.remove(id - 1);
           return true;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return false;
        }
    }
    public void getContentFromString(String content){
        this.jsonA = new JSONArray(content);
    }

    public String getStringFromJsonObject(){
        return this.jsonA.toString();
    }


    public String getDataFromKey(String name, String key, EncryptionController ec){
        List<Integer> idList = getIdFromKey(name, key);
        String separator = "========================";
        String data = "";

        for (int id: idList){
            JSONObject jsobj = this.jsonA.getJSONObject(id);
            data += "name : \u001B[32m" + jsobj.getString("name") + "\u001B[0m\n" +
                    "user name : \u001B[32m" + jsobj.getString("user_name") + "\u001B[0m\n" +
                    "site : \u001B[32m" + jsobj.getString("site") + "\u001B[0m\n" +
                    "password : \u001B[32m" + ec.decrypt(jsobj.getString("password")) + "\u001B[0m\n" +
                    separator + "\n";
        }
        if (!data.equals(""))
            return data;
        else
            return key + " not found";
    }

    public String getUniqueDataFromKey(String name, String key, EncryptionController ec){
        List<Integer> idList = getIdFromKey(name, key);
        return ec.decrypt(this.jsonA.getJSONObject(idList.get(0)).getString("password"));
    }

    private List<Integer> getIdFromKey(String name, String key){
        List<Integer> idList = new ArrayList<>();

        for (int i = 0; i <= this.jsonA.length() - 1; i++){
            JSONObject jsobj = jsonA.getJSONObject(i);
            if (jsobj.getString(key).equalsIgnoreCase(name))
                idList.add(Integer.parseInt(jsobj.getString("id")) - 1);
        }
        return idList;
    }
}
