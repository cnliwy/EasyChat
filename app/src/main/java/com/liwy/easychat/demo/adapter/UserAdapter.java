package com.liwy.easychat.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liwy.easychat.R;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by liwy on 2017/5/19.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<String> users;
    private Context context;
    private int[] mUsernameColors;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(Context context ,List<String> users,OnItemClickListener onItemClickListener) {
        this.users = users;
        this.context = context;
        mUsernameColors = context.getResources().getIntArray(R.array.username_colors);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(v,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String userName = users.get(position);
        holder.setUsername(userName);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(String userId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mUsernameView;
        private View itemView;
        private String username;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            this.itemView = itemView;
            this.onItemClickListener = onItemClickListener;
            mUsernameView = (TextView) itemView.findViewById(R.id.tv_username);
        }

        public void setUsername(final String username) {
            if (null == mUsernameView) return;
            this.username = username;
            mUsernameView.setText(username);
            mUsernameView.setTextColor(getUsernameColor(username));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)onItemClickListener.onItemClick(username);
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) onItemClickListener.onItemClick(username);
        }

        private int getUsernameColor(String username) {
            int hash = 7;
            for (int i = 0, len = username.length(); i < len; i++) {
                hash = username.codePointAt(i) + (hash << 5) - hash;
            }
            int index = Math.abs(hash % mUsernameColors.length);
            return mUsernameColors[index];
        }
    }
}
