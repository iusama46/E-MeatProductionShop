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
import co.example.meatshop.activities.admin.adapters.ProductionUnitAdapter;
import co.example.meatshop.activities.admin.models.ProductionUnit;
import co.example.meatshop.utils.CShowProgress;

public class ProductionListActivity extends AppCompatActivity {

   public static List<ProductionUnit> productionUnitList;
    RecyclerView rv;
    ProductionUnitAdapter adapter;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress = CShowProgress.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cShowProgress.showProgress(this);
        rv = findViewById(R.id.rv);
        productionUnitList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("production_unit");

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new ProductionUnitAdapter(productionUnitList, reference);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts();
    }

    private void getProducts() {

        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productionUnitList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    ProductionUnit product = new ProductionUnit();
                    product.setName(dsp.child("name").getValue().toString());
                    product.setLocation(dsp.child("location").getValue().toString());
                    product.setPhone(dsp.child("phone").getValue().toString());

                    product.setId(dsp.getKey().toString());

                    if (!productionUnitList.isEmpty()) {
                        cShowProgress.hideProgress();
                    }
                    productionUnitList.add(product);
                    adapter = new ProductionUnitAdapter(productionUnitList, reference);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                cShowProgress.hideProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cShowProgress.hideProgress();
                Toast.makeText(getApplicationContext(), "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null)
            reference.removeEventListener(valueEventListener);
    }
}