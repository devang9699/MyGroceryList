package com.devang.mygrocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devang.mygrocerylist.data.DatabaseHelper;
import com.devang.mygrocerylist.model.Grocery;

import java.util.List;
import java.util.zip.Inflater;

class RecyleViewAdapter extends RecyclerView.Adapter<RecyleViewAdapter.ViewHolder> {

    List<Grocery> mGroceryList;
    Context mContext;

    public RecyleViewAdapter(List<Grocery> groceryList, Context context) {
        mGroceryList = groceryList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery=mGroceryList.get(position);

        holder.groceryName.setText(grocery.getName());
        holder.groceryQty.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemAdded());

    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView groceryName,groceryQty,dateAdded;
        Button editButton,deleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryName=itemView.findViewById(R.id.groceryNameDet);
            groceryQty=itemView.findViewById(R.id.quantityDet);
            dateAdded=itemView.findViewById(R.id.dateAddedDet);


            editButton=itemView.findViewById(R.id.editButtonDet);
            deleteButton=itemView.findViewById(R.id.deleteButtonDet);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Grocery grocery=mGroceryList.get(position);
                    Intent intent=new Intent(mContext,DetailsActivity.class);
                    intent.putExtra("name",grocery.getName());
                    intent.putExtra("quantity",grocery.getQuantity());
                    intent.putExtra("date",grocery.getDateItemAdded());
                    intent.putExtra("id",grocery.getId());

                    mContext.startActivity(intent);

                }
            });




        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.editButtonDet)
            {
                //Edit code
                int position =getAdapterPosition();
                Grocery grocery=mGroceryList.get(position);
                editItem(grocery);
            }
            else if(view.getId() == R.id.deleteButtonDet)
            {
                //delete code
                int position=getAdapterPosition();
                Grocery grocery=mGroceryList.get(position);
              deleteItem(grocery.getId());
            }


        }
        private void deleteItem(final int id)
        {
            final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

            builder.setTitle("Are you sure");
            builder.setMessage("Are you sure you want to delete this item");



           builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //delete finally
                    DatabaseHelper db=new DatabaseHelper(mContext,null,null,1);
                    db.deleteGrocery(id);
                    mGroceryList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());


                }
            });
            builder.setNegativeButton("No",null);
            builder.show();


        }
        private void editItem(final Grocery grocery)
        {

           AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(mContext);
           LayoutInflater inflater=LayoutInflater.from(mContext);
           View view= inflater.inflate(R.layout.popup,null);
           final EditText groceryItem=view.findViewById(R.id.enterItemET);
           final EditText  quantity=view.findViewById(R.id.enterQuantityET);
           TextView textView=view.findViewById(R.id.enterItemTV);
           textView.setText("Update Grocery");
            Button submit=view.findViewById(R.id.submitBtn);


            dialogBuilder.setView(view);
           final AlertDialog dialog=dialogBuilder.create();
            dialog.show();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseHelper db=new DatabaseHelper(mContext,null,null,1);

                    //update item
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQuantity(quantity.getText().toString());

                    if(!groceryItem.getText().toString().isEmpty()
                            && !quantity.getText().toString().isEmpty())
                    {

                        db.updateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),grocery);
                        dialog.dismiss();
                    }
                }
            });



        }
    }
}
