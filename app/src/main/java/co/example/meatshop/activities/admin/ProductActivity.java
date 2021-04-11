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
import co.example.meatshop.activities.admin.adapters.ProductAdapter;
import co.example.meatshop.activities.admin.models.Product;
import co.example.meatshop.utils.CShowProgress;

public class ProductActivity extends AppCompatActivity {

    static List<Product> productList;
    RecyclerView rv;
    ProductAdapter adapter;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;

    public static List<Product> getProductList() {
        return productList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        cShowProgress = CShowProgress.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress.showProgress(this);
        rv = findViewById(R.id.rv);
        productList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("products");

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new ProductAdapter(productList, reference);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts();
    }

    private void getProducts() {

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Product product = new Product();
                    product.setpName(dsp.child("name").getValue().toString());
                    product.setExpiryDate(dsp.child("expiryDate").getValue().toString());
                    product.setpImg(dsp.child("img").getValue().toString());
                    product.setQty(dsp.child("quantity").getValue().toString());
                    product.setStock(dsp.child("stock").getValue().toString());
                    product.setpName(dsp.child("name").getValue().toString());
                    product.setId(dsp.getKey().toString());

                    if (!productList.isEmpty()) {
                        cShowProgress.hideProgress();
                    }
                    productList.add(product);
                    adapter = new ProductAdapter(productList, reference);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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