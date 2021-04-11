package co.example.meatshop.activities.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import co.example.meatshop.R;

public class AddProductionUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_production_unit);

        EditText name = findViewById(R.id.name);
        EditText address = findViewById(R.id.address);
        EditText location = findViewById(R.id.location);
        EditText phone = findViewById(R.id.phoneNo);


        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            name.setText(ProductionListActivity.productionUnitList.get(id).getName());
            location.setText(ProductionListActivity.productionUnitList.get(id).getLocation());
            phone.setText(ProductionListActivity.productionUnitList.get(id).getPhone());
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("production_unit");


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("name", name.getText().toString());
                dataMap.put("num_of_workers", address.getText().toString());
                dataMap.put("phone", phone.getText().toString());
                dataMap.put("location", location.getText().toString());

                if (id != -1) {
                    reference.child(ProductionListActivity.productionUnitList.get(id).getId()).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddProductionUnitActivity.this, "production unit updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddProductionUnitActivity.this, "" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return;
                }
                reference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddProductionUnitActivity.this, "production unit added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddProductionUnitActivity.this, "" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}