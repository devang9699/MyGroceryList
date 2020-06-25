package com.devang.mygrocerylist;

import android.content.Intent;
import android.os.Bundle;

import com.devang.mygrocerylist.data.DatabaseHelper;
import com.devang.mygrocerylist.model.Grocery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText groceryItem,quantity;
    Button submit;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new DatabaseHelper(this,null,null,1);
        bypassActivity();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setUpAlertPopUpView();
           }


        });
    }

    private void setUpAlertPopUpView()
    {

        Button submit;

        dialogBuilder =new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem=view.findViewById(R.id.enterItemET);
        quantity=view.findViewById(R.id.enterQuantityET);
        submit=view.findViewById(R.id.submitBtn);


        dialogBuilder.setView(view);
        dialog=dialogBuilder.create();
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!groceryItem.getText().toString().isEmpty()
                && !quantity.getText().toString().isEmpty())
                {

                    saveTODb();
                }
            }
        });




    }

    private void saveTODb() {
        String name=groceryItem.getText().toString();
        String qty=groceryItem.getText().toString();

        Grocery grocery=new Grocery();
        grocery.setName(name);
        grocery.setQuantity(qty);
        db.addGrocery(grocery);

        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        Log.d("MYAPP","Item added"+ db.getGroceryCount());
        dialog.dismiss();
        startActivity(new Intent(MainActivity.this,ListActivity.class));

    }
    private void bypassActivity()
    {
        if(db.getGroceryCount() > 0)
        {
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }




}