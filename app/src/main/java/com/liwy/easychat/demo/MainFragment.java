package com.liwy.easychat.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.liwy.easychat.R;
import com.liwy.easychat.callback.ChatMessageListener;
import com.liwy.easychat.chat.ConnectManager;
import com.liwy.easychat.demo.adapter.MessageAdapter;
import com.liwy.easychat.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * A chat fragment containing messages view and input form.
 */
public class MainFragment extends Fragment implements ChatMessageListener{

    private static final String TAG = "MainFragment";

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<ChatMessage> mMessages = new ArrayList<ChatMessage>();
    private RecyclerView.Adapter mAdapter;

    public MainFragment() {
        super();
    }


    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(context, mMessages);
        if (context instanceof Activity){
            //this.listener = (MainActivity) context;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
        ConnectManager.getInstance().setChatMessageListener(this);

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
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
    public void messageArrived(ChatMessage chatMessage) {
        mMessages.add(chatMessage);
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        leave();
    }

    private void leave() {
        ConnectManager.getInstance().setChatMessageListener(null);
        ConnectManager.getInstance().disconnect();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void sendMessage(String content){
//        ConnectManager.getInstance().sendMessage(content);
    }
}

