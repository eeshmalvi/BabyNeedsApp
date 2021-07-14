package com.example.babyneedsapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyneedsapp.data.DataBaseHandler;
import com.example.babyneedsapp.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private EditText babyitem;
    private EditText itemquantity;
    private EditText itemcolor;
    private EditText itemsize;
    private Button save_button;
    private DataBaseHandler dataBaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHandler = new DataBaseHandler(this);

        BypassActivity();

//        List<Item> items = dataBaseHandler.getAllItems();
//        for(Item item:items)
//        {
//            Log.d("least", item.getItemName());
//        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });







    }

    private void BypassActivity() {
    if(dataBaseHandler.getItemCount()>0)
    {
        startActivity(new Intent(MainActivity.this,ListActivity.class));
        finish();
    }

    }

    private void saveItem(View view) {
        //save baby item
        //move to next screen-details screen
        Item item = new Item();

        String newitem = babyitem.getText().toString().trim();
        String newcolor=itemcolor.getText().toString().trim();
        int size = Integer.parseInt(itemsize.getText().toString().trim());
        int quantity = Integer.parseInt(itemquantity.getText().toString().trim());

        item.setItemName(newitem);
        item.setItemcolor(newcolor);
        item.setItemsize(size);
        item.setItemQuantity(quantity);

        dataBaseHandler.additem(item);

        Snackbar.make(view,"item added",Snackbar.LENGTH_SHORT).show();

        //move to next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200 );


    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        babyitem = view.findViewById(R.id.baby_item);
        itemquantity = view.findViewById(R.id.quantity);
        itemcolor = view.findViewById(R.id.color);
        itemsize = view.findViewById(R.id.size);
        save_button = view.findViewById(R.id.save_button);


        builder.setView(view);

        dialog = builder.create();
        dialog.show();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!babyitem.getText().toString().isEmpty()
                && !itemcolor.getText().toString().isEmpty()
                && !itemsize.getText().toString().isEmpty()
                && !itemquantity.getText().toString().isEmpty()){
                    saveItem(v);

                }
                else{
                    Snackbar.make(v,"Empty field not allowed",Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}