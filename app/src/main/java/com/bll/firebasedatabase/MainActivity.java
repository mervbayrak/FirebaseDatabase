package com.bll.firebasedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bll.firebasedatabase.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerUser(View view) {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        username = etUsername.getText().toString();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        User user = new User(etUsername.getText().toString(), etEmail.getText().toString());

        String key =  db.child("users").push().getKey();
        db.child("users").child(key).setValue(user);

        //db.child("users").child(etUsername.getText().toString()).setValue(user);

    }

    public void openChat(View view) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
