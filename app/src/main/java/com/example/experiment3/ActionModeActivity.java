package com.example.experiment3;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode);

        setTitle("实验4：ActionMode上下文菜单");

        listView = findViewById(R.id.listViewActionMode);

        dataList = new ArrayList<>();
        dataList.add("苹果 Apple");
        dataList.add("香蕉 Banana");
        dataList.add("橙子 Orange");
        dataList.add("葡萄 Grape");
        dataList.add("西瓜 Watermelon");
        dataList.add("草莓 Strawberry");
        dataList.add("芒果 Mango");
        dataList.add("菠萝 Pineapple");
        dataList.add("樱桃 Cherry");
        dataList.add("桃子 Peach");
        dataList.add("梨 Pear");
        dataList.add("柠檬 Lemon");

        adapter = new ArrayAdapter<>(
                this,
                R.layout.item_simple,
                R.id.tvTitle,
                dataList
        );

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int count = listView.getCheckedItemCount();
                mode.setTitle(getString(R.string.action_mode_title, count));
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_context_action, menu);
                actionMode = mode;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int id = item.getItemId();
                SparseBooleanArray checked = listView.getCheckedItemPositions();

                if (id == R.id.action_delete) {
                    // 删除选中项（从后往前删，避免索引错乱）
                    for (int i = dataList.size() - 1; i >= 0; i--) {
                        if (checked.get(i)) {
                            dataList.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ActionModeActivity.this, "已删除选中项", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;

                } else if (id == R.id.action_share) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (checked.get(i)) {
                            sb.append(dataList.get(i)).append("\n");
                        }
                    }
                    Toast.makeText(ActionModeActivity.this,
                            "分享：\n" + sb.toString(),
                            Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;

                } else if (id == R.id.action_select_all) {
                    boolean allChecked = true;
                    for (int i = 0; i < dataList.size(); i++) {
                        if (!checked.get(i)) {
                            allChecked = false;
                            break;
                        }
                    }
                    if (allChecked) {
                        for (int i = 0; i < dataList.size(); i++) {
                            listView.setItemChecked(i, false);
                        }
                    } else {
                        for (int i = 0; i < dataList.size(); i++) {
                            listView.setItemChecked(i, true);
                        }
                    }
                    return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
            }
        });

        // 单击显示名称
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode == null) {
                    Toast.makeText(ActionModeActivity.this,
                            "点击了：" + dataList.get(position),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
