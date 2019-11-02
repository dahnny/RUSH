package com.danielogbuti.rush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentChoice extends AppCompatActivity {
    private CardView account;
    private CardView airtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_choice);

        account = (CardView)findViewById(R.id.account);
        airtime = (CardView)findViewById(R.id.airtime);



        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStatus();
            }
        });

        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStatus();
            }
        });
    }

    private void viewStatus() {
        Intent intent = new Intent(PaymentChoice.this,ViewStatus.class);
        startActivity(intent);
    }
}
