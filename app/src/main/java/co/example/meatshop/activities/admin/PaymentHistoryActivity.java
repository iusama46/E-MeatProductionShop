package co.example.meatshop.activities.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.adapters.PaymentAdapter;
import co.example.meatshop.activities.admin.models.Payment;
import co.example.meatshop.utils.CShowProgress;

public class PaymentHistoryActivity extends AppCompatActivity {

    List<Payment> paymentList;
    RecyclerView rv;
    PaymentAdapter adapter;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        cShowProgress = CShowProgress.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress.showProgress(this);
        rv = findViewById(R.id.rv);
        paymentList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("payments");

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new PaymentAdapter(paymentList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts();
    }

    private void getProducts() {

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paymentList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Payment product = new Payment();
                    product.setAmount(dsp.child("amount").getValue().toString());
                    product.setStatus(dsp.child("payment").getValue().toString());
                    product.setPaymentMethod(dsp.child("payment_method").getValue().toString());
                    product.setId(dsp.child("uId").getValue().toString());
                    product.setId(dsp.getKey().toString());

                    if (!paymentList.isEmpty()) {
                        cShowProgress.hideProgress();
                    }
                    paymentList.add(product);
                    adapter = new PaymentAdapter(paymentList);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentHistoryActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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