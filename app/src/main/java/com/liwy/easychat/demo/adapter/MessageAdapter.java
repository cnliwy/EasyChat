package com.liwy.easychat.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liwy.easychat.R;
import com.liwy.easychat.entity.ChatMessage;

import java.util.List;

import static com.liwy.easychat.entity.ChatMessage.DIRECTION_RECEIVE;
import static com.liwy.easychat.entity.ChatMessage.DIRECTION_SEND;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<ChatMessage> mMessages;
    private int[] mUsernameColors;

    public MessageAdapter(Context context, List<ChatMessage> messages) {
        mMessages = messages;
        mUsernameColors = context.getResources().getIntArray(R.array.username_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.item_message;
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ChatMessage message = mMessages.get(position);
        viewHolder.setDirection(message.getDirection());
        viewHolder.setMessage(message.getContent());

        if (message.getDirection() == DIRECTION_RECEIVE){
            viewHolder.setUsername(message.getTalkId());
        }else if (message.getDirection() == DIRECTION_SEND){
            viewHolder.setUsername(message.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getContentType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLeftUsernameView;
        private TextView mLeftMessageView;
        private TextView mRightUsernameView;
        private TextView mRightMessageView;
        private View itemView;
        private int direction;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mLeftUsernameView = (TextView) itemView.findViewById(R.id.left_username);
            mRightUsernameView = (TextView) itemView.findViewById(R.id.right_username);
            mLeftMessageView = (TextView) itemView.findViewById(R.id.left_message);
            mRightMessageView = (TextView) itemView.findViewById(R.id.right_message);
        }

        public void setUsername(String username) {
            if (direction == DIRECTION_RECEIVE){
                mLeftUsernameView.setText(username);
                mLeftUsernameView.setTextColor(getUsernameColor(username));
            }else if (direction == DIRECTION_SEND){
                mRightUsernameView.setText(username);
                mRightUsernameView.setTextColor(getUsernameColor(username));
            }
        }

        public void setMessage(String message) {
            if (null == mLeftMessageView) return;
            if (direction == DIRECTION_RECEIVE){
                mLeftMessageView.setText(message);
            }else if (direction == DIRECTION_SEND){
                mRightMessageView.setText(message);
            }
        }

        private int getUsernameColor(String username) {
            int hash = 7;
            for (int i = 0, len = username.length(); i < len; i++) {
                hash = username.codePointAt(i) + (hash << 5) - hash;
            }
            int index = Math.abs(hash % mUsernameColors.length);
            return mUsernameColors[index];
        }


        public void setDirection(int direction) {
            this.direction = direction;
            if (itemView instanceof LinearLayout){
                if (direction == DIRECTION_RECEIVE){
                    ((LinearLayout) itemView).setGravity(Gravity.LEFT);
                }else if (direction == DIRECTION_SEND){
                    ((LinearLayout) itemView).setGravity(Gravity.RIGHT);
                }
            }

        }
    }
}
