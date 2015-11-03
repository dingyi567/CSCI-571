package com.mycompany.mythirdapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.Adapter;


public class DisplayItemListActivity extends ActionBarActivity {
    private ArrayList<item> jsonArray;
    private TextView resultsForKeywords;
    static public  String message;
    public final static String item_MESSAGE = "com.mycompany.mysecondapp.itemMESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item_list);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.JSON_MESSAGE);
        String keyword = intent.getStringExtra(MainActivity.KEYWORD_MESSAGE);

        JSONObject json;
        ListView listView;
        resultsForKeywords = (TextView) findViewById(R.id.ResultsForKeywords);
        resultsForKeywords.setText("Results for '" + keyword + "'");

        try {
            json = new JSONObject(message);
            jsonArray = item.fromJson2(json);
//            Log.d("caonima571",jsonArray.get(0).title()+"");
            Intent theintent = new Intent(DisplayItemListActivity.this, DetailsActivity.class);
            ArrayAdapter<item> adapter = new UsersAdapter(this,theintent,jsonArray);
// Attach the adapter to a ListView
            listView = (ListView) findViewById(R.id.listView);
            //try {
            listView.setAdapter(adapter);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
//                    item theItem = jsonArray.get(position);
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                    Uri uri = Uri.parse(theItem.viewItemURL());
//                    intent.setData(uri);
//                    startActivity(intent);
//                }
//            });

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
//                    item theItem = jsonArray.get(position);
//
//                    Intent intent = new Intent(DisplayItemListActivity.this, DetailsActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(JSON_STRING, message);
//                    intent.putExtra(JSON_POSITION, Integer.toString(position));
//                    startActivity(intent);
//                }
//            });

            //}
            //catch(Throwable e){}
        }
        catch(JSONException e){}





    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}