package co.example.meatshop.activities.consumer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.adapters.PromotionAdapter;
import co.example.meatshop.activities.consumer.models.Promotion;

;


public class PromotionFragment extends Fragment {


    List<Promotion> promotionsList;


    RecyclerView rv;
    PromotionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promotion, container, false);


        rv = view.findViewById(R.id.rv);
        promotionsList = new ArrayList<>();

//        categoriesList.add("Mutton");
//        categoriesList.add("Beef");
//        categoriesList.add("Chicken");
//        categoriesList.add("Chicken Dasi");
//        categoriesList.add("Fish");
        promotionsList.add(new Promotion("25% off Azadi Sale",R.drawable.chicken,"Chicken",9,2));
        promotionsList.add(new Promotion("50% Cash back offer",R.drawable.beefmutton,"Mutton",90,0));
        promotionsList.add(new Promotion("25% off cash back",R.drawable.fish,"Fish",45,4));
        promotionsList.add(new Promotion("25% off on beef",R.drawable.beef,"Beef",10,1));
        promotionsList.add(new Promotion("65% off on Debit Card",R.drawable.mutton,"Mutton",80,0));


        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());

        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        adapter = new PromotionAdapter(promotionsList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}