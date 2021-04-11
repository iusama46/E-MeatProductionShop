package co.example.meatshop.activities.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.HashMap;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.fragments.CartFragment;
import co.example.meatshop.utils.CShowProgress;

public class CheckOutActivity extends AppCompatActivity {

    int selectedIndex = 0;
    EditText debit;
    CShowProgress cShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress = CShowProgress.getInstance();
        EditText name = findViewById(R.id.name);
        debit = findViewById(R.id.debit);
        EditText address = findViewById(R.id.address);
        EditText orderNo = findViewById(R.id.order_id);
        EditText note = findViewById(R.id.description);
        EditText phone = findViewById(R.id.payment);
        Button button = findViewById(R.id.add);
        getSupportActionBar().setTitle("Checkout");
        getSpinner();


        int amount = getIntent().getIntExtra("amount", 1);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("orders");
        DatabaseReference referencePay = FirebaseDatabase.getInstance().getReference().child("payments");
        String key = referencePay.push().getKey();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cShowProgress.showProgress(CheckOutActivity.this);
                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("uId", auth.getUid().toString());


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateTime = sdf.format(new Date());
                dataMap.put("transaction_date", dateTime);
                if (selectedIndex == 1) {
                    dataMap.put("payment_method", "debit");
                    dataMap.put("payment", "done");
                    dataMap.put("debit_card", debit.getText().toString());

                } else {
                    dataMap.put("payment_method", "cod");
                    dataMap.put("payment", "pending");
                    dataMap.put("debit_card", "0");
                }
                dataMap.put("amount", amount);


                referencePay.child(key).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        final HashMap<Object, Object> dataMap1 = new HashMap<Object, Object>();
                        dataMap1.put("uId", auth.getUid().toString());
                        dataMap1.put("amount", amount);
                        dataMap1.put("paymentId", key);
                        dataMap1.put("payment_method", selectedIndex);

                        dataMap1.put("customerName", name.getText().toString());
                        dataMap1.put("address", address.getText().toString());
                        dataMap1.put("phone", phone.getText().toString());
                        dataMap1.put("note", note.getText().toString());
                        dataMap1.put("orderNo", orderNo.getText().toString());

                        LocalDate tempDate = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            tempDate = LocalDate.now().plusDays(2);

                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                            dataMap1.put("deliveryTime", tempDate.format(myFormatObj).toString()+ " 12:30PM");
                        } else {
                            dataMap1.put("deliveryTime", "01/02/2021 12:30PM");
                        }


                        for (int i = 0; i < CartFragment.getCartList().size(); i++) {
                            dataMap1.put("order" + i, CartFragment.getCartList().get(i).getId());
                        }

                        reference.push().setValue(dataMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                cShowProgress.hideProgress();
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(CheckOutActivity.this, OrderSuccessActivity.class)
                                            .putExtra("orderNo", orderNo.getText().toString())
                                            .putExtra("amount", amount));
                                } else {

                                    Toast.makeText(CheckOutActivity.this, "Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        cShowProgress.hideProgress();
                        Toast.makeText(CheckOutActivity.this, "Failed to check out, error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }


    private void getSpinner() {
        MaterialSpinner spinnerState = findViewById(R.id.state);

        spinnerState.setItems("Cash On Delivery", "Credit Card");
        spinnerState.setHint("Payment Method");
        spinnerState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectedIndex = position;

                if (position == 1) {
                    debit.setVisibility(View.VISIBLE);
                } else {
                    debit.setVisibility(View.GONE);
                }

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