package com.example.benjamin_pc.houseofcode.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benjamin_pc.houseofcode.Models.ChatMessage;
import com.example.benjamin_pc.houseofcode.R;

import java.util.ArrayList;

public class ChatMessageAdapter extends BaseAdapter {
    ArrayList<ChatMessage> ChatMessageList;
    Context context;

    public ChatMessageAdapter(Context context, ArrayList<ChatMessage> chatmessageList){
        this.context = context;
        this.ChatMessageList = chatmessageList;
    }

    @Override
    public int getCount() {
        return ChatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //set the cardview items to specific values
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chat_list_item, null);
        }
        ChatMessage chatMessage = ChatMessageList.get(position);

        //TODO: Hent billede af bruger fra facebook
        TextView txtName = (TextView) convertView.findViewById(R.id.chat_txtHeadline);
        TextView txtDate = (TextView) convertView.findViewById(R.id.chat_txtCreated);
        TextView txtText = (TextView) convertView.findViewById(R.id.chat_txtText);

        txtName.setText(chatMessage.getName());
        txtDate.setText(chatMessage.getDate());
        txtText.setText(chatMessage.getText());
        return convertView;
    }
}
