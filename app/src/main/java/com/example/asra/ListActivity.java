package com.example.asra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import com.example.asra.SQL.ServicesDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.ServicesRecyclerAdapter;

public class ListActivity extends AppCompatActivity{
    private static Context mContext;

    private FloatingActionButton fab;
    private final AppCompatActivity activity = ListActivity.this;
    private RecyclerView recyclerViewServices;
    private ServicesRecyclerAdapter servicesRecyclerAdapter;
    private ServicesDatabaseHelper servicesDatabaseHelper;
    private List<Services> listService;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mContext = getApplicationContext();

        fab = findViewById(R.id.floating_action_button);
        fab.bringToFront();
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentCreate = new Intent(ListActivity.this, CreateService.class);
                        startActivity(intentCreate);
                        finish();
                    }
                });

        createRecyclerAdapter();
        setupRecyclerAdapter();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ASRA");
        }

    }

    private void createRecyclerAdapter() {
        listService = new ArrayList<>();
        servicesDatabaseHelper = new ServicesDatabaseHelper(activity);
        getDataFromSQLite();

        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        servicesRecyclerAdapter = new ServicesRecyclerAdapter(ListActivity.this, listService);
    }

    private void setupRecyclerAdapter() {
        recyclerViewServices.setAdapter(servicesRecyclerAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListActivity.this);
        recyclerViewServices.setLayoutManager(mLayoutManager);
        recyclerViewServices.setItemAnimator(new DefaultItemAnimator());
        recyclerViewServices.setHasFixedSize(true);

        if(servicesDatabaseHelper == null){
            Toast.makeText(this, "No data to show! Create a service!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,CreateService.class);
            startActivity(intent);
            finish();
        }
    }

    private void getDataFromSQLite() {
        listService = servicesDatabaseHelper.getAllServices();
        Log.v(ListActivity.class.getSimpleName(), "" + listService.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                Log.v(ListActivity.class.getSimpleName(), "" + listService.size());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                servicesRecyclerAdapter.getFilter().filter(newText);
                Log.v(ListActivity.class.getSimpleName(), "" + listService.size());
                return false;
            }
        });
        return true;
    }
}
