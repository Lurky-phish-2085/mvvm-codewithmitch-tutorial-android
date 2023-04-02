package xyz.lurkyphish2085.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import xyz.lurkyphish2085.myapplication.adapters.RecyclerAdapter;
import xyz.lurkyphish2085.myapplication.models.NicePlace;

public class MainActivity extends AppCompatActivity {

    // ToDo: continue working on the MainActivity
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progress_bar);

        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new RecyclerAdapter(this, new ArrayList<NicePlace>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }
}