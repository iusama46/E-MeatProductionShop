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
import co.example.meatshop.activities.admin.adapters.OrderAdapter;
import co.example.meatshop.activities.admin.models.Order;
import co.example.meatshop.utils.CShowProgress;

public class OrdersHistoryActivity extends AppCompatActivity {

    List<Order> orderList;
    RecyclerView rv;
    OrderAdapter adapter;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        cShowProgress = CShowProgress.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress.showProgress(this);
        rv = findViewById(R.id.rv);
        orderList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("orders");

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new OrderAdapter(orderList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts();
    }

    private void getProducts() {

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Order product = new Order();
                    product.setAmount(dsp.child("amount").getValue().toString());
                    product.setNote(dsp.child("note").getValue().toString());
                    product.setCustName(dsp.child("customerName").getValue().toString());
                    product.setDeliveryDate(dsp.child("deliveryTime").getValue().toString());
                    product.setOrderId(dsp.child("orderNo").getValue().toString());
                    product.setId(dsp.getKey().toString());

                    if (!orderList.isEmpty()) {
                        cShowProgress.hideProgress();
                    }
                    orderList.add(product);
                    adapter = new OrderAdapter(orderList);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                cShowProgress.hideProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cShowProgress.hideProgress();
                Toast.makeText(OrdersHistoryActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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