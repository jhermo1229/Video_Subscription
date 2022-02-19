package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Global variables
    public static final String MULTI_DEVICE_DOWNLOADS = "Multi-device Downloads";
    public static final String WATCH_ON_LAPTOP = "Watch on Laptop";
    public static final String ULTRA_HD = "Ultra HD";
    public static final String PRIORITY_SUPPORT = "Priority Support";
    public static final String STANDARD = "Standard";
    public static final String PREMIUM = "Premium";
    public static final String TWODECIMALPLACES = "0.00f";
    EditText name;
    CheckBox unlimitedMovies;
    CheckBox hdMovies;
    Spinner noOfScreen;
    Spinner feature;
    AutoCompleteTextView province;
    DatePicker videoDate;
    Button submitButton;
    String radioBut;
    String additionChoice;

    //Used decimalformat to get 2 decimal places.
    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Province list of canada to appear on autocomplete
    private static final String[] PROVINCE = new String[] {
            "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
            "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island",
            "Quebec", "Saskatchewan", "Yukon",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Id initialization on create
        name = findViewById(R.id.name);
        submitButton = findViewById(R.id.submitButton);
        videoDate = findViewById(R.id.subscriptionDate);
        province = findViewById(R.id.province);
        noOfScreen = findViewById(R.id.screen_spinner);
        unlimitedMovies = findViewById(R.id.unlimited_movies);
        hdMovies = findViewById(R.id.hd);
        //On default, standard is chosen on the radio button
        radioBut = STANDARD;
        feature = findViewById(R.id.feature_spinner);


        //default spinner on create for number of screens
        screenSpinner();

        //default feature spinner on create
        featureSpinner("Standard");

        //On create autocomplete textview for province.
        provinceAutoComplete();

        //Submit button event handler on click
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //On click will get all the values in the app.
                String customerName = name.getText().toString();

                //Added 1 on month as using getMonth() weirdly gets the month before instead of the one chosen.
                String video = videoDate.getMonth() + 1 + "/" + videoDate.getDayOfMonth() + "/" + videoDate.getYear();
                String prov = province.getText().toString();
                String screen = noOfScreen.getSelectedItem().toString();
                String featureChoice = feature.getSelectedItem().toString();

                //Computation of cost depending on the choices of the user.
                Double cost = getPlanValue() + getAddition() + getScreenValue(screen) +  getFeature(featureChoice);

                //This is not refactored to show that tax is added on the cost.
                cost += cost * .13;

                //Toast popup to show the output. Chosen long length.
                Toast.makeText(getApplicationContext(),"On " + video + ", for " + customerName + " from " + prov + ", plan " + radioBut +
                        ", with " + additionChoice + ", " +  screen + " Screens, and "  + featureChoice + ", cost: $" + df.format(cost),Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * This method gets the array of province and add it as an autocomplete for
     * the textview
     */
    private void provinceAutoComplete() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PROVINCE);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.province);
        textView.setAdapter(adapter);
    }


    /**
     * This method computes the cost of feature depending on the choice of the user.
     * @param featureChoice
     * @return Double cost
     */
    private Double getFeature(String featureChoice) {
        Double cost = new Double(TWODECIMALPLACES);

        if(featureChoice.equals(MULTI_DEVICE_DOWNLOADS)){
            cost += 3.99;
        }else if(featureChoice.equals(WATCH_ON_LAPTOP)){
            cost += 2.99;
        }else if(featureChoice.equals(ULTRA_HD)){
            cost += 4.99;
        }else if(featureChoice.equals(PRIORITY_SUPPORT)){
            cost += 6.99;
        }
        return cost;
    }

    /**
     * This method calculate the cost depending on the number of screens chosen.
     * @param screen
     * @return Double cost
     */
    private Double getScreenValue(String screen){
        Double cost = new Double(TWODECIMALPLACES);
        if(screen.equals("2")){
            cost += 2.99;
        }else if(screen.equals(("4"))){
            cost += 4.99;
        }

        return cost;
    }

    /**
     * This method calculate the cost depending on the plan chosen.
     * @return Double cost
     */
    private Double getPlanValue() {
        Double cost = new Double(TWODECIMALPLACES);
        if(this.radioBut.equals(STANDARD)){
            cost += 14.99;
        }else if(this.radioBut.equals(PREMIUM)){

            cost += 18.99;
        }
        return cost;
    }

    /**
     * This method calculates the cost depending on the addition purchase by user.
     * Additional is either Unlimited movies or HD or both.
     * Also add to the global string additionChoice the choice by the user.
     * Global string additionChoice will be used to show in toast
     * if the user purchase any additionals.
     * @return Double cost
     */
    private Double getAddition() {
        Double cost = new Double(TWODECIMALPLACES);
        if(unlimitedMovies.isChecked() && !hdMovies.isChecked()){
            additionChoice = unlimitedMovies.getText().toString();
            cost += 5.25;
        }else if(!unlimitedMovies.isChecked() && hdMovies.isChecked()){
            additionChoice = hdMovies.getText().toString();
            cost += 6.49;
        }else if(unlimitedMovies.isChecked() && hdMovies.isChecked()){
            additionChoice = unlimitedMovies.getText().toString() + " and " + hdMovies.getText().toString();
            cost += 5.25 + 6.49;
        }
        return cost;
    }

    /**
     * This method is the event handler for radio button.
     * Either of standard or premium is chosen.
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {

        // checks if the button is checked.
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_standard:
                if (checked)
                    featureSpinner(STANDARD);
                radioBut = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radio_premium:
                if (checked)
                    featureSpinner(PREMIUM);
                radioBut = ((RadioButton) view).getText().toString();
                    break;
        }
    }

    /**
     * This method is required by the app and throws an error if removed.
     * @param view
     */
    public void onCheckboxClicked(View view) {
    }

    /**
     * This method sets the spinner for the number of screens.
     */
    private void screenSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.screen_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.screen_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * This method gets the feature and add it to the spinner
     * If the standard radiobutton is clicked, it will add the standard fetaure in the spinner
     * else if premium, then it will add the premium.
     * @param feature
     */
    private void featureSpinner(String feature) {
        Spinner spinner = (Spinner) findViewById(R.id.feature_spinner);
        ArrayAdapter<CharSequence> adapter;

        //If feature equals to standard then add the standard array to the adapter.
        //else add the premium
        if(feature.equals(STANDARD)) {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.standard_array, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.premium_array, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}