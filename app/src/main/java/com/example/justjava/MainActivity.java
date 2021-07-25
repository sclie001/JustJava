package com.example.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

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

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);


        //Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has chocolate: " + chocolate_checkBox);

        int price = calculatePrice();
        displayMessage(createOrderSummary(price, name, hasWhippedCream, hasChocolate));
    }

    /**
     * calculates the price of the order
     * @return total price
     */
    private int calculatePrice(){
        return quantity * 5;
    }

    /**
     * Creates a summary of the order
     * @param price of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param name of the customer
     * @return text summary
     */
    private String createOrderSummary(int price, String name, boolean hasWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name + "\n" + "Add whipped cream? "+
                hasWhippedCream + "\n" + "Add chocolate? " + addChocolate
                + "\n" + "Quantity: " + quantity + "\n" + "Total: " + price + "\n"
                + "Thank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees){
        TextView quantity = (TextView) findViewById(R.id.quantity_num);
        quantity.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the plus button is pressed
     * @param view plus button
     */
    public void increment(View view){
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is pressed
     * @param view minus button
     */
    public void decrement(View view){
        quantity -= 1;
        displayQuantity(quantity);
    }
}