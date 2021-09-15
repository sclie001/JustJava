package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    RadioButton coffeeTypeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view){
        EditText nameField = findViewById((R.id.name_field));
        String name = nameField.getText().toString();
        //Log.v("MainActivity", "What is your name: " + name);

        //Figure out the milk substitution selected
        RadioButton milkSubstitutionSelected = onRadioButtonClickedForMilkSubstitutions();

        //Figure out the espresso shot option selected
        RadioButton espressoShotOption = onRadioButtonClickedForEspressoShotOptions();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        //Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has chocolate: " + chocolate_checkBox);

        //calculate price for coffee order
        int price = calculatePrice(hasChocolate, hasWhippedCream, espressoShotOption);
        String OrderMessage = createOrderSummary(price, name, hasWhippedCream,
                hasChocolate, milkSubstitutionSelected, espressoShotOption);

        //allows user to send order summary to his/her email, using email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only email apps should handle
        String subject = getString(R.string.email_subject, name);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, OrderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Determine which radio button was selected for coffee type
     */
    public void onRadioButtonClicked(View view){
        /*RadioGroup radioGroup = findViewById(R.id.radioCoffeeType);
        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton coffeeType = findViewById(selectedID);*/

        boolean checked = ((RadioButton) view).isChecked();

        int viewId = view.getId();

        //if user selects regular coffee, espresso option and milk substitution radio buttons are
        // not clickable
        if(R.id.regularCoffee == viewId){
            if(checked){
                setEspressoShotToNotClickable();
                coffeeTypeSelected = findViewById(viewId);
                RadioButton noEspresso = findViewById(R.id.noEspressoShot);
                noEspresso.setChecked(true);
            }
        }
        //if user selects hot latte, user can select an espresso shot
        if(R.id.hotLatte == viewId){
            if(checked) {
                setEspressoShotToClickable();
                coffeeTypeSelected = findViewById(viewId);
            }
        }
        //if user selects iced coffee, user can select milk substitutions and toppings
        if(R.id.icedCoffee == viewId){
            if(checked){
                setEspressoShotToNotClickable();
                coffeeTypeSelected = findViewById(viewId);
                RadioButton noEspresso = findViewById(R.id.noEspressoShot);
                noEspresso.setChecked(true);

            }

        }
    }

    /**
     * Set espresso shot radio buttons to not be clickable
     */
    private void setEspressoShotToNotClickable(){
        RadioButton noEspressoShot = findViewById(R.id.noEspressoShot);
        noEspressoShot.setClickable(false);

        RadioButton singleEspressoShot = findViewById(R.id.singleEspressoShot);
        singleEspressoShot.setClickable(false);

        RadioButton doubleEspressoShot = findViewById(R.id.doubleEspressoShot);
        doubleEspressoShot.setClickable(false);
    }

    /**
     * Set espresso shot radio buttons to clickable
     */
    private void setEspressoShotToClickable(){
        RadioButton noEspressoShot = findViewById(R.id.noEspressoShot);
        noEspressoShot.setClickable(true);

        RadioButton singleEspressoShot = findViewById(R.id.singleEspressoShot);
        singleEspressoShot.setClickable(true);

        RadioButton doubleEspressoShot = findViewById(R.id.doubleEspressoShot);
        doubleEspressoShot.setClickable(true);
    }

    /**
     * Determine which radio button was selected for the milk substitution
     * @return milkSubstitution
     */
    public RadioButton onRadioButtonClickedForMilkSubstitutions(){
        RadioGroup radioGroupSubstitutions = findViewById(R.id.radioSubstitutions);
        int selectedID = radioGroupSubstitutions.getCheckedRadioButtonId();
        RadioButton milkSubstitution = findViewById(selectedID);

        return milkSubstitution;
    }

    /**
     * Determine which radio button was selected for espresso shot option
     * @return espressoOption
     */
    public RadioButton onRadioButtonClickedForEspressoShotOptions(){
        RadioGroup radioGroupEspressoOption = findViewById(R.id.radioEspressoShotOptions);
        int selectedID = radioGroupEspressoOption.getCheckedRadioButtonId();
        RadioButton espressoOption = findViewById(selectedID);

        return espressoOption;
    }

    /**
     * calculates the price of the order
     * @param addChocolate whether or not the user wants chocolate topping
     * @param addWhippedCream whether or not the user wants whipped cream topping
     * @param espressoShotOption whether or not the user wants an extra shot for a hot latte
     * @return total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream,
                               RadioButton espressoShotOption){
        int chargeForChocolate = 2;
        int chargeForWhippedCream = 1;
        int espressoShotCharge = 1;
        int basePrice = 2;

        if(coffeeTypeSelected.getId() == R.id.icedCoffee){
            basePrice = 3;
        }
        else if(coffeeTypeSelected.getId() == R.id.hotLatte
                && espressoShotOption.getId() == R.id.singleEspressoShot){

            basePrice = 4;
            basePrice = basePrice + espressoShotCharge;

        }
        else if(coffeeTypeSelected.getId() == R.id.hotLatte
                && espressoShotOption.getId() == R.id.doubleEspressoShot){

            basePrice = 4;
            basePrice = basePrice + (2 * espressoShotCharge);
        }

        //add $1 if the user selects the whipped cream topping
        if(addWhippedCream){
            basePrice = basePrice + chargeForWhippedCream;
        }
        //add $2 if the user selects chocolate topping
        if(addChocolate){
            basePrice = basePrice + chargeForChocolate;
        }
        return basePrice * quantity;
    }

    /**
     * Creates a summary of the order
     * @param price of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param name of the customer
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean hasWhippedCream,
                                      boolean addChocolate, RadioButton milkSubstitution,
                                      RadioButton espressoShotOption){
        String whipped = hasWhippedCream(hasWhippedCream);
        String chocolate = hasChocolate(addChocolate);

        String priceMessage = getString(R.string.order_summary_name,name) + "\n" +
                getString(R.string.order_summary_coffee_type, coffeeTypeSelected.getText()) + "\n" +
                getString(R.string.order_summary_milkSubstitution, milkSubstitution.getText())
                + "\n" + getString(R.string.order_summary_add_whipped_cream, whipped) + "\n" +
                getString(R.string.order_summary_add_chocolate,chocolate) + "\n" +
                getString(R.string.order_summary_espressoShotOption, espressoShotOption.getText())
                + "\n" + getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total, price) + "\n" +
                getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Determine whether user selected whipped cream topping
     * @param addWhippedCream is whether or not user wants whipped cream topping
     * @return yes or no
     */
    private String hasWhippedCream(boolean addWhippedCream){
        if(addWhippedCream){
            return "Yes";
        }
        return "No";
    }

    /**
     * Determine whether user selected chocolate topping
     * @param addChocolate is whether the user wants chocolate topping
     * @return yes or no 
     */
    private String hasChocolate(boolean addChocolate){
        if(addChocolate){
            return "Yes";
        }
        return "No";
    }

    /**
     * This method displays the given quantity value on the screen.
     * @param numberOfCoffees quantity of coffees the user requested
     */
    private void displayQuantity(int numberOfCoffees){
        TextView quantity = (TextView) findViewById(R.id.quantity_num);
        String coffees = "" + numberOfCoffees;
        quantity.setText(coffees);
    }

    /**
     * This method is called when the plus button is pressed
     * @param view plus button
     */
    public void increment(View view){
        //user limited to ordering 100 cups of coffee
        if (quantity <= 99){
            quantity += 1;
        }
        else{
            //show an error message as a toast
            CharSequence errorMessage = "You cannot have more than 100 coffees";
            Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
            return;
        }

        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is pressed
     * @param view minus button
     */
    public void decrement(View view){
        //user limited to ordering at least 1 cup of coffee
        if(quantity > 1){
            quantity -= 1;
        }
        else{
            //show error message as a toast
            CharSequence errorMessage = "You cannot have less than 1 coffee";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }
}
