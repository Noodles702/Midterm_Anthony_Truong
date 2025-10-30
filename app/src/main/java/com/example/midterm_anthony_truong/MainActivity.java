package com.example.midterm_anthony_truong;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText enterNumb;
    private Button calcBtn;
    private ListView listResults;

    private final ArrayList<String> results = new ArrayList<>();
    private ArrayAdapter<String> resultsAdapter;

    private static final String KEY_RESULTS = "results";
    private static final String KEY_INPUT   = "input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enterNumb   = findViewById(R.id.enterNumb);
        calcBtn     = findViewById(R.id.calcBtn);
        listResults = findViewById(R.id.listResults);

        resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        listResults.setAdapter(resultsAdapter);

        if (savedInstanceState != null) {
            ArrayList<String> restored = savedInstanceState.getStringArrayList(KEY_RESULTS);
            if (restored != null) {
                results.clear();
                results.addAll(restored);
                resultsAdapter.notifyDataSetChanged();
            }
            enterNumb.setText(savedInstanceState.getString(KEY_INPUT, ""));
        }

        calcBtn.setOnClickListener(v -> generateTable());

        listResults.setOnItemClickListener((parent, view, position, id) -> {
            String row = results.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete row?")
                    .setMessage("Remove: " + row + " ?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        results.remove(position);
                        resultsAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + row, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void generateTable() {
        String text = enterNumb.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            enterNumb.setError("Enter a number");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            enterNumb.setError("Invalid number");
            return;
        }

        results.clear();
        for (int i = 1; i <= 10; i++) {
            results.add(n + " × " + i + " = " + (n * i));
        }
        resultsAdapter.notifyDataSetChanged();
        TimesTables.addNumber(n);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.historyAction) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (id == R.id.clearHist) {
            if (results.isEmpty()) {
                Toast.makeText(this, "Nothing to clear.", Toast.LENGTH_SHORT).show();
                return true;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Clear all?")
                    .setMessage("Delete every row from the current list?")
                    .setPositiveButton("Clear", (d, w) -> {
                        results.clear();
                        resultsAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "All rows cleared.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_RESULTS, new ArrayList<>(results));
        outState.putString(KEY_INPUT, enterNumb.getText().toString());
    }
}
