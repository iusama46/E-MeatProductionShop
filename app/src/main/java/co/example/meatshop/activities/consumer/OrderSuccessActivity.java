package co.example.meatshop.activities.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.fragments.CartFragment;

public class OrderSuccessActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("consumers").child(auth.getUid()).child("orders");

        for (int i = 0; i < CartFragment.getCartList().size(); i++) {
            final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
            dataMap.put("name", CartFragment.getCartList().get(i).getName());
            dataMap.put("pId", CartFragment.getCartList().get(i).getpId());
            dataMap.put("quantity", CartFragment.getCartList().get(i).getQty());
            dataMap.put("price", CartFragment.getCartList().get(i).getPrice());
            dataMap.put("img", CartFragment.getCartList().get(i).getImg());

            reference.child(CartFragment.getCartList().get(i).getId()).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Success");
        TextView id = findViewById(R.id.id_order);
        TextView date = findViewById(R.id.date);
        TextView amountTV = findViewById(R.id.amount);

        int amount = getIntent().getIntExtra("amount", 0);
        amountTV.setText("Rs. " + amount + "/-");
        LocalDate tempDate = null;

        try {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                tempDate = LocalDate.now().plusDays(2);
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date.setText(tempDate.format(myFormatObj) + " 12:30PM");
            } else {
                date.setText("22/02/2021 12:30PM");
            }
        } catch (Exception e) {
            date.setText("22/02/2021 12:30PM");
        }
        try {


            String orderNo = getIntent().getStringExtra("orderNo");

            id.setText(orderNo);
        } catch (Exception e) {
            id.setText("234567871");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            HomeActivity.cartRef.removeValue();

        } catch (Exception e) {

        }
    }
}