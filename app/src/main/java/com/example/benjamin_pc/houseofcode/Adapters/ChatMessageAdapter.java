package com.example.benjamin_pc.houseofcode.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benjamin_pc.houseofcode.Models.ChatMessage;
import com.example.benjamin_pc.houseofcode.R;

import java.util.ArrayList;

public class ChatMessageAdapter extends BaseAdapter {
    ArrayList<ChatMessage> ChatMessageList;
    Context context;
    private int lastPosition = -1;

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


    static class ViewHolder {
        TextView text;
        TextView date;
        TextView name;
    }

    //set the cardview items to specific values
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final View result;
        ViewHolder holder;

        ChatMessage chatMessage = ChatMessageList.get(position);

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chat_list_item, null);

            //holder to the shown list
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.chat_txtText);
            holder.date = (TextView) convertView.findViewById(R.id.chat_txtCreated);
            holder.name = (TextView) convertView.findViewById(R.id.chat_txtHeadline);

            result = convertView;

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //animation when scrolling
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim );
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(chatMessage.getName());
        holder.date.setText(chatMessage.getDate());
        holder.text.setText(chatMessage.getText());
        //TODO: Hent billede af bruger
        return convertView;
    }
}
