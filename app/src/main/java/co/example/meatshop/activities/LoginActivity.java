package co.example.meatshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.HomeActivity;
import co.example.meatshop.activities.consumer.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.pass);
        Button button = findViewById(R.id.btn);
        TextView textView = findViewById(R.id.old_user);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (auth.getCurrentUser().getUid() != null) {
                                if (auth.getCurrentUser().getUid().equals("hFitgvGqFPXCSCnS9zQM6haeOAj2") || email.getText().toString().equals("admin@admin.com"))
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                else
                                    startActivity(new Intent(LoginActivity.this, co.example.meatshop.activities.consumer.HomeActivity.class));

                                finish();
                            }


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}