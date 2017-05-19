package com.liwy.easychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.liwy.easychat.callback.RosterMessageListener;
import com.liwy.easychat.chat.ConnectManager;
import com.liwy.easychat.entity.ChatMessage;

public class MainActivity extends AppCompatActivity {
    private TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentTv = (TextView)findViewById(R.id.tv_content);
        ConnectManager.getInstance().getUsers();
        ConnectManager.getInstance().setRosterMessageListener(new RosterMessageListener() {
            @Override
            public void rosters(final ChatMessage chatMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contentTv.setText(chatMessage.getContent());
                    }
                });

            }

            @Override
            public void rosterUpdate(final ChatMessage chatMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contentTv.setText(chatMessage.getContent());
                    }
                });
            }
        });
    }
}
