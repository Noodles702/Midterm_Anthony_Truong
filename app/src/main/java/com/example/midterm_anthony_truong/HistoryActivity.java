package com.example.midterm_anthony_truong;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("History");
        }

        ListView list = findViewById(R.id.historyList);
        list.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                TimesTables.getNumbers()
        ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // EITHER of these is fine. Choose one:

            // Option A (simplest):
            finish();

            // Option B (if you prefer the dispatcher API):
            // getOnBackPressedDispatcher().onBackPressed();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
