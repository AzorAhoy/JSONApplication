package com.example.jsonapplication;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class CustomIconLabelAdapter extends BaseAdapter {
    List<itemModel> mails;
    Context context;

    CustomIconLabelAdapter(List<itemModel> mails, Context context) {
        this.mails = mails;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mails.size();
    }

    @Override
    public Object getItem(int position) {
        return mails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (view == null) {
            view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_row_icon_label, null);

            viewHolder = new MyViewHolder();
            viewHolder.username = view.findViewById(R.id.username);
            viewHolder.title = view.findViewById(R.id.title);
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.avatar = view.findViewById(R.id.avatar);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.checkBox = view.findViewById(R.id.checkbox);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder)view.getTag();
        }

        final itemModel mail = mails.get(position);
        viewHolder.username.setText(mail.getUsername());
        viewHolder.title.setText(mail.getName());
        viewHolder.content.setText(mail.getEmail());
        viewHolder.avatar.setImageResource(mail.getAvatar());
        viewHolder.time.setText(mail.getAddress());
        viewHolder.checkBox.setChecked(mail.isSelected());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail.setSelected(((CheckBox)view).isChecked());
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

class MyViewHolder {
    TextView username;
    TextView title;
    TextView content;
    ImageView avatar;
    TextView time;
    CheckBox checkBox;
}