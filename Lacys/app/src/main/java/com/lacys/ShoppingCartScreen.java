package com.lacys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Christina on 3/9/2015.
 */
public class ShoppingCartScreen extends ActionBarActivity {
    private static DBAdapter db;
    private static System system;
    private static TextView total;
    private static double totalPrice = 0;

    private static SimpleCursorAdapter shoppingCartAdapter;

    //these change as soon as a shipping speed is clicked so that total
    //can be automatically updated. estArrival and shippingCost are
    //also used to make the shipping object. Static because they are
    //called by method in anonymous inner class OnItemClickListener
    private static ListView shippingListView;
    public static Calendar estArrival;
    public static double shippingCost;
    private static Context cartContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_cart_screen);

        cartContext = this.getApplicationContext();


        //total is updated and estArrival and shippingCost change when
        //a speed is selected.
        shippingListView = (ListView) findViewById(R.id.shipping_speed_list);
        shippingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingCartScreen.changeShippingSpeed(position);
            }
        });

        db = new DBAdapter(this);
        db.init();

        system = System.getInstance();

        final int userID = system.getUserID();
        if (userID != 0) {
            //Obtains the items in cart based on the userid that is logged in
            Cursor cs = db.getShoppingCartData(userID);

            //Simple Cursor Adapter to fill up the listview
            shoppingCartAdapter = new SimpleCursorAdapter(this,
                    R.layout.shopping_cart_adapter,
                    cs,
                    new String[]{LacyConstants.TABLE_PRODUCT_NAME,
                            LacyConstants.TABLE_PRODUCT_PRICE,
                            LacyConstants.TABLE_PRODUCT_DISCOUNT,
                            LacyConstants.TABLE_PRODUCT_IMGINDEX,
                            LacyConstants.TABLE_PRODUCTORDER_QUANTITY,
                            LacyConstants.TABLE_PRODUCTORDER_ALREADYCHECKEDOUT,
                    },
                    new int[]{R.id.shopping_cart_item_name,
                            R.id.new_price_shopping_cart,
                            R.id.old_price_shopping_cart,
                            R.id.shopping_cart_item,
                            R.id.quantity_spinner,
                            R.id.shopping_cart_remove_button,
                    });

            totalPrice = db.getShoppingCartTotal(userID);
            total = (TextView) findViewById(R.id.shopping_cart_total_price);


            //Update the listview contents with database values
            shoppingCartAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int column) {
                    final int orderid = cursor.getInt(cursor.getColumnIndex(LacyConstants.TABLE_ORDERS_ID));
                    //If the column index matches the column for product name
                    if (column == cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME)) {
                        TextView tv = (TextView) view;
                        String name = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_NAME));
                        String size = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_SIZE));
                        String color = cursor.getString(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCTORDER_COLOR));

                        //Update the name to add the color and size of item
                        tv.setText(name + "\nColor: " + color + "\nSize: " + size + "\n");
                    }
                    //If the view id matches the old price id textview
                    else if (view.getId() == R.id.old_price_shopping_cart) {
                        TextView tv = (TextView) view;
                        double oldprice = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                        //Update the old price
                        tv.setText("$" + oldprice + "0");
                    }
                    //If the view id matches the new price id textview
                    else if (view.getId() == R.id.new_price_shopping_cart) {
                        TextView tv = (TextView) view;
                        double oldprice = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_PRICE));
                        double discount = cursor.getDouble(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_DISCOUNT));

                        double newprice = oldprice - (oldprice * discount);
                        tv.setText("$" + newprice + "0");
                    }
                    //If the view id matches the shopping cat item image id
                    else if (view.getId() == R.id.shopping_cart_item) {
                        ImageView imv = (ImageView) view;

                        int productImgIndex = cursor.getInt(cursor.getColumnIndex(LacyConstants.TABLE_PRODUCT_IMGINDEX));
                        //Update to show the correct image
                        if (productImgIndex < ImageAdapter.images.length)
                            imv.setImageResource(ImageAdapter.images[productImgIndex]);
                        else
                            imv.setImageResource(ImageAdapter.images[0]);
                    }
                    //If the view id matches the quantity_spinner
                    else if (view.getId() == R.id.quantity_spinner) {
                        Spinner qty = (Spinner) view;
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<CharSequence> quantitySpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                                R.array.shopping_cart_quantity_choices, android.R.layout.simple_spinner_item);

                        // Specify the layout to use when the list of choices appears
                        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Apply the adapter to the spinner
                        final int currentSelection = qty.getSelectedItemPosition();
                        qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                if (currentSelection != position) {
                                    //Position starts at index 0, so add 1 to get the correct quantity
                                    int quantity = (position + 1);
                                    //Get the individual item's price from database
                                    double itemPrice = db.getPrice(orderid);
                                    //Get the individual item's quantity from database
                                    int dbQty = db.getQuantity(orderid);
                                    double tp = 0;
                                    //Determine whether the quantity increased or decreased to get the new price
                                    if (dbQty > quantity) {
                                        int diff = dbQty - quantity;
                                        tp = totalPrice - (diff * itemPrice);
                                    } else {
                                        int diff = quantity - dbQty;
                                        tp = totalPrice + (diff * itemPrice);
                                    }
                                    //Update database to set the quantity
                                    db.updateProductQuantity(orderid, quantity);
                                    totalPrice = tp;
                                    //Update the total text view with the new price
                                    total.setText("$" + totalPrice + "0");
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                return;
                            }
                        });
                        qty.setAdapter(quantitySpinnerAdapter);
                    }
                    //ID matches remove button
                    else if (view.getId() == R.id.shopping_cart_remove_button) {
                        Button btn = (Button) view;
                        //Add button listener
                        btn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                int userID = system.getUserID();
                                if (userID != 0) {
                                    //Remove shopping cart item, sets the "alreadycheckedout" true, thus removing it from the adapter
                                    db.removeShoppingCartItem(orderid);
                                    Toast.makeText(getApplicationContext(), "Item removed from your shopping cart", Toast.LENGTH_SHORT).show();
                                    //The update adapter view wasn't working so this is what I did it instead, will close this activity then open it again
                                    //I think this works better anyways because if the cart is empty it wont show the screen with the empty adapter.
                                    finish();
                                    //Checks if the cart isn't empty before re-opening cart screen
                                    if (db.getShoppingCartData(userID) == null)
                                        Toast.makeText(getApplicationContext(), "Your shopping cart is empty.", Toast.LENGTH_SHORT).show();
                                    else
                                        startActivity(new Intent(getApplicationContext(), ShoppingCartScreen.class));
                                }
                            }
                        });
                    }
                    return true;
                }
            });


            // Get the ListView so we can work with it
            ListView shoppingCartItems = (ListView) findViewById(R.id.shopping_cart_item_list);

            // Connect the ListView with the Adapter that acts as a bridge between it and the array
            shoppingCartItems.setAdapter(shoppingCartAdapter);


            String[] shippingSpeeds = {"7-9 business days - FREE for orders over $50",
                    "3-5 business days - $7.00", "2 business days - $16.00"};

            ListAdapter shippingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shippingSpeeds);

            ListView shippingListView = (ListView) findViewById(R.id.shipping_speed_list);

            shippingListView.setAdapter(shippingAdapter);


            total.setText("$" + totalPrice + "0");
        }
        //
    }


    public void onCheckOutClick(View view) {

        //check to make sure a shipping speed is selected

        if (ShoppingCartScreen.estArrival != null) {
            //make shipping object
            Shipping shipping = new Shipping(estArrival, shippingCost);

            //set shipping object in Singleton System
            system.setShippingForNewOrder(shipping);

            //go to shipping screen
            startActivity(new Intent(this, ShippingAddressScreen.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please select a shipping speed.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void displayEstArrivalInToast(Calendar calendar) {
        //months in Calendar start at zero
        int month = calendar.get(Calendar.MONTH) + 1;
        String formattedDate = month + "-" +
                calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR);

        Toast.makeText(ShoppingCartScreen.cartContext, "Estimated arrival: " + formattedDate, Toast.LENGTH_SHORT).show();
    }

    public static void changeShippingSpeed(int position) {
        int daysToArrive = 0;
        ShoppingCartScreen.estArrival = Calendar.getInstance();
        int userID = system.getUserID();
        double newShippingCost = db.getShoppingCartTotal(userID);

        if (position == 0) {
            daysToArrive = 8;

            ShoppingCartScreen.shippingCost = 0.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
            newShippingCost = ShoppingCartScreen.shippingCost + newShippingCost;
            total.setText("$" + newShippingCost + "0");
        } else if (position == 1) {
            daysToArrive = 4;
            ShoppingCartScreen.shippingCost = 7.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
            newShippingCost = ShoppingCartScreen.shippingCost + newShippingCost;
            total.setText("$" + newShippingCost + "0");
        } else if (position == 2) {
            daysToArrive = 2;
            ShoppingCartScreen.shippingCost = 16.0;
            ShoppingCartScreen.estArrival.add(Calendar.DAY_OF_MONTH, daysToArrive);
            ShoppingCartScreen.displayEstArrivalInToast(estArrival);
            newShippingCost = ShoppingCartScreen.shippingCost + newShippingCost;
            total.setText("$" + newShippingCost + "0");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.contact_us:
                startActivity(new Intent(ShoppingCartScreen.this, ContactUs.class));
                return true;
            case R.id.action_settings:
                return true;
            case R.id.home:
                startActivity(new Intent(ShoppingCartScreen.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
