package com.mycompany.mythirdapp;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import android.util.Log;
/**
 * Created by Administrator on 2015/4/24.
 */
public class item {
    //basic info
    private String title;
    public String title() {
        return this.title;
    }
    private String viewItemURL;
    public String viewItemURL() {
        return this.viewItemURL;
    }
    private String galleryURL;
    public String galleryURL() {
        return this.galleryURL;
    }
    private String pictureURLSuperSize;
    public String pictureURLSuperSize() {
        return this.pictureURLSuperSize;
    }
    private String convertedCurrentPrice;
    public String convertedCurrentPrice() {
        return this.convertedCurrentPrice;
    }
    private String shippingServiceCost;
    public String shippingServiceCost() {
        return this.shippingServiceCost;
    }
    private String conditionDisplayName;
    public String conditionDisplayName() {
        return this.conditionDisplayName;
    }
    private String listingType;
    public String listingType() {
        return this.listingType;
    }
    private String location;
    public String location() {
        return this.location;
    }
    private String categoryName;
    public String categoryName() {
        return this.categoryName;
    }
    private String topRatedListing;
    public String topRatedListing() {
        return this.topRatedListing;
    }
    //seller info
    private String sellerUserName;
    public String sellerUserName() {
        return this.sellerUserName;
    }
    private String feedbackScore;
    public String feedbackScore() {
        return this.feedbackScore;
    }
    private String positiveFeedbackPercent;
    public String positiveFeedbackPercent() {
        return this.positiveFeedbackPercent;
    }
    private String feedbackRatingStar;
    public String feedbackRatingStar() {
        return this.feedbackRatingStar;
    }
    private String topRatedSeller;
    public String topRatedSeller() {
        return this.topRatedSeller;
    }
    private String sellerStoreName;
    public String sellerStoreName() {
        return this.sellerStoreName;
    }
    private String sellerStoreURL;
    public String sellerStoreURL() {
        return this.sellerStoreURL;
    }
    //shipping Info
    private String shippingType;
    public String shippingType() {
        return this.shippingType;
    }
    private String shipToLocations;
    public String shipToLocations() {
        return this.shipToLocations;
    }
    private String expeditedShipping;
    public String expeditedShipping() {
        return this.expeditedShipping;
    }
    private String oneDayShippingAvailable;
    public String oneDayShippingAvailable() {
        return this.oneDayShippingAvailable;
    }
    private String returnsAccepted;
    public String returnsAccepted() {
        return this.returnsAccepted;
    }

    private String handlingTime;
    public String handlingTime() {
        return this.handlingTime;
    }

    public static ArrayList<item> fromJson2(JSONObject jsonObject) {
        int length = 0;
        ArrayList<item> resultsArray = new ArrayList<item>();


        try {
            length = Integer.parseInt(jsonObject.getString("itemCount"));
        }
        catch(JSONException e)
        {}
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                JSONObject itemJson = null;
                String tempString = "item" + Integer.toString(i);
                try {
                    itemJson = jsonObject.getJSONObject(tempString);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                item Item = fromJson1(itemJson);
                resultsArray.add(Item);

            }
        }

        return resultsArray;
    }


    // Decodes business json into business model object
    public static item fromJson1(JSONObject jsonObject) {
        item theItem = new item();
        try {
            JSONObject basicInfoObject =  jsonObject.getJSONObject("basicInfo");
            theItem.title = basicInfoObject.getString("title");
            theItem.viewItemURL = basicInfoObject.getString("viewItemURL");
            theItem.galleryURL = basicInfoObject.getString("galleryURL");
            theItem.pictureURLSuperSize = basicInfoObject.getString("pictureURLSuperSize");
            theItem.convertedCurrentPrice = basicInfoObject.getString("convertedCurrentPrice");
            theItem.shippingServiceCost = basicInfoObject.getString("shippingServiceCost");
            theItem.conditionDisplayName = basicInfoObject.getString("conditionDisplayName");
            theItem.listingType = basicInfoObject.getString("listingType");
            theItem.location = basicInfoObject.getString("location");
            theItem.categoryName = basicInfoObject.getString("categoryName");
            theItem.topRatedListing = basicInfoObject.getString("topRatedListing");

            JSONObject sellerInfoObject =  jsonObject.getJSONObject("sellerInfo");
            theItem.sellerUserName = sellerInfoObject.getString("sellerUserName");
            theItem.feedbackScore = sellerInfoObject.getString("feedbackScore");
            theItem.positiveFeedbackPercent = sellerInfoObject.getString("positiveFeedbackPercent");
            theItem.feedbackRatingStar = sellerInfoObject.getString("feedbackRatingStar");
            theItem.topRatedSeller = sellerInfoObject.getString("topRatedSeller");
            theItem.sellerStoreName = sellerInfoObject.getString("sellerStoreName");
            theItem.sellerStoreURL = sellerInfoObject.getString("sellerStoreURL");

            JSONObject shippingInfoObject =  jsonObject.getJSONObject("shippingInfo");
            theItem.shippingType = shippingInfoObject.getString("shippingType");
            theItem.shipToLocations = shippingInfoObject.getString("shipToLocations");
            theItem.expeditedShipping = shippingInfoObject.getString("expeditedShipping");
            theItem.oneDayShippingAvailable = shippingInfoObject.getString("oneDayShippingAvailable");
            theItem.returnsAccepted = shippingInfoObject.getString("returnsAccepted");
            theItem.handlingTime = shippingInfoObject.getString("handlingTime");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return theItem;
    }


}