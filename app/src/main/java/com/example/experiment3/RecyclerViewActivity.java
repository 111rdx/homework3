package com.example.experiment3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> animalList;
    private int addCount = 0;

    private int[] imageIds = {
            R.drawable.elephant,
            R.drawable.tiger,
            R.drawable.lion,
            R.drawable.monkey,
            R.drawable.dog,
            R.drawable.cat
    };

    private String[] names = {
            "大象", "老虎", "狮子", "猴子", "狗", "猫"
    };

    private String[] descs = {
            "陆地上最大的哺乳动物",
            "百兽之王，森林中的霸主",
            "草原之王，群居动物",
            "聪明伶俐，善于攀爬",
            "人类最忠实的朋友",
            "优雅神秘的喵星人"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        setTitle("实验5：RecyclerView");

        recyclerView = findViewById(R.id.recyclerView);
        Button btnAdd = findViewById(R.id.btnAdd);

        // 初始化数据
        animalList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            animalList.add(new Animal(names[i], descs[i], imageIds[i]));
        }

        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnimalAdapter(animalList);
        recyclerView.setAdapter(adapter);

        // 点击事件
        adapter.setOnItemClickListener(new AnimalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Animal animal = animalList.get(position);
                Toast.makeText(RecyclerViewActivity.this,
                        "点击了：" + animal.getName() + "\n" + animal.getDescription(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 添加按钮
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCount++;
                int index = addCount % names.length;
                String newName = names[index] + " (新" + addCount + ")";
                String newDesc = descs[index] + " - 新增第" + addCount + "只";
                int imgId = imageIds[index];

                // 在列表头部添加
                animalList.add(0, new Animal(newName, newDesc, imgId));
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);

                Toast.makeText(RecyclerViewActivity.this,
                        "已添加：" + newName,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
