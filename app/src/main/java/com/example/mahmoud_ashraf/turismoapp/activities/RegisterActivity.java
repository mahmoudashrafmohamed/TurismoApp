package com.example.mahmoud_ashraf.turismoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahmoud_ashraf.turismoapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //find views
    private TextView back;
    private EditText email,password,name,age;
    private Button signup;
    private TextView help_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init
        back=(TextView)findViewById(R.id.textView_back);
        email=(EditText)findViewById(R.id.edittext_email_signup);
        password = (EditText) findViewById(R.id.editTextPassword_signup);
        name=(EditText)findViewById(R.id.editText_name_signup);
        age=(EditText)findViewById(R.id.editTextAgeSignup);
        signup=(Button)findViewById(R.id.sign_up_button);

        help_pass = (TextView) findViewById(R.id.textView_helppass_register);

        // focus
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    help_pass.setVisibility(View.VISIBLE);
                }else {
                    help_pass.setVisibility(View.GONE);
                }
            }
        });

        //listener
        back.setOnClickListener(this);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if( view.getId() == signup.getId() ) {



            if (name.getText().toString().length() == 0) {
                name.setError("you should enter your name ");
            } else if (email.getText().toString().length() == 0) {
                email.setError("you should enter your email ");
            } else if (password.getText().toString().length() == 0) {
                password.setError("you should enter your password");
            } else if (!isEmailValid(email.getText().toString())) {
                email.setError("Invalid email");
            } else if (!isPasswordValid(password.getText().toString())) {
                password.setError("Invalid pass");
            } else {
                // PUT EXTRA TO REGISTER AS ACTIVITY
              //  presenter.validateCredentials("", name.getText().toString(), email.getText().toString(), password.getText().toString(), age.getText().toString());
                Intent intent = new Intent(this, RegisterationAsActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);

            }
        }
        else if ( view.getId() == back.getId()){

            finish();

        }

    }

    public boolean isPasswordValid(String password) {
        return password.matches("(?=.*[a-z-A-Z]).{8,}");
    }

    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
