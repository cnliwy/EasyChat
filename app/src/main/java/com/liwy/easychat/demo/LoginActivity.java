package com.liwy.easychat.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liwy.easychat.R;
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
        final SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String userName = sharedPreferences.getString("username","");
        if(!"".equals(userName)){
            loginnameEt.setText(userName);
        }
        loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userId = loginnameEt.getText().toString();
                if (!"".equals(userId)){
                    ConnectManager.getInstance().setSystemMessageListener(new SystemMessageListener() {
                        @Override
                        public void loginCallback(ChatMessage chatMessage) {
                            SharedPreferences.Editor editor  = sharedPreferences.edit();
                            editor.putString("username",userId);
                            editor.commit();
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
