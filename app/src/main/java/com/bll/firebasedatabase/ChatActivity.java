package com.bll.firebasedatabase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    String username;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username = getIntent().getStringExtra("username");

        db = FirebaseDatabase.getInstance().getReference("message");
        final LinearLayout llChat = (LinearLayout) findViewById(R.id.llChat);
        final ScrollView svChat = (ScrollView) findViewById(R.id.svChat);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    String message = dataSnapshot.getValue(String.class);

                    TextView tvMessage = new TextView(ChatActivity.this);
                    tvMessage.setText(message);

                    if(message.startsWith(username))
                        tvMessage.setBackgroundResource(R.drawable.chat_right);
                    else
                        tvMessage.setBackgroundResource(R.drawable.chat_left);


                    tvMessage.setTextColor(Color.WHITE);

                    llChat.addView(tvMessage);

                    svChat.post(new Runnable() {
                        @Override
                        public void run() {
                            svChat.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });




                    //Log.d( "message", dataSnapshot.getValue(String.class));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




    }

    public void sendMessage(View view) {
        EditText etMessage = (EditText) findViewById(R.id.etMessage);

        db.setValue( username + ": " + etMessage.getText().toString());


    }
}
