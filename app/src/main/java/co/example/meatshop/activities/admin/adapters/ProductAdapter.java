package co.example.meatshop.activities.admin.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.AddProductActivity;
import co.example.meatshop.activities.admin.models.Product;

/**
 * Created by Ussama Iftikhar on 19-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> productList;
    DatabaseReference reference;

    public ProductAdapter(List<Product> productList, DatabaseReference reference) {
        this.productList = productList;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_product_item, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.name.setText(productList.get(position).getpName());
        holder.qty.setText("Qty: " + productList.get(position).getQty());
        holder.stock.setText("Stock: " + productList.get(position).getStock());
        holder.expiry.setText("Expiry: " + productList.get(position).getExpiryDate());

        Glide
                .with(holder.itemView.getContext())
                .load(productList.get(position).getpImg())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.img);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(productList.get(position).getId());
                Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AddProductActivity.class).putExtra("id", position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, expiry, stock;
        ImageView del, modify, img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            qty = itemView.findViewById(R.id.qty);
            expiry = itemView.findViewById(R.id.expiry);
            stock = itemView.findViewById(R.id.stock);
            del = itemView.findViewById(R.id.delete);
            modify = itemView.findViewById(R.id.modify);
            img = itemView.findViewById(R.id.img);
        }
    }
}
