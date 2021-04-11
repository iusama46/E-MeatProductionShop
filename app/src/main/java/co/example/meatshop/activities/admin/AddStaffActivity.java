package co.example.meatshop.activities.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;

import co.example.meatshop.R;

public class AddStaffActivity extends AppCompatActivity {

    ArrayList<String> categoriesList = new ArrayList<>();
    int stateIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        setSpinners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText name = findViewById(R.id.name);
        EditText address = findViewById(R.id.address);
        EditText phone = findViewById(R.id.phoneNo);
        EditText location = findViewById(R.id.location);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("staff");

        int id = getIntent().getIntExtra("id",-1);
        if(id!=-1 ){

            name.setText(StaffListActivity.staffList.get(id).getName());
            location.setText(StaffListActivity.staffList.get(id).getLocation());

        }


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("name", name.getText().toString());
                dataMap.put("address", address.getText().toString());
                dataMap.put("phone", phone.getText().toString());
                dataMap.put("location", location.getText().toString());
                dataMap.put("role", categoriesList.get(stateIndex).toString());

                if(id!=-1){

                    reference.child(StaffListActivity.staffList.get(id).getId()).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddStaffActivity.this, "Staff member info updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddStaffActivity.this, "" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return;
                }
                reference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddStaffActivity.this, "Staff member added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddStaffActivity.this, "" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void setSpinners() {

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.state);


        categoriesList.add("Manager");
        categoriesList.add("Cashier");
        categoriesList.add("Cash Dealer");
        categoriesList.add("Office Boy");
        categoriesList.add("Administrator");

        spinner.setItems(categoriesList);
        spinner.setHint("Role");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                stateIndex = position;
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


    }

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
}