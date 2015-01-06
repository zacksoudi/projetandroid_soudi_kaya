package info.androidhive.projetandroid;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://binouze.fabrigli.fr/bieres/5.json";

    // JSON Node names


    private static final String TAG_NAME = "name";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_COUNTRY_NAME = "id";
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();




        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String category = ((TextView) view.findViewById(R.id.category))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.description))
                        .getText().toString();
                String country = ((TextView) view.findViewById(R.id.country))
                        .getText().toString();






                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_CATEGORY, category);
                in.putExtra(TAG_DESCRIPTION, description);
                in.putExtra(TAG_COUNTRY_NAME, country);
                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            for (int i = 1; i < 50; i++) {
                // Making a request to url and getting response
                url = "http://binouze.fabrigli.fr/bieres/" + i + ".json";

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

                Log.d("Response: ", "> " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);


                        // looping through All Contacts


                        String name = jsonObj.getString(TAG_NAME);
                        String category = jsonObj.getString(TAG_CATEGORY);
                        String description = jsonObj.getString(TAG_DESCRIPTION);


                        JSONObject jsonObj2 = jsonObj.getJSONObject(TAG_COUNTRY);
                        String country = jsonObj2.getString(TAG_COUNTRY_NAME);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value

                        contact.put(TAG_NAME, name);
                        contact.put(TAG_CATEGORY, category);
                        contact.put(TAG_DESCRIPTION, description);
                        contact.put(TAG_COUNTRY_NAME, country);
                        // adding contact to contact list
                        contactList.add(contact);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[]{TAG_NAME, TAG_CATEGORY, TAG_DESCRIPTION, TAG_COUNTRY_NAME}, new int[]{R.id.name, R.id.category, R.id.description, R.id.country});

            setListAdapter(adapter);



        }
    }}


