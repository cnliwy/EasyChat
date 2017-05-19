package com.liwy.easychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liwy.easychat.callback.SystemMessageListener;
import com.liwy.easychat.chat.ConnectManager;
import com.liwy.easychat.entity.ChatMessage;

public class LoginActivity extends AppCompatActivity {
    private EditText loginnameEt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginnameEt = (EditText)findViewById(R.id.et_loginname);
        loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = loginnameEt.getText().toString();
                if (!"".equals(userId)){
                    ConnectManager.getInstance().setSystemMessageListener(new SystemMessageListener() {
                        @Override
                        public void loginCallback(ChatMessage chatMessage) {
                            System.out.println(chatMessage.getContent());
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    ConnectManager.getInstance().connectServer("192.168.131.19",8888,userId);
//                    ConnectManager.getInstance().login(userId);
                }
            }
        });

    }
}
