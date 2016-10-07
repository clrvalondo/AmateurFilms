package com.example.clrvalondo.valondo_finalexam;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    boolean isName(String name) {
        String pat = "[A-Za-z]{1,}$";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }


    private Toast popToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        final EditText remail = (EditText) findViewById(R.id.remail);
        final EditText rfname = (EditText) findViewById(R.id.rfname);
        final EditText rlname = (EditText) findViewById(R.id.rlname);
        final EditText runame = (EditText) findViewById(R.id.runame);
        final EditText rpass = (EditText) findViewById(R.id.rpass);
        final EditText rconfpass = (EditText) findViewById(R.id.rconfpass);
        final Button register = (Button) findViewById(R.id.register);

        popToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);


        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(rfname.getText().toString().equals("")||rlname.getText().toString().equals("")||runame.getText().toString().equals("")||remail.getText().toString().equals("")||rpass.getText().toString().equals("")||rconfpass.getText().toString().equals(""))
                {
                    popToast.setText("Fill out all the remaining fields.");
                    popToast.show();
                }
                else if(!isName(rfname.getText().toString()))
                {
                    rfname.setError("Numbers and Special Characters are not allowed.");
                }
                else if(!isName(rlname.getText().toString()))
                {
                    rlname.setError("Numbers and Special Characters are not allowed.");
                }
                else if(isValidEmail(runame.getText()))
                {
                    runame.setError("Invalid Username.");
                }
                else if(loginDataBaseAdapter.ifExist(runame.getText().toString()))
                {
                    runame.setError("Existing Username.");
                }
                else if(!isValidEmail(remail.getText()))
                {
                    remail.setError("Please enter a valid email address.");
                }
                else if(loginDataBaseAdapter.ifExist(remail.getText().toString()))
                {
                    remail.setError("Existing Email Address.");
                }
                else if(rpass.getText().length()<8)
                {
                    rpass.setError("Minimum password length is at least 8 characters.");
                }
                else if(rpass.getText().toString().equals(rconfpass.getText().toString()))
                {
                    loginDataBaseAdapter.insertEntry(rfname.getText().toString(),rlname.getText().toString(),runame.getText().toString(),remail.getText().toString(),rpass.getText().toString());
                    popToast.setText("Account Successfully Created.");
                    popToast.show();


                    Intent intent = new Intent(Register.this,MainActivity.class );
                    startActivity(intent);
                    finish();
                }
                else
                {
                    rconfpass.setError("Passwords do not match.");
                }
            }

        });
    }

}
