package com.example.hh.hardiks;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResultsActivity extends AppCompatActivity
{
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ActionBar actionBar=getActionBar();

        actionBar.setHomeButtonEnabled(true);
        tx=(TextView)findViewById(R.id.textQuery);


        handleIntent( getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        handleIntent(intent);

        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query=intent.getStringExtra(SearchManager.QUERY);
            tx.setText("Search Query::"+query);
        }


    }
}
