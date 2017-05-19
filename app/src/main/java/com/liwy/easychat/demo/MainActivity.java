package com.liwy.easychat.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.liwy.easychat.R;
import com.liwy.easychat.callback.RosterMessageListener;
import com.liwy.easychat.chat.ConnectManager;
import com.liwy.easychat.demo.adapter.UserAdapter;
import com.liwy.easychat.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView listView;
    private UserAdapter adapter;
    private List<String> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ConnectManager.getInstance().setRosterMessageListener(new RosterMessageListener() {
            @Override
            public void rosters(final ChatMessage chatMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String content = chatMessage.getContent();
                        updateList(content);
                    }
                });

            }

            @Override
            public void rosterUpdate(final ChatMessage chatMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String content = chatMessage.getContent();
                        updateList(content);
                    }
                });
            }
        });
        ConnectManager.getInstance().getUsers();
    }
    private UserAdapter.OnItemClickListener onItemClickListener = new UserAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String userId) {
            Intent intent = new Intent(MainActivity.this,ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("talkId",userId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    public void initView(){
        listView = (RecyclerView)findViewById(R.id.list_user);
        users = new ArrayList<>();
        adapter = new UserAdapter(this, users,onItemClickListener);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    // 更新好友列表
    public void updateList(String content){
        String[] ids = content.split(",");
        users = new ArrayList<String>();
        for (String s : ids){
            if (!"".equals(s)){
                users.add(s);
            }
        }
        adapter = new UserAdapter(this,users,onItemClickListener);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectManager.getInstance().disconnect();
    }
}
