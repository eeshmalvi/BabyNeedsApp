package com.example.babyneedsapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyneedsapp.R;
import com.example.babyneedsapp.data.DataBaseHandler;
import com.example.babyneedsapp.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context, List<Item> itemList){
    this.context = context;
    this.itemList=itemList;

    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);


        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
    Item item=itemList.get(position);
    holder.listitemName.setText( "ITEM NAME : "+item.getItemName());
    holder.listitemColor.setText( "ITEM COLOR : "+item.getItemcolor());
    holder.listitemquantity.setText("QUANTITY : "+String.valueOf(item.getItemQuantity()));
    holder.listitemsize.setText("SIZE : "+String.valueOf(item.getItemQuantity()));
    holder.listitemdateAdded.setText("ADDED ON : "+item.getDateItemAdded());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView listitemName;;
        public TextView listitemColor;
        public TextView listitemquantity;
        public TextView listitemsize;
        public TextView listitemdateAdded;
        public Button editbutton;
        public Button deletebutton;
        public int id;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);

            context=ctx;
            listitemName = itemView.findViewById(R.id.item_name);
            listitemColor = itemView.findViewById(R.id.item_color);
            listitemquantity = itemView.findViewById(R.id.item_quantity);
            listitemsize = itemView.findViewById(R.id.item_size);
            listitemdateAdded = itemView.findViewById(R.id.item_date);
            editbutton = itemView.findViewById(R.id.editbutton);
            deletebutton = itemView.findViewById(R.id.deletebutton);

            editbutton.setOnClickListener(this);
            deletebutton.setOnClickListener(this);





        }

        @Override
        public void onClick(View v) {
            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);
            switch (v.getId()){
                case R.id.editbutton:

                    editItem(item);
                    break;

                case R.id.deletebutton:


                    deleteItem(item.getId());
                    break;

            }

        }

        private void editItem(Item newitem) {

//             newitem = itemList.get(getAdapterPosition());


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.popup,null);

             EditText babyitem;
             EditText itemquantity;
             EditText itemcolor;
             EditText itemsize;
             Button save_button;
             TextView title;

            babyitem = view.findViewById(R.id.baby_item);
            itemquantity = view.findViewById(R.id.quantity);
            itemcolor = view.findViewById(R.id.color);
            itemsize = view.findViewById(R.id.size);
            save_button = view.findViewById(R.id.save_button);
            title = view.findViewById(R.id.title);

            save_button.setText(R.string.UPDATE);

            title.setText("EDIT ITEM");
            babyitem.setText(newitem.getItemName());
            itemquantity.setText(String.valueOf(newitem.getItemQuantity()));
            itemcolor.setText(newitem.getItemcolor());
            itemsize.setText(String.valueOf(newitem.getItemsize()));

            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    //update items
                    newitem.setItemName(babyitem.getText().toString());
                    newitem.setItemcolor(itemcolor.getText().toString());
                    newitem.setItemQuantity(Integer.parseInt(itemquantity.getText().toString()));
                    newitem.setItemsize(Integer.parseInt(itemsize.getText().toString()));

                    if(!babyitem.getText().toString().isEmpty()
                            && !itemcolor.getText().toString().isEmpty()
                            && !itemsize.getText().toString().isEmpty()
                            && !itemquantity.getText().toString().isEmpty()) {

                        dataBaseHandler.update(newitem);
                        notifyItemChanged(getAdapterPosition(),newitem);
                        dialog.dismiss();

                    }
                    else{
                        Snackbar.make(v,"Empty field not allowed",Snackbar.LENGTH_SHORT)
                                .show();
                    }


                }
            });


        }

        private void deleteItem(int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop,null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });





        }
    }


}
