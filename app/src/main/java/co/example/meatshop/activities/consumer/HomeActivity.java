package co.example.meatshop.activities.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import co.example.meatshop.R;
import co.example.meatshop.activities.LoginActivity;
import co.example.meatshop.activities.SplashActivity;
import co.example.meatshop.activities.consumer.fragments.CartFragment;
import co.example.meatshop.activities.consumer.fragments.HomeFragment;
import co.example.meatshop.activities.consumer.fragments.PromotionFragment;
import co.example.meatshop.utils.CShowProgress;

public class HomeActivity extends AppCompatActivity {
    public static CShowProgress cShowProgress = CShowProgress.getInstance();
    public static DatabaseReference cartRef;
    public static ChipNavigationBar chipNavigationBar;
    FrameLayout frameLayout;
    FirebaseAuth auth;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_consumer);

        frameLayout = findViewById(R.id.frame_lay);
        setFragment(new HomeFragment());
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.home_nav, true);
        //chipNavigationBar.showBadge(R.id.cart_nav, 12);
        auth = FirebaseAuth.getInstance();
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart").child(auth.getCurrentUser().getUid());
        //chipNavigationBar.getSelectedItemId();
        bottomMenu();
        updateCartNumber();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home_nav){
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCartNumber() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getChildrenCount()==0 || snapshot==null)
                chipNavigationBar.dismissBadge(R.id.cart_nav);
                else {
                    int temp = (int)snapshot.getChildrenCount();

                    chipNavigationBar.showBadge(R.id.cart_nav, temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (R.id.cart_nav == i)
                    setFragment(new CartFragment());
                else if (R.id.home_nav == i)
                    setFragment(new HomeFragment());

                else if (R.id.promotion_nav == i)
                    setFragment(new PromotionFragment());

            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(valueEventListener!=null)
            cartRef.removeEventListener(valueEventListener);
    }
}