package co.example.meatshop.activities.consumer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.CheckOutActivity;
import co.example.meatshop.activities.consumer.HomeActivity;
import co.example.meatshop.activities.consumer.adapters.CartAdapter;
import co.example.meatshop.activities.consumer.models.Cart;
import co.example.meatshop.utils.CShowProgress;


public class CartFragment extends Fragment {


    static List<Cart> cartList;
    Button proceedBtn;
    TextView total;
    RecyclerView rv;
    CartAdapter adapter;
    ValueEventListener valueEventListener;

    CShowProgress cShowProgress;
    RelativeLayout layout;
    int totalAmount = 0;

    public static List<Cart> getCartList() {
        return cartList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        proceedBtn = view.findViewById(R.id.proceed);
        total = view.findViewById(R.id.total);

        cShowProgress = CShowProgress.getInstance();

        layout = view.findViewById(R.id.lay_2);
        rv = view.findViewById(R.id.rv);
        cartList = new ArrayList<>();


        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new CartAdapter(cartList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CheckOutActivity.class).putExtra("amount", totalAmount));
            }
        });

        getProducts();
        return view;
    }

    private void getProducts() {
        cShowProgress.showProgress(getContext());
        valueEventListener = HomeActivity.cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Cart cart = new Cart();
                    cart.setName(dsp.child("name").getValue().toString());

                    cart.setImg(dsp.child("img").getValue().toString());
                    cart.setQty(dsp.child("quantity").getValue().toString());
                    cart.setPrice(dsp.child("price").getValue().toString());
                    cart.setpId(dsp.child("pId").getValue().toString());

                    cart.setId(dsp.getKey().toString());
                    totalAmount = totalAmount + Integer.parseInt(dsp.child("price").getValue().toString());
                    total.setText("Rs. " + String.valueOf(totalAmount) + "/-");

                    layout.setVisibility(View.VISIBLE);
                    cartList.add(cart);
                    adapter = new CartAdapter(cartList);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                if (cartList.isEmpty()) {
                    layout.setVisibility(View.INVISIBLE);
                    cShowProgress.hideProgress();
                    Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                    adapter = new CartAdapter(cartList);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                cShowProgress.hideProgress();
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
            HomeActivity.cartRef.removeEventListener(valueEventListener);
    }
}