package com.example.clrvalondo.valondo_finalexam;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.text.method.Touch;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private Toast popToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        popToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
        popToast.setText("Logged in!");
        popToast.show();

        final Button btnTouch = (Button) findViewById(R.id.btnTouch);

        btnTouch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Profile.this,Touch.class );
                startActivity(intent);
            }

        });
    }

}
