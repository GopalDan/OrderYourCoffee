package com.example.gopal.orderyourcoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Increment number of Coffees
    public void increment(View view) {
        if(quantity==15){
            Toast.makeText(getApplicationContext(), getString(R.string.maximum_coffee_order_toast),Toast.LENGTH_SHORT).show();
        }
        quantity = quantity + 1;
        display(quantity);
    }

    // Decrement number of Coffees
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), getString( R.string.minimum_coffee_order_toast), Toast.LENGTH_SHORT).show();
        }
        else {
            quantity = quantity - 1;
            display(quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();
        EditText customerNameView = findViewById(R.id.customer_name);
        String customerName = customerNameView.getText().toString();
        String message = "Name: " + customerName;
        message += "\nAdd Whipped Cream? " + hasWhippedCream;
        message += "\nAdd Chocolate? " + hasChocolate;
        message = message + "\nquantity: " + quantity;
        message = message + "\nTotal:$ " + calculatePrice(quantity, hasWhippedCream, hasChocolate);
        message = message + "\nThank You!";

        // Intent for e-mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String[] address = {"ctcCaffee@gmail.com "};
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary of Your coffee");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /* Calculate price  of each cup of coffee */
    public int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int totalPrice;
        if(hasWhippedCream && hasChocolate) totalPrice = quantity*8;
        else if (hasWhippedCream) totalPrice = quantity*6;
        else if(hasChocolate) totalPrice = quantity*7;
        else totalPrice = quantity*5;
        return totalPrice;
    }
}
