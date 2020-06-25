package com.devang.mygrocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView groceryName,quantity,date;
    Button editButton,deleteButton;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        groceryName=findViewById(R.id.groceryNameDet);
        quantity=findViewById(R.id.quantityDet);
        date=findViewById(R.id.dateAddedDet);


        Bundle bundle=getIntent().getExtras();
        groceryName.setText(bundle.getString("name"));
        quantity.setText(bundle.getString("quantity"));
        date.setText(bundle.getString("date"));
        id=bundle.getInt("id");





    }
}