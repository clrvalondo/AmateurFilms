package com.example.clrvalondo.valondo_finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LoginDataBaseAdapter loginDataBaseAdapter;

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private Toast popToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        final Button login = (Button) findViewById(R.id.register);
        final TextView showPass = (TextView) findViewById(R.id.showPass);
        final TextView txtReg = (TextView) findViewById(R.id.txtReg);

        popToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);


        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String userName = email.getText().toString();
                String password = pass.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    //Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, loginDataBaseAdapter.getInfo(userName), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Touch.class );
                    startActivity(intent);

                    finish();
                    //dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_LONG).show();
                }

            }

        });

        txtReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,Register.class );
                startActivity(intent);
                finish();
            }

        });


        showPass.setOnTouchListener(new View .OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                int cursor = pass.getSelectionStart();

                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        pass.setTransformationMethod(null);
                        Log.d("Classname", "ACTION_DOWN");
                        pass.setSelection(cursor);
                        return true;
                    case MotionEvent.ACTION_UP:
                        //pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        pass.setTransformationMethod(new PasswordTransformationMethod());
                        Log.d("Classname", "ACTION_UP");
                        pass.setSelection(cursor);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        //pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        pass.setTransformationMethod(new PasswordTransformationMethod());
                        pass.setSelection(cursor);
                        return true;
                    default:
                        return true;
                }

            }
        });

    }

}