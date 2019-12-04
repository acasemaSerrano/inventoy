package com.acasema.inventory.iu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.acasema.inventory.R;

public class LoginActivity extends AppCompatActivity {

    private Button btSinngIn;
    private Button btSingUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        btSinngIn=findViewById(R.id.btSignIn);
        btSinngIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashActivity.class);
                startActivity(intent);
            }
        });

        btSingUp=findViewById(R.id.btSignUp);
        btSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

    }
}
