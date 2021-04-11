package co.example.meatshop.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            if (auth.getCurrentUser().getUid().equals("hFitgvGqFPXCSCnS9zQM6haeOAj2"))
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            else
                startActivity(new Intent(SplashActivity.this, co.example.meatshop.activities.consumer.HomeActivity.class));
            return;
        }

        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }
}