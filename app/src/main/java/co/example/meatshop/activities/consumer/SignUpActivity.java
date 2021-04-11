package co.example.meatshop.activities.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import co.example.meatshop.R;
import co.example.meatshop.activities.LoginActivity;
import co.example.meatshop.utils.CShowProgress;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseAuth auth= FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("consumers");
        CShowProgress cShowProgress = CShowProgress.getInstance();

        EditText name = findViewById(R.id.name);
        EditText pass = findViewById(R.id.pass);
        EditText email = findViewById(R.id.email);
        EditText address = findViewById(R.id.address);
        TextView textView = findViewById(R.id.old_user);
        Button signUp = findViewById(R.id.btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cShowProgress.showProgress(SignUpActivity.this);
                auth.createUserWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult.getUser()!=null){
                            final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                            dataMap.put("name", name.getText().toString());
                            dataMap.put("address", address.getText().toString());
                            dataMap.put("email", email.getText().toString());

                            reference.child(authResult.getUser().getUid()).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    cShowProgress.hideProgress();
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Unable to create account "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Unable to create account "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        cShowProgress.hideProgress();
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}