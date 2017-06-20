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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud_ashraf.turismoapp.R;
import com.example.mahmoud_ashraf.turismoapp.async.Connector;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //find views
    private ProgressBar progressBar;
    private Button login;
    private EditText email,password;
    private TextView createacc,forgetpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init
        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        login=(Button)findViewById(R.id.login_button);
        email=(EditText)findViewById(R.id.editEmail_login);
        password = (EditText) findViewById(R.id.editTextPassword_login);
        createacc = (TextView) findViewById(R.id.textview_create_account);
        forgetpass = (TextView) findViewById(R.id.textView_forgetpass_login);


        // listener
        createacc.setOnClickListener(this);
        forgetpass.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if( view.getId() == login.getId() ) {



            if (email.getText().toString().length() == 0) {
                hideProgress();
                email.setError("you should enter your email ");
            } else if (password.getText().toString().length() == 0) {
                hideProgress();
                password.setError("you should enter your password");
            } else if (!isEmailValid(email.getText().toString())) {
                hideProgress();
                email.setError("Invalid email");
            } else if (!isPasswordValid(password.getText().toString())) {
                hideProgress();
                password.setError("Invalid pass");
            } else {
                Toast.makeText(this,"Loading... send to server",Toast.LENGTH_LONG).show();
               //// validateCredentials( email.getText().toString(), password.getText().toString());
                Connector c = new Connector(this);
                c.Login(email.getText().toString(),password.getText().toString());

            }
        }
        else if ( view.getId() == createacc.getId()){

            startActivity(new Intent(this, RegisterActivity.class));

        }
        else if(view.getId() == forgetpass.getId()){

        }


    }
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isPasswordValid(String password) {
        //return password.matches("(?=.*[a-z-A-Z]).{8,}");
        return true;
    }
}
