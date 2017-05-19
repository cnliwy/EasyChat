package com.liwy.easychat.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.liwy.easychat.R;
import com.liwy.easychat.callback.ChatMessageListener;
import com.liwy.easychat.chat.ConnectManager;
import com.liwy.easychat.demo.adapter.MessageAdapter;
import com.liwy.easychat.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatMessageListener {
    private static final String TAG = "MainFragment";

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<ChatMessage> mMessages = new ArrayList<ChatMessage>();
    private RecyclerView.Adapter mAdapter;
    private String talkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        talkId = getIntent().getExtras().getString("talkId");
        initView();
    }

    private void initView(){
        mAdapter = new MessageAdapter(this, mMessages);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) findViewById(R.id.message_input);
        ConnectManager.getInstance().setChatMessageListener(this);

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mInputMessageView.getText().toString());

            }
        });
    }

    /**
     * 新消息监听
     * @param chatMessage
     */
    @Override
    public void messageArrived(final ChatMessage chatMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessages.add(chatMessage);
                mAdapter.notifyItemInserted(mMessages.size() - 1);
                scrollToBottom();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leave();
    }

    private void leave() {
        ConnectManager.getInstance().setChatMessageListener(null);
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void sendMessage(String content){
        ChatMessage chatMessage = ConnectManager.getInstance().sendMessage(content,talkId);
        messageArrived(chatMessage);
        mInputMessageView.setText("");
    }
}
