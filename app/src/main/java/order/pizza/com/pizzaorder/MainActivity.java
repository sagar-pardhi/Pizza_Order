package order.pizza.com.pizzaorder;

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
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inc(View view) {
        if (quantity == 50) {
            Toast.makeText(this, "You cannot have More than 50 Pizzas", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void dec(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 Pizzas", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    public void order(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String valueName = name.getText().toString();

        EditText address = (EditText) findViewById(R.id.address);
        String valueAddress = address.getText().toString();

        EditText number = (EditText) findViewById(R.id.number);
        String valueNumber = number.getText().toString();

        CheckBox cheese = (CheckBox) findViewById(R.id.cheese);
        boolean hasCheese = cheese.isChecked();

        CheckBox panner = (CheckBox) findViewById(R.id.panner);
        boolean hasPanner = panner.isChecked();

        CheckBox vegie = (CheckBox) findViewById(R.id.vegie);
        boolean hasVegie = vegie.isChecked();

        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        boolean hasChicken = chicken.isChecked();

        int price = calculatePrice(hasCheese, hasPanner, hasVegie, hasChicken);
        String priceMessage = orderSummary(price, hasCheese, hasPanner, hasVegie, hasChicken, valueName, valueAddress, valueNumber);


        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:")); // main method not to forget
        i.putExtra(Intent.EXTRA_SUBJECT, "Pizza Order for " + valueName);
        i.putExtra(Intent.EXTRA_TEXT, priceMessage);

        /*This method is used for material EditText to show error
        if the below fields are empty.
         */

        int userName = name.getText().toString().trim().length();
        int userAddress = address.getText().toString().trim().length();
        int userNumber = number.getText().toString().trim().length();

        if (userName == 0 || userAddress == 0 || userNumber == 0){
            if (userName == 0){
                name.setError("This field cannot be empty");
            }
            if (userAddress == 0){
                address.setError("This field cannot be empty");
            }
            if (userNumber == 0){
                number.setError("This field cannot be empty");
            }
        }
        else if ( i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
            Toast.makeText(this, "Order Successful",Toast.LENGTH_SHORT).show();
        }
        else {
         //TODO NOTHING
        }

    }

    /*This method calculate the price of pizza as per toppings and add them
    in the final amount.
     */
    private int calculatePrice(boolean Cheese, boolean Panner, boolean Vegie, boolean Chicken) {
        int basePrice = 50;
        if (Cheese) {
            basePrice = basePrice + 10;
        }
        if (Panner) {
            basePrice = basePrice + 7;
        }
        if (Vegie) {
            basePrice = basePrice + 5;
        }
        if (Chicken) {
            basePrice = basePrice + 20;
        }
        return quantity * basePrice;
    }

    private String orderSummary(int price, boolean addcheese, boolean addpanner, boolean addvegie, boolean addchicken, String personName, String personAddress, String personNumber) {
        String priceMessage = "Name: " + personName;
        priceMessage += "\nAddress: " + personAddress;
        priceMessage += "\nPhone Number: " + personNumber;
        priceMessage += "\nAdd Cheese: " + addcheese;
        priceMessage += "\nAdd Panner: " + addpanner;
        priceMessage += "\nAdd Vegie: " + addvegie;
        priceMessage += "\nAdd Chicken: " + addchicken;
        priceMessage += "\nTotal Ruppees: " + price;
        priceMessage += "\nThank You !!!";
        return priceMessage;
    }

    public void display(int num) {
        TextView tv = (TextView) findViewById(R.id.quantity_text_view);
        tv.setText("" + num);
    }


}

