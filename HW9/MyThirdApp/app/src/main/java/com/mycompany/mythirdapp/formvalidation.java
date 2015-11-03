package com.mycompany.mythirdapp;

/**
 * Created by Administrator on 2015/4/21.
 */
import android.widget.EditText;
import android.widget.TextView;

public class formvalidation {


    // Error Messages
    private static final String Keyword_Empty_MSG = "The keyword must not be empty.";
    private static final String Price_From_MSG = "Must be a positive integer or decimal number.";
    private static final String Two_Field_MSG = "Price To must not be less than Price From.";



    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean validateKeywordInput(EditText editText, TextView textView) {

        String text = editText.getText().toString().trim();
        textView.setText("");

        // length 0 means there is no text
        if (text.length() == 0) {
            textView.setText(Keyword_Empty_MSG);
            return false;
        }
        return true;
    }
    // validate price from input
    public static boolean validatePriceFromInput(EditText editText, TextView textView) {

        String text = editText.getText().toString().trim();
        textView.setText("");

        // length 0 means there is no text
        if (text.length() != 0) {
            if (Float.parseFloat(text) < 0) {
                textView.setText(Price_From_MSG);
                return false;
            }
        }
        return true;
    }

    // validate price to input
    public static boolean validatePriceToInput(EditText editText1,EditText editText2, TextView textView ) {

        String text1 = editText1.getText().toString().trim(); //price from
        String text2 = editText2.getText().toString().trim(); //price to
        textView.setText("");

        // length 0 means there is no text
        if (text2.length() != 0) {
            if (Float.parseFloat(text2) < 0) {
                textView.setText(Price_From_MSG);
                return false;
            }
            if (text1.length() != 0)
                if (Float.parseFloat(text2) < Float.parseFloat(text1)) {
                    textView.setText(Two_Field_MSG);
                    return false;
                }
        }
        return true;
    }



}