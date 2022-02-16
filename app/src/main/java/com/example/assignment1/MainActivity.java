package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    EditText name;
    CheckBox addition1;
    CheckBox addition2;
    Spinner noOfScreen;
    Spinner feature;
    AutoCompleteTextView province;
    DatePicker videoDate;
    Button submitButton;
    String radioBut;
    String choice;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final String[] PROVINCE = new String[] {
            "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
            "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island",
            "Quebec", "Saskatchewan", "Yukon",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenSpinner();
        standardSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PROVINCE);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.province);
        textView.setAdapter(adapter);

        name = findViewById(R.id.name);
        submitButton = findViewById(R.id.submitButton);
        videoDate = findViewById(R.id.datePicker2);
        province = findViewById(R.id.province);
        noOfScreen = findViewById(R.id.screen_spinner);
        addition1 = findViewById(R.id.checkbox_meat);
        addition2 = findViewById(R.id.checkbox_cheese);
        radioBut = "Standard";
        feature = findViewById(R.id.feature_spinner);


        submitButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String namess = name.getText().toString();
                String video = videoDate.getMonth() + 1 + "/" + videoDate.getDayOfMonth() + "/" + videoDate.getYear();
                String prov = province.getText().toString();
                String screen = noOfScreen.getSelectedItem().toString();
                String featureChoice = feature.getSelectedItem().toString();

                Double cost = getPlanValue() + getAddition() + getScreenValue(screen) +  getFeature(featureChoice);



                cost += cost * .13;

                Toast.makeText(getApplicationContext(),"On " + video + ", for " + namess + " from " + prov + ", plan " + radioBut +
                        ", with " + choice + ", " +  screen + " Screens, and "  + featureChoice + ", cost: $" + df.format(cost),Toast.LENGTH_LONG).show();



            }
        });
    }

    private Double getFeature(String featureChoice) {
        Double cost = new Double("0.00f");
        if(featureChoice.equals("Multi-device Downloads")){
            cost += 3.99;
        }else if(featureChoice.equals("Watch on Laptop")){
            cost += 2.99;
        }else if(featureChoice.equals("Ultra HD")){
            cost += 4.99;
        }else if(featureChoice.equals("Priority Support")){
            cost += 6.99;
        }
        return cost;
    }

    private Double getScreenValue(String screen){
        Double cost = new Double("0.00f");
        if(screen.equals("2")){
            cost += 2.99;
        }else if(screen.equals(("4"))){
            cost += 4.99;
        }

        return cost;
    }

    private Double getPlanValue() {
        Double cost = new Double("0.00f");
        if(this.radioBut.equals("Standard")){
            cost += 14.99;
        }else if(this.radioBut.equals("Premium")){
            cost += 18.99;
        }
        return cost;
    }

    private Double getAddition() {
        Double cost = new Double("0.00f");
        if(addition1.isChecked() && !addition2.isChecked()){
            choice = addition1.getText().toString();
            cost += 5.25;
        }else if(!addition1.isChecked() && addition2.isChecked()){
            choice = addition2.getText().toString();
            cost += 6.49;
        }else if(addition1.isChecked() && addition2.isChecked()){
            choice = addition1.getText().toString() + " and " + addition2.getText().toString();
            cost += 5.25 + 6.49;
        }
        return cost;
    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    standardSpinner();
                radioBut = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    premiumSpinner();
                radioBut = ((RadioButton) view).getText().toString();
                    break;
        }
    }

    public void onCheckboxClicked(View view) {
    }

    private void screenSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.screen_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.screen_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void standardSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.feature_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.standard_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void premiumSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.feature_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.premium_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


}