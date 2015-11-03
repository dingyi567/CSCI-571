package com.mycompany.mythirdapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends ActionBarActivity {

    private String[] sortby;
    private Spinner spinner;
    private Button clear;
    private Button search;
    private EditText keywordInput;
    private EditText priceFromInput;
    private EditText priceToInput;
    private TextView msgField;
    public String json;
    public final static String JSON_MESSAGE = "com.mycompany.mysecondapp.MESSAGE";
    public final static String KEYWORD_MESSAGE = "com.mycompany.mysecondapp.KEYWORD_MESSAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        keywordInput = (EditText) findViewById(R.id.keywordInput);
        priceFromInput = (EditText) findViewById(R.id.priceFromInput);
        priceToInput = (EditText) findViewById(R.id.priceToInput);
        msgField = (TextView) findViewById(R.id.msgShowField);
//show spinner
        sortby = getResources().getStringArray(R.array.sortByArray);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sortby);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
//clear function
        clear = (Button)findViewById(R.id.clearButton);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordInput.setText("");
                priceFromInput.setText("");
                priceToInput.setText("");
                spinner.setSelection(0);
            }
        });
//form validation
        keywordInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                formvalidation.validateKeywordInput(keywordInput, msgField);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        priceFromInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                formvalidation.validatePriceFromInput(priceFromInput, msgField);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        priceToInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                formvalidation.validatePriceToInput(priceFromInput, priceToInput, msgField);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
//search function, validate before submit
        search = (Button)findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( checkValidation () )
                    submitForm();
                else
                    Toast.makeText(MainActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting...", Toast.LENGTH_LONG).show();
        new LongOperation().execute("");

    }
    private boolean checkValidation(){
        boolean ret = true;

        if (!formvalidation.validateKeywordInput(keywordInput, msgField)) ret = false;
        if (!formvalidation.validatePriceFromInput(priceFromInput, msgField)) ret = false;
        if (!formvalidation.validatePriceToInput(priceFromInput, priceToInput, msgField)) ret = false;

        return ret;
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            HttpClient httpclient = new DefaultHttpClient();
            String url = generateURL("http://dingyihw8-env.elasticbeanstalk.com/HW8.php");
            HttpGet httpget = new HttpGet(url);
            try {
                HttpResponse response = httpclient.execute(httpget);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream  = entity.getContent();
                    result = convertInputStreamToString(inputStream);

                } else {
                    result = "Did not work!";
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
            return result;


        }

        @Override
        protected void onPostExecute(String result) {

            json = result;
            msgField = (TextView) findViewById(R.id.msgShowField);
            //msgField.setText(result);

            try {
                JSONObject toJson = new JSONObject(json);
                String a = toJson.getString("ack");

                if (a.equals("No results found"))
                    msgField.setText("No results found.");
                else {
                    Intent intent = new Intent(MainActivity.this, DisplayItemListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(JSON_MESSAGE, json);

                    keywordInput = (EditText) findViewById(R.id.keywordInput);
                    intent.putExtra(KEYWORD_MESSAGE, keywordInput.getText().toString().trim());

                    startActivity(intent);
                }
            }
            catch(JSONException e){

            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    protected String generateURL(String url){

        keywordInput = (EditText) findViewById(R.id.keywordInput);
        priceFromInput = (EditText) findViewById(R.id.priceFromInput);
        priceToInput = (EditText) findViewById(R.id.priceToInput);
        spinner = (Spinner) findViewById(R.id.spinner);

        if(!url.endsWith("?"))
            url += "?";

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        params.add(new BasicNameValuePair("keyWords", keywordInput.getText().toString()));
        params.add(new BasicNameValuePair("minPrice", priceFromInput.getText().toString().trim()));
        params.add(new BasicNameValuePair("maxPrice", priceToInput.getText().toString().trim()));
        params.add(new BasicNameValuePair("sortBy", spinner.getSelectedItem().toString()));
        params.add(new BasicNameValuePair("resultsPerPage", "5"));

        String paramString = URLEncodedUtils.format(params, "utf-8");

        url += paramString;
        return url;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
