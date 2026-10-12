package com.example.experiment3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("实验3：XML定义菜单");

        tvTest = findViewById(R.id.tvTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 字体大小
        if (id == R.id.menu_font_small) {
            tvTest.setTextSize(10f);
            Toast.makeText(this, "字体大小：小 (10号)", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_font_medium) {
            tvTest.setTextSize(16f);
            Toast.makeText(this, "字体大小：中 (16号)", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_font_large) {
            tvTest.setTextSize(20f);
            Toast.makeText(this, "字体大小：大 (20号)", Toast.LENGTH_SHORT).show();
            return true;
        }

        // 普通菜单项
        if (id == R.id.menu_normal) {
            Toast.makeText(this, R.string.menu_normal_toast, Toast.LENGTH_SHORT).show();
            return true;
        }

        // 字体颜色
        if (id == R.id.menu_color_red) {
            tvTest.setTextColor(Color.RED);
            Toast.makeText(this, "字体颜色：红色", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_color_black) {
            tvTest.setTextColor(Color.BLACK);
            Toast.makeText(this, "字体颜色：黑色", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
