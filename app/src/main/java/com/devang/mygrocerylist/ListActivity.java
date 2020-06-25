package com.devang.mygrocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.devang.mygrocerylist.data.DatabaseHelper;
import com.devang.mygrocerylist.model.Grocery;
import com.devang.mygrocerylist.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Grocery> mGroceryList;
    RecyleViewAdapter mRecyleViewAdapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db=new DatabaseHelper(this,null,null,1);

        mRecyclerView=findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mGroceryList=db.getAllGroceries();

        List<Grocery> listitems=new ArrayList();

        for(Grocery g:mGroceryList)
        {
            Grocery grocery=new Grocery();
            grocery.setName(g.getName());
            grocery.setQuantity(g.getQuantity());
            grocery.setDateItemAdded(g.getDateItemAdded());
            grocery.setId(g.getId());

            listitems.add(grocery);
        }

        mRecyleViewAdapter = new RecyleViewAdapter(listitems,this);
        mRecyclerView.setAdapter(mRecyleViewAdapter);
       mRecyleViewAdapter.notifyDataSetChanged();



    }
}