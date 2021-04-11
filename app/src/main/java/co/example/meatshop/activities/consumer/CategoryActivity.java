package co.example.meatshop.activities.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.models.Product;
import co.example.meatshop.activities.consumer.adapters.HomeAdapter;
import co.example.meatshop.utils.CShowProgress;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView rv;
    HomeAdapter adapter;
    List<Product> productList;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int catId = getIntent().getIntExtra("cat",0);

        cShowProgress = CShowProgress.getInstance();
        auth = FirebaseAuth.getInstance();
        cShowProgress.showProgress(this);
        rv = findViewById(R.id.rv);
        productList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("products");

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new HomeAdapter(productList, auth.getUid());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts(catId);

    }

    private void getProducts(int catId) {

        valueEventListener = reference.orderByChild("category").equalTo(catId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Product product = new Product();
                    product.setpName(dsp.child("name").getValue().toString());
                    product.setExpiryDate(dsp.child("expiryDate").getValue().toString());
                    product.setpImg(dsp.child("img").getValue().toString());
                    product.setQty(dsp.child("quantity").getValue().toString());
                    product.setPrice(dsp.child("price").getValue().toString());
                    product.setStock(dsp.child("stock").getValue().toString());
                    product.setpName(dsp.child("name").getValue().toString());
                    product.setId(dsp.getKey().toString());


                    productList.add(product);
                    adapter = new HomeAdapter(productList, auth.getUid().toString());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                cShowProgress.hideProgress();
                if (productList.isEmpty()) {
                    cShowProgress.hideProgress();
                    Toast.makeText(CategoryActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getActivity(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (valueEventListener != null)
            reference.removeEventListener(valueEventListener);
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