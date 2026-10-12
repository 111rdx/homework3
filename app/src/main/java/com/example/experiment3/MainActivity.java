package com.example.experiment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExp1 = findViewById(R.id.btnExp1);
        Button btnExp2 = findViewById(R.id.btnExp2);
        Button btnExp3 = findViewById(R.id.btnExp3);
        Button btnExp4 = findViewById(R.id.btnExp4);
        Button btnExp5 = findViewById(R.id.btnExp5);

        btnExp1.setOnClickListener(this);
        btnExp2.setOnClickListener(this);
        btnExp3.setOnClickListener(this);
        btnExp4.setOnClickListener(this);
        btnExp5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.btnExp1) {
            intent = new Intent(this, ListViewActivity.class);
        } else if (id == R.id.btnExp2) {
            intent = new Intent(this, AlertDialogActivity.class);
        } else if (id == R.id.btnExp3) {
            intent = new Intent(this, MenuActivity.class);
        } else if (id == R.id.btnExp4) {
            intent = new Intent(this, ActionModeActivity.class);
        } else if (id == R.id.btnExp5) {
            intent = new Intent(this, RecyclerViewActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
