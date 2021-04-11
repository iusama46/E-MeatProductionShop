package co.example.meatshop.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.LoginActivity;
import co.example.meatshop.activities.SplashActivity;
import co.example.meatshop.activities.admin.adapters.HomeAdapter;
import co.example.meatshop.activities.admin.adapters.SpacesItemDecoration;
import co.example.meatshop.activities.admin.models.Home;

public class HomeActivity extends AppCompatActivity {

    RecyclerView home;
    HomeAdapter adapter;
    List<Home> homeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home = findViewById(R.id.rv);
        homeList = new ArrayList<>();


        GridLayoutManager manager = new GridLayoutManager(this, 2);


        home.setLayoutManager(manager);
        home.setNestedScrollingEnabled(false);


        homeList.add(new Home("Products", R.drawable.ic_outline_shopping_bag_24));
        homeList.add(new Home("Add Product", R.drawable.ic_outline_add_to_photos_24));

        homeList.add(new Home("Orders History", R.drawable.ic_outline_history_24));
        homeList.add(new Home("Production Units", R.drawable.ic_outline_account_balance_24));

        homeList.add(new Home("Add Dealer", R.drawable.ic_outline_person_add_24));
        homeList.add(new Home("Payments", R.drawable.ic_outline_attach_money_24));


        homeList.add(new Home("Staff", R.drawable.ic_outline_group_24));
        homeList.add(new Home("Add Staff", R.drawable.ic_outline_person_add_24));
        homeList.add(new Home("Add ProductionUnit", R.drawable.ic_outline_add_circle_outline_24));


        adapter = new HomeAdapter(homeList);
        home.setAdapter(adapter);
        home.addItemDecoration(new SpacesItemDecoration(50));
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new HomeAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Home myData) {

                if (homeList.get(position).getItemName().equals(homeList.get(0).getItemName()))
                    startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                if (homeList.get(position).getItemName().equals(homeList.get(1).getItemName()))
                    startActivity(new Intent(HomeActivity.this, AddProductActivity.class));
                if (homeList.get(position).getItemName().equals(homeList.get(2).getItemName()))
                    startActivity(new Intent(HomeActivity.this, OrdersHistoryActivity.class));

                if (homeList.get(position).getItemName().equals(homeList.get(3).getItemName()))
                    startActivity(new Intent(HomeActivity.this, ProductionListActivity.class));
                if (homeList.get(position).getItemName().equals(homeList.get(4).getItemName()))
                    startActivity(new Intent(HomeActivity.this, AddDealerActivity.class));

                if (homeList.get(position).getItemName().equals(homeList.get(5).getItemName()))
                    startActivity(new Intent(HomeActivity.this, PaymentHistoryActivity.class));

                if (homeList.get(position).getItemName().equals(homeList.get(6).getItemName()))
                    startActivity(new Intent(HomeActivity.this, StaffListActivity.class));


                if (homeList.get(position).getItemName().equals(homeList.get(7).getItemName()))
                    startActivity(new Intent(HomeActivity.this, AddStaffActivity.class));
                if (homeList.get(position).getItemName().equals(homeList.get(8).getItemName()))
                    startActivity(new Intent(HomeActivity.this, AddProductionUnitActivity.class));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_nav) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}