package com.example.benjamin_pc.houseofcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjamin_pc.houseofcode.Adapters.ChatRoomAdapter;
import com.example.benjamin_pc.houseofcode.Models.ChatRoom;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView mainListView;
    public static final ArrayList<ChatRoom> ChatRoomList = new ArrayList<>();
    private DatabaseReference rootDatabase, chatroomRef;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        setTitle("Chat Rooms");

        rootDatabase = FirebaseDatabase.getInstance().getReference();
        chatroomRef = rootDatabase.child("Chatroom");
        mainListView = findViewById(R.id.chat_ListView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);

        //Listener to firebase database
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChatRoomList.clear();
                getData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        chatroomRef.addListenerForSingleValueEvent(valueEventListener);

        //Swipe to refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ChatRoomList.clear();
                chatroomRef.addListenerForSingleValueEvent(valueEventListener);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //inflater meny
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chatroom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //method to get the datasnapchot from firebase database
    public void getData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String desc = ds.child("Description").getValue(String.class);
            String name = ds.child("Name").getValue(String.class);
            ChatRoomList.add(new ChatRoom(name, desc));
            Log.d("chatlist", desc + " / " + name);
        }

        //custom adapter to navigate to specific chat room
        mainListView.setAdapter(new ChatRoomAdapter(ChatActivity.this, ChatRoomList));
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), SpecifikChatRoomActivity.class);
                ChatRoom chatRoom = ChatRoomList.get(position);
                intent.putExtra("ChatRoomName", chatRoom.getName());
                startActivity(intent);
            }
        });
    }

    //sig out button
    public void Signout(MenuItem item) {

        //TODO: google logud
        if (Profile.getCurrentProfile() != null){
            LoginManager.getInstance().logOut();
        } else if(FirebaseAuth.getInstance() != null){
            FirebaseAuth.getInstance().signOut();
        }
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
