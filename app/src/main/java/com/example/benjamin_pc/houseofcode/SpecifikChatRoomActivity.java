package com.example.benjamin_pc.houseofcode;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SpecifikChatRoomActivity extends AppCompatActivity {
    private ListView SpecifikListView;
    public static final ArrayList<ChatMessage> MessageList = new ArrayList<>();
    private String chatroomName;
    private DatabaseReference rootDatabase, chatmessageRef;
    EditText editText;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifik_chat_room);
        SpecifikListView = findViewById(R.id.specifikchat_ListView);
        editText = (EditText) findViewById(R.id.specifikchat_edittext);

        Intent intent = getIntent();
        chatroomName = (String) intent.getSerializableExtra("ChatRoomName");
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        chatmessageRef = rootDatabase.child("ChatroomMessage").child(chatroomName);


        //Listener to firebase database
        valueEventListener = new ValueEventListener() {
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

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SendMessage();
                return true;
            }
        });
    }

    public void SendMessage(){
        String name = Profile.getCurrentProfile().getName();
        Date Calanderdate = Calendar.getInstance().getTime();
        Long timeinmilis = Calendar.getInstance().getTimeInMillis();


        String text = editText.getText().toString();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String date = sdf.format(Calanderdate);

        ChatMessage chatMessage = new ChatMessage(name,date ,text);
        chatmessageRef.child(timeinmilis.toString()).setValue(chatMessage);
        editText.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        Toast.makeText(this, "Kommentar oprettet", Toast.LENGTH_SHORT).show();
        chatmessageRef.addListenerForSingleValueEvent(valueEventListener);
        //TODO: update

    }


    public void getData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String date = ds.child("date").getValue(String.class);
            String name = ds.child("name").getValue(String.class);
            String text = ds.child("text").getValue(String.class);
            MessageList.add(new ChatMessage(name, date, text));
        }

        SpecifikListView.setAdapter(new ChatMessageAdapter(SpecifikChatRoomActivity.this, MessageList));

    }
}
