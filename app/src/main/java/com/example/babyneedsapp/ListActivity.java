package com.example.babyneedsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneedsapp.data.DataBaseHandler;
import com.example.babyneedsapp.model.Item;
import com.example.babyneedsapp.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "listactivity";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DataBaseHandler dataBaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText babyitem;
    private EditText itemquantity;
    private EditText itemcolor;
    private EditText itemsize;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);

        dataBaseHandler = new DataBaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList=new ArrayList<>();

        itemList = dataBaseHandler.getAllItems();

//        for(Item item:itemList){
//
//            Log.d(TAG, String.valueOf(item.getItemQuantity()));
//        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });

    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        babyitem = view.findViewById(R.id.baby_item);
        itemquantity = view.findViewById(R.id.quantity);
        itemcolor = view.findViewById(R.id.color);
        itemsize = view.findViewById(R.id.size);
        save_button = view.findViewById(R.id.save_button);

        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();

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

    private void saveItem(View v) {

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

        Snackbar.make(v,"item added",Snackbar.LENGTH_SHORT).show();

        //move to next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();
            }
        },1200 );


    }


}