package co.example.meatshop.activities.consumer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class HomeFragment extends Fragment {


    RecyclerView rv;
    HomeAdapter adapter;
    List<Product> productList;
    ValueEventListener valueEventListener;
    DatabaseReference reference;
    CShowProgress cShowProgress;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cShowProgress = CShowProgress.getInstance();
        auth = FirebaseAuth.getInstance();
        cShowProgress.showProgress(view.getContext());
        rv = view.findViewById(R.id.rv);
        productList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("products");

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new HomeAdapter(productList, auth.getUid());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getProducts();
        return view;
    }

    private void getProducts() {

        valueEventListener = reference.orderByKey().addValueEventListener(new ValueEventListener() {
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

                    if (!productList.isEmpty()) {
                        cShowProgress.hideProgress();
                    }
                    productList.add(product);
                    adapter = new HomeAdapter(productList, auth.getUid().toString());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
}