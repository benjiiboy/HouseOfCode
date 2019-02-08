package com.example.benjamin_pc.houseofcode.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benjamin_pc.houseofcode.Models.ChatRoom;
import com.example.benjamin_pc.houseofcode.R;

import java.util.ArrayList;

public class ChatRoomAdapter extends BaseAdapter {

    ArrayList<ChatRoom> ChatroomList;
    Context context;

    public ChatRoomAdapter(Context context, ArrayList<ChatRoom> chatroomList){
        this.context = context;
        this.ChatroomList = chatroomList;
    }

    @Override
    public int getCount() {
        return ChatroomList.size();
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
            convertView = inflater.inflate(R.layout.chatroom_list_item, null);
        }
        ChatRoom chatroom = ChatroomList.get(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.chatroom_txtName);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.chatroom_txtDescription);

        txtName.setText(chatroom.getName());
        txtDescription.setText(chatroom.getDescription());
        return convertView;
    }
}
