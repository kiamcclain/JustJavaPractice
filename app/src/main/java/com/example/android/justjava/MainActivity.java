/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.customer_name);
        String name = nameField.getText().toString();

        // Ask user if they want whip cream topping on coffee.
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        // Ask user if they want chocolate topping on coffee.
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + name);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    /**
     * Calculates the price of the order.
     * @return total price
     * @param addWhippedCream if user wants whipped cream.
     * @param addChocolate if user wants chocolate.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        // Add $1 if user wants whipped cream topping.
        if(addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // Add $2 if user wants chocolate topping.
        if(addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate total order price by multiplying by quantity.
        return quantity * basePrice;
    }

    /**
     * Creates summary of the order.
     * @param name of customer.
     * @param addWhippedCream specifies whether customer wants whipped cream.
     * @param addChocolate specifies whether customer wants chocolate.
     * @return text summary
     */
    private String createOrderSummary(String name, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(calculatePrice(addWhippedCream, addChocolate)));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity <= 100)
        {
            quantity = quantity +1;
            displayQuantity (quantity);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "The maximum cups of coffee can be 100 ! Sorry :(",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0)
        {
            quantity = quantity - 1;
            displayQuantity (quantity);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "The least cups of coffee can only be 1 ! Sorry :(",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}