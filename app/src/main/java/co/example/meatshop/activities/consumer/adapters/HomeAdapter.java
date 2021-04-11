package co.example.meatshop.activities.consumer.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.models.Product;
import co.example.meatshop.activities.consumer.HomeActivity;
import co.example.meatshop.activities.consumer.ProductDisplayActivity;

/**
 * Created by Ussama Iftikhar on 19-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<Product> productList;
    String uId;

    public HomeAdapter(List<Product> productList, String uId) {
        this.productList = productList;
        this.uId = uId;
    }

//    public HomeAdapter(List<Product> productList) {
//        this.productList = productList;
//    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_home_consumer_item, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.price.setText("Rs " + productList.get(position).getPrice() + "/-");
        holder.title.setText(productList.get(position).getpName());
        holder.qty.setText(productList.get(position).getQty());

        Glide
                .with(holder.itemView.getContext())
                .load(productList.get(position).getpImg())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductDisplayActivity.class).putExtra("key", productList.get(position).getId()));
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductDisplayActivity.class).putExtra("key", productList.get(position).getId()));
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
                dataMap.put("name", productList.get(position).getpName());
                dataMap.put("pId", productList.get(position).getId());
                dataMap.put("quantity", productList.get(position).getQty());
                dataMap.put("price", productList.get(position).getPrice());
                dataMap.put("img", productList.get(position).getpImg());

                HomeActivity.cartRef.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(holder.itemView.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, qty;
        ImageView imageView;
        Button btn, view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            imageView = itemView.findViewById(R.id.img);
            btn = itemView.findViewById(R.id.add);
            view = itemView.findViewById(R.id.whatsapp);
        }
    }
}
