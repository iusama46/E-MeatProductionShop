package co.example.meatshop.activities.consumer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import co.example.meatshop.R;
import co.example.meatshop.utils.CShowProgress;

public class ProductDisplayActivity extends AppCompatActivity {

    CShowProgress cShowProgress = CShowProgress.getInstance();
    DatabaseReference dbRef;
    ValueEventListener listener;
    TextView itemType, title, qty, expiry, description, price;
    ImageView imageView;
    String img;
    String tempPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.img);
        title = findViewById(R.id.name);
        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        expiry = findViewById(R.id.date);
        description = findViewById(R.id.description);
        itemType = findViewById(R.id.type);
        cShowProgress.showProgress(this);
        String key = getIntent().getStringExtra("key");
        if (key != null) {
            getProductById(key);
        } else {
            Toast.makeText(this, "Unable to get Data", Toast.LENGTH_SHORT).show();
            finish();
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cart").child(auth.getCurrentUser().getUid());

        Button addBtn = findViewById(R.id.add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cShowProgress.showProgress(ProductDisplayActivity.this);
                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("name", title.getText().toString());
                dataMap.put("pId", key);
                dataMap.put("quantity", qty.getText().toString());
                dataMap.put("price", tempPrice);
                dataMap.put("img", img);

                reference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        cShowProgress.hideProgress();
                        if (task.isSuccessful()) {
                            Toast.makeText(ProductDisplayActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (listener != null)
                    dbRef.removeEventListener(listener);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getProductById(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference().child("products").child(id);
        listener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    if (dataSnapshot != null && dataSnapshot.exists()) {

                        title.setText(dataSnapshot.child("name").getValue().toString());
                        int tempType = dataSnapshot.child("type").getValue(Integer.class);

                        if (tempType == 1)
                            itemType.setText("Frozen");
                        else
                            itemType.setText("Fresh");


                        description.setText(dataSnapshot.child("description").getValue().toString());
                        qty.setText(dataSnapshot.child("quantity").getValue().toString());
                        img = dataSnapshot.child("img").getValue().toString();
                        expiry.setText("Expiry: " + dataSnapshot.child("expiryDate").getValue().toString());

                        price.setText("Rs " + dataSnapshot.child("price").getValue().toString() + "/-");
                        tempPrice=dataSnapshot.child("price").getValue().toString();

                        Glide.with(ProductDisplayActivity.this).load(img).placeholder(R.drawable.placeholder).into(imageView);

                        cShowProgress.hideProgress();
                    }

                } catch (Exception e) {
                    cShowProgress.hideProgress();
                    Toast.makeText(ProductDisplayActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                cShowProgress.hideProgress();
            }
        });
    }

    @Override
    public void onDestroy() {
        if (listener != null)
            dbRef.removeEventListener(listener);
        super.onDestroy();
    }
}