package com.example.benjamin_pc.houseofcode.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin_pc.houseofcode.Adapters.ChatMessageAdapter;
import com.example.benjamin_pc.houseofcode.Models.ChatMessage;
import com.example.benjamin_pc.houseofcode.NotificationHelper;
import com.example.benjamin_pc.houseofcode.R;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth mAuth;

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifik_chat_room);
        SpecifikListView = findViewById(R.id.specifikchat_ListView);
        editText = (EditText) findViewById(R.id.specifikchat_edittext);


        //get google user instance
        mAuth = FirebaseAuth.getInstance();

        mNotificationHelper = new NotificationHelper(this);

        Intent intent = getIntent();
        chatroomName = (String) intent.getSerializableExtra("ChatRoomName");
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        chatmessageRef = rootDatabase.child("ChatroomMessage").child(chatroomName);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        setTitle(chatroomName);


        //Read data  from firebase database
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

        //add button
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });


    }

    //inflater meny
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_specificchat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void subscribeToChannel(){
        //custom dialog box
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Vil du subscribe til: " + chatroomName + " ?");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference();
                reference.child("ChatroomMessage").child(chatroomName)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children){
                                    //Gets new comment from firebase database
                                    ChatMessage chatMessageValue = child.getValue(ChatMessage.class);
                                    sendOnChannel1("Ny Chat besked: " + chatroomName,chatMessageValue.getText(), chatroomName);
                                    //Updates list from database with new messages
                                    MessageList.clear();
                                    getData(dataSnapshot);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.GREEN);
        okBT.setLayoutParams(neutralBtnLP);
        okBT.setText("Ja tak!");

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
        cancelBT.setText("Nej tak!");

    }


    //Method to post a message to firebase database using
    public void sendMessage(){

        //check for google or facebook login name.
        String name ="";

        if (AccessToken.getCurrentAccessToken() != null)
        {
            name = Profile.getCurrentProfile().getName();
        }
        if (mAuth.getCurrentUser() != null){
            name = mAuth.getCurrentUser().getDisplayName();
        }

        Date Calanderdate = Calendar.getInstance().getTime();
        Long timeinmilis = Calendar.getInstance().getTimeInMillis();


        String text = editText.getText().toString();
        //format the date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String date = sdf.format(Calanderdate);

        ChatMessage chatMessage = new ChatMessage(name,date ,text);
        chatmessageRef.child(timeinmilis.toString()).setValue(chatMessage);
        editText.setText("");
        //Hide keyboard after pressing enter
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        subscribeToChannel();

        //Inform user that they posted a message
        Toast.makeText(this, "Kommentar oprettet", Toast.LENGTH_SHORT).show();
        chatmessageRef.addListenerForSingleValueEvent(valueEventListener);


    }

    //method to get the data snapshot from firebase database
    public void getData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String date = ds.child("date").getValue(String.class);
            String name = ds.child("name").getValue(String.class);
            String text = ds.child("text").getValue(String.class);
            MessageList.add(new ChatMessage(name, date, text));
        }

        SpecifikListView.setAdapter(new ChatMessageAdapter(SpecifikChatRoomActivity.this, MessageList));

    }


    //notification

    public void sendOnChannel1(String title, String message, String chatroomName){
        android.support.v4.app.NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title,message, chatroomName);
        mNotificationHelper.getManager().notify(1,nb.build());
    }
}
