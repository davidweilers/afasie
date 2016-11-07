package nl.weilers.david.afasie;

import android.app.AlertDialog;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private EditText editText;
    private JSONObject obj;
    private ArrayList<JSONObject> obj2 = new ArrayList<JSONObject>();
    private TextToSpeech speech;
    private Socket s;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListView = (ListView) findViewById( R.id.mainListView );

        //new Thread(new ClientThread()).start();

        /*editText = (EditText) findViewById(R.id.editText);*/
        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
        //speech.setLanguage(Locale.forLanguageTag("nl"));

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("test",i + " " + new Json("",) )

                JSONObject obj3;
                try {
                    //Log.d("test",obj.toString());
                    String b = listAdapter.getItem((int)l);

                    Log.d("test","[] '"+b+"'");
                    if ("eten"==b) {
                        Log.d("test","() "+b);
                    }   else
                    if (".."==b) {
                        obj = new JSONObject(obj2.get(obj2.size()-1).toString());
                        obj2.remove(obj2.size()-1);
                    }   else {
                        obj3 = obj.getJSONObject(b);
                        if (obj3.length()!=0) {
                            obj2.add(new JSONObject(obj.toString()));
                            obj = obj.getJSONObject(b);
                        }
                        speech.speak(b,TextToSpeech.QUEUE_FLUSH,savedInstanceState,null);
                    }
                    Log.d("test","{} "+b+" "+obj.toString());
                    add(obj);
                    Log.d("test",""+obj2.size());
                } catch (Exception e){
                    Log.d("test",e.getMessage());
                }
            }
        });

        ArrayList<String> planetList = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
        mainListView.setAdapter( listAdapter );

        String s = (String) getString(R.string.name);

        try {
            obj = new JSONObject(s);
            obj2.add(new JSONObject(obj.toString()));
            add(obj);
        } catch (Exception e){
            Log.d("test","");
        }

        load();
    }

    void add(JSONObject obj) {
        try {
            //JSONObject obj = new JSONObject(s);
            listAdapter.clear();
            if (obj2.size()!=1) listAdapter.add("..");
            Iterator<String> j = obj.keys();
            while (j.hasNext()) {
                String a = j.next();
                //Log.d("test",a+" "+obj.getJSONObject(a));
                listAdapter.add(a);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle((String)getString(R.string.app_name));
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        switch (id) {
            case R.id.afasie:
                alertDialog.setMessage((String)getString(R.string.afasie_app));
                break;
            case R.id.about:
                alertDialog.setMessage((String)getString(R.string.about));
                break;
        }

        alertDialog.show();
        return super.onOptionsItemSelected(item);
    }

    void load() {
        try {
            s = new Socket("http://weilers.nl/json/",80);
            /*SocketAddress ss = new SocketAddress() {
            };
            s.connect(ss);*/
            InputStream sss = s.getInputStream();
            int size = sss.available();
            Log.d("hoi",""+size);
        } catch (Exception e){
            e.getMessage();
            Log.d("hoi"," ");
        }
    }

    /*public String loadJSONFromAsset() {
        String json = null;
        try {
            //String jsonLocation = AssetJSONFile("formules.json", CatList.this);
            InputStream is = getAssets().open("json.txt");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }*/

}
