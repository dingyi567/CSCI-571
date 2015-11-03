package com.mycompany.mythirdapp;

import android.content.Intent;
import android.content.*;
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
import android.util.Log;
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

import java.net.URLConnection;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.net.*;
import java.net.URI;
import android.content.Intent;

import android.content.Context;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;

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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
/**
 * Created by Administrator on 2015/4/24.
 */
public class UsersAdapter extends ArrayAdapter<item> {

    item theItem;
    Context adapterContext;
    Intent theIntent;
    public final static String JSON_STRING = "com.mycompany.mysecondapp.jsonString";
    public final static String JSON_POSITION = "com.mycompany.mysecondapp.jsonPosition";
    public UsersAdapter(Context context, Intent itent, ArrayList<item> users) {
        super(context, R.layout.items_display,users);
        adapterContext = context;
        theIntent = itent;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        theItem = getItem(position);
        final int position1 = position;
        //Log.d("caonima571", theItem.title());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_display, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.title);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.price);
        ImageView tvGalleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
        // Populate the data into the template view using the data object
//set title
        if (theItem.title() != "")
            tvTitle.setText(theItem.title());
        else
            tvTitle.setText("N/A");

//set price
        String tempString;
        if(theItem.convertedCurrentPrice() != "")
            tempString = "Price: $" + theItem.convertedCurrentPrice();
        else
            tempString = "Price: N/A";

        if(theItem.shippingServiceCost().equals(""))
            tempString = tempString + "(+ $" + "N/A" + " Shipping)";
        else
        {
            float temp = Float.parseFloat(theItem.shippingServiceCost());
            if (temp == 0)
                tempString = tempString + "(FREE SHIPPING)";
            else
                tempString = tempString + "(+ $" + theItem.shippingServiceCost() + " Shipping)";
        }

        tvPrice.setText(tempString);


        //fetch images using asynctask (URLconnection, InputStream and BitmapFactory)
        tvGalleryImage.setTag(theItem.galleryURL());
        new DownloadImageTask().execute(tvGalleryImage);

        tvGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                Uri uri = Uri.parse(getItem(position1).viewItemURL());
                intent.setData(uri);
                adapterContext.startActivity(intent);
            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item theItem = getItem(position1);
                theIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                theIntent.putExtra(JSON_STRING, DisplayItemListActivity.message);
                theIntent.putExtra(JSON_POSITION, Integer.toString(position1));
                adapterContext.startActivity(theIntent);
            }
        });





        return convertView;
    }




}
