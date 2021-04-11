package co.example.meatshop.activities.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import co.example.meatshop.R;
import co.example.meatshop.utils.CShowProgress;

public class AddDealerActivity extends AppCompatActivity {

    DatabaseReference database;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance().getReference().child("dealers");
        CShowProgress cShowProgress = CShowProgress.getInstance();
        EditText name = findViewById(R.id.name);
        EditText lastName = findViewById(R.id.last_name);
        EditText cnic = findViewById(R.id.cnic);
        EditText address = findViewById(R.id.address);
        EditText number = findViewById(R.id.supplier);
        EditText inventory = findViewById(R.id.supplier_inv);


        Button btn = findViewById(R.id.add);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cShowProgress.showProgress(AddDealerActivity.this);
                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("name", name.getText().toString());
                dataMap.put("lastName", lastName.getText().toString());
                dataMap.put("cnic", cnic.getText().toString());
                dataMap.put("address", address.getText().toString());
                dataMap.put("number", number.getText().toString());
                dataMap.put("inventory", inventory.getText().toString());

                try {

                    database.push().setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddDealerActivity.this, "Dealer added successfully", Toast.LENGTH_SHORT).show();
                            cShowProgress.hideProgress();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            cShowProgress.hideProgress();
                            Toast.makeText(AddDealerActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    cShowProgress.hideProgress();
                    Toast.makeText(AddDealerActivity.this, "Failed to add data, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}