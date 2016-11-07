package nl.weilers.david.afasie;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by david_000 on 20-9-2016.
 */

public class Json {

    public String name="/";
    public JSONObject obj = null;

    public Json(String name, String s) {
        //obj = new Objects();
        this.name = name;
        try {
            this.obj = new JSONObject(s);
        } catch (Exception e){
            Log.d("test",""+name);
        }
    }

}
