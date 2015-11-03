package com.mycompany.mythirdapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.Toast;


public class DetailsActivity extends ActionBarActivity {

    private ImageView superSizeImage;
    private TextView title;
    private TextView price;
    private TextView location;
    private ImageView topRatedBadge;
    private Button buyButton;
    private ImageButton facebookButton;

    private Button basicInfoButton;
    private TextView catagoryName;
    private TextView condition;
    private TextView buyingFormat;

    private Button sellerButton;
    private TextView userName;
    private TextView feedbackScore;
    private TextView positiveFeedback;
    private TextView feedbackRating;
    private ImageView topRated;
    private TextView store;

    private Button shippingButton;
    private TextView shippingType;
    private TextView handlingTime;
    private TextView shippingLocations;
    private ImageView expeditedShipping;
    private ImageView oneDayShipping;
    private ImageView returnsAccepted;

    private RelativeLayout basicLayout;
    private RelativeLayout sellerLayout;
    private RelativeLayout shippingLayout;

    private item theItem;
    private String facebookContent;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        superSizeImage = (ImageView) findViewById(R.id.detail_superSizeImage);
        title = (TextView)findViewById(R.id.detail_title);
        price = (TextView)findViewById(R.id.detail_price);
        location = (TextView)findViewById(R.id.detail_location);
        topRatedBadge = (ImageView)findViewById(R.id.detail_topRatedBadge);
        buyButton = (Button)findViewById(R.id.detail_buyButton);
        facebookButton = (ImageButton)findViewById(R.id.detail_facebookButton);

        basicInfoButton = (Button)findViewById(R.id.basicInfoButton);
        catagoryName = (TextView)findViewById(R.id.catagoryName);
        condition = (TextView)findViewById(R.id.condition);
        buyingFormat = (TextView)findViewById(R.id.buyingFormat);

        sellerButton = (Button)findViewById(R.id.sellerButton);
        userName = (TextView)findViewById(R.id.userName);
        feedbackScore = (TextView)findViewById(R.id.feedbackScore);
        positiveFeedback = (TextView)findViewById(R.id.positiveFeedback);
        feedbackRating = (TextView)findViewById(R.id.feedbackRating);
        topRated = (ImageView)findViewById(R.id.topRated);
        store = (TextView)findViewById(R.id.store);



        shippingButton = (Button)findViewById(R.id.shippingButton);
        shippingType = (TextView)findViewById(R.id.shippingType);
        handlingTime = (TextView)findViewById(R.id.handlingTime);
        shippingLocations = (TextView)findViewById(R.id.shippingLocations);
        expeditedShipping = (ImageView)findViewById(R.id.expeditedShipping);
        oneDayShipping = (ImageView)findViewById(R.id.oneDayShipping);
        returnsAccepted = (ImageView)findViewById(R.id.returnsAccepted);

        basicLayout = (RelativeLayout)findViewById(R.id.basicLayout);
        sellerLayout = (RelativeLayout)findViewById(R.id.sellerLayout);
        shippingLayout = (RelativeLayout)findViewById(R.id.shippingLayout);

        JSONObject jsonObject;
        ArrayList<item> jsonArray;

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra(UsersAdapter.JSON_STRING);
        String position = intent.getStringExtra(UsersAdapter.JSON_POSITION);
        try {
            jsonObject = new JSONObject(jsonString);
            jsonArray = item.fromJson2(jsonObject);
            theItem = jsonArray.get(Integer.parseInt(position));

            //set image
            if(theItem.pictureURLSuperSize() != "")
                superSizeImage.setTag(theItem.pictureURLSuperSize());
            else
                superSizeImage.setTag(theItem.galleryURL());
            new DownloadImageTask().execute(superSizeImage);
            //set title
            if(theItem.location() != "")
                title.setText(theItem.title());
            else
                title.setText("N/A");
            //set price
            String tempString;
            if(theItem.convertedCurrentPrice() != "")
                tempString = "Price: $" + theItem.convertedCurrentPrice();
            else
                tempString = "Price: N/A";

            if(theItem.shippingServiceCost() != "") {
                float temp = Float.parseFloat(theItem.shippingServiceCost());
                if (temp == 0)
                    tempString = tempString + "(FREE SHIPPING)";
                else
                    tempString = tempString + "(+ $" + theItem.shippingServiceCost() + " Shipping)";
            }
            else
                tempString = tempString + "(+ $" + "N/A" + " Shipping)";
            price.setText(tempString);

            //set location
            String tempString1;
            if(theItem.location() != "")
                tempString1 = theItem.location();
            else
                tempString1 = "N/A";
            location.setText(tempString1);

            facebookContent = tempString + ", Location: " + tempString1;

            //set toprated button
            if(theItem.topRatedListing().equals("true")) {
                topRatedBadge.setTag("http://cs-server.usc.edu:45678/hw/hw8/itemTopRated.jpg");
                new DownloadImageTask().execute(topRatedBadge);
            }
            // basic info layout
            if(theItem.categoryName().equals(""))
                catagoryName.setText("N/A");
            else
                catagoryName.setText(theItem.categoryName());

            if(theItem.conditionDisplayName().equals(""))
                condition.setText("N/A");
            else
                condition.setText(theItem.conditionDisplayName());

            if(theItem.listingType().equals(""))
                buyingFormat.setText("N/A");
            else
                buyingFormat.setText(theItem.listingType());

            //seller info
            if(theItem.sellerUserName().equals(""))
                userName.setText("N/A");
            else
                userName.setText(theItem.sellerUserName());

            if(theItem.feedbackScore().equals(""))
                feedbackScore.setText("N/A");
            else
                feedbackScore.setText(theItem.feedbackScore());

            if(theItem.positiveFeedbackPercent().equals(""))
                positiveFeedback.setText("N/A");
            else
                positiveFeedback.setText(theItem.positiveFeedbackPercent());


            if(theItem.feedbackRatingStar().equals(""))
                feedbackRating.setText("N/A");
            else
                feedbackRating.setText(theItem.feedbackRatingStar());

            if(theItem.topRatedSeller().equals("false"))
                topRated.setImageResource(R.drawable.crossmark);
            else
                topRated.setImageResource(R.drawable.check);

            if(theItem.sellerStoreName().equals(""))
                store.setText("N/A");
            else
                store.setText(theItem.sellerStoreName());
//shipping info
            if(theItem.shippingType().equals(""))
                shippingType.setText("N/A");
            else
                shippingType.setText(theItem.shippingType());

            if(theItem.handlingTime().equals(""))
                handlingTime.setText("N/A");
            else
                handlingTime.setText(theItem.handlingTime());

            if(theItem.shipToLocations().equals(""))
                shippingLocations.setText("N/A");
            else
                shippingLocations.setText(theItem.shipToLocations());

            if(theItem.expeditedShipping().equals("false"))
                expeditedShipping.setImageResource(R.drawable.crossmark);
            else
                expeditedShipping.setImageResource(R.drawable.check);

            if(theItem.oneDayShippingAvailable().equals("false"))
                oneDayShipping.setImageResource(R.drawable.crossmark);
            else
                oneDayShipping.setImageResource(R.drawable.check);

            if(theItem.returnsAccepted().equals("false"))
                returnsAccepted.setImageResource(R.drawable.crossmark);
            else
                returnsAccepted.setImageResource(R.drawable.check);




            //add link to buy button
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    Uri uri = Uri.parse(theItem.viewItemURL());
                    intent.setData(uri);
                    startActivity(intent);
                   }
            });
            basicInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    basicLayout.setVisibility(View.VISIBLE);
                    sellerLayout.setVisibility(View.INVISIBLE);
                    shippingLayout.setVisibility(View.INVISIBLE);
                    basicInfoButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
                }
            });

            sellerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    basicLayout.setVisibility(View.INVISIBLE);
                    sellerLayout.setVisibility(View.VISIBLE);
                    shippingLayout.setVisibility(View.INVISIBLE);
                    sellerButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
                }
            });

            shippingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    basicLayout.setVisibility(View.INVISIBLE);
                    sellerLayout.setVisibility(View.INVISIBLE);
                    shippingLayout.setVisibility(View.VISIBLE);
                    shippingButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
                }
            });

            facebookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // this part is optional
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(theItem.title())
                                .setContentDescription(facebookContent)
                                .setContentUrl(Uri.parse(theItem.viewItemURL()))
                                .setImageUrl(Uri.parse(theItem.galleryURL()))
                                .build();
                        shareDialog.show(linkContent, ShareDialog.Mode.FEED);
                    }




                    }
            });

            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    String tempString = "Posted Story, ID: " + result.getPostId();
                    Toast.makeText(DetailsActivity.this, tempString, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {

                    Toast.makeText(DetailsActivity.this, "Post error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancel() {

                    Toast.makeText(DetailsActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
                }

            });




        }
        catch(JSONException e){}

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
