package com.example.rxrecyclerview.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.rxrecyclerview.R;
import com.example.rxrecyclerview.di.logic.DaggerLogicComponent;
import com.example.rxrecyclerview.di.logic.LogicComponent;
import com.example.rxrecyclerview.doamin.GetList;
import javax.inject.Inject;
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    Adapter adapter;

    LogicComponent component;
    @Inject
    GetList getList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        component = DaggerLogicComponent.create();
        component.inject(this);
        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter();
        adapter.setContext(this);

        /*
        RecyclerView Adapter Should Be Set Using A method in domain logic, it takes a recyclerView And an adapter
        notice that neither adapter nor recyclerView should be null. initialize context and recyclerView Then parse then into setAdapter Class;
        */
        recyclerView.setAdapter(getList.setAdapter(adapter , recyclerView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu , menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}