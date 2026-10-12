package com.example.experiment3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "animal_channel";
    private static final int NOTIFICATION_ID = 1;

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
        setContentView(R.layout.activity_list_view);

        setTitle("实验1：ListView + SimpleAdapter");

        ListView listView = findViewById(R.id.listView);

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imageIds[i]);
            map.put("name", names[i]);
            map.put("desc", descs[i]);
            data.add(map);
        }

        String[] from = {"image", "name", "desc"};
        int[] to = {R.id.ivAnimal, R.id.tvName, R.id.tvDesc};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_animal,
                from,
                to
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                String name = (String) item.get("name");
                String desc = (String) item.get("desc");

                // 显示Toast
                Toast.makeText(ListViewActivity.this,
                        "您选中了：" + name + " - " + desc,
                        Toast.LENGTH_SHORT).show();

                // 发送通知
                sendNotification(name, desc);
            }
        });

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "动物通知";
            String description = "点击列表项时的通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification(String title, String content) {
        Intent intent = new Intent(this, ListViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText("您点击了动物：" + content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("您点击了动物列表中的【" + title + "】\n" + content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        try {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } catch (SecurityException e) {
            Toast.makeText(this, "通知权限未开启", Toast.LENGTH_SHORT).show();
        }
    }
}
