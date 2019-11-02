package com.danielogbuti.rush;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView signUpLink;
    private EditText emailText;
    private EditText passwordText;
    private static final int REQUEST_SIGNUP = 0;
    private final String TAG = "akiliHealth";
    connectToEthereum blockchain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        blockchain = new connectToEthereum();
        if(blockchain.ConnectToEthereum()){
            Toast.makeText(this, "Successfully connected to blockchain", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Succesfully Connected To Block");
        }else{
            Log.e(TAG,"Not successful");
        }
        loginButton = (Button)findViewById(R.id.btn_login);
        signUpLink = (TextView)findViewById(R.id.link_signup);
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        //enable the signup text

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(SignInActivity.this,SignUpActivity.class);
                startActivityForResult(intent,REQUEST_SIGNUP);
                finish();
            }
        });
    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }



        //show a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        // authenticate the user using firebase
        /*auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.i(TAG, "This was unsuccessful");
                        } else {
                            Log.i(TAG, "This was successful");
                        }
                    }
                });*/

        Intent intent = new Intent(SignInActivity.this,Home.class);
        startActivity(intent);



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        Log.i(TAG,"Login Success");
                        //onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginFailed() {
        //if login failed display this in a toast and enable the button
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //if email is invalid set valid to false
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
