package com.example.benjamin_pc.houseofcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpecifikChatRoomActivity extends AppCompatActivity {
    private ListView SpecifikListView;
    public static final ArrayList<ChatMessage> MessageList = new ArrayList<>();
    private String chatroomName;
    private DatabaseReference rootDatabase, chatmessageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifik_chat_room);
        SpecifikListView = findViewById(R.id.specifikchat_ListView);

        Intent intent = getIntent();
        chatroomName = (String) intent.getSerializableExtra("ChatRoomName");
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        chatmessageRef = rootDatabase.child("ChatroomMessage").child(chatroomName);


        //Listener to firebase database
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MessageList.clear();
                getData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        chatmessageRef.addListenerForSingleValueEvent(valueEventListener);

    }

    public void getData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String created = ds.child("Created").getValue(String.class);
            String name = ds.child("Name").getValue(String.class);
            String txt = ds.child("Text").getValue(String.class);
            MessageList.add(new ChatMessage(name, created, txt));
        }

        SpecifikListView.setAdapter(new ChatMessageAdapter(SpecifikChatRoomActivity.this, MessageList));

    }
}
