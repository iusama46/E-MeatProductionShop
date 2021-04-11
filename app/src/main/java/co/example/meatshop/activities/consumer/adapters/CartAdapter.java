package co.example.meatshop.activities.consumer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.models.Cart;

/**
 * Created by Ussama Iftikhar on 22-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Cart> cartList;

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        holder.qty.setText("Qty 1 X " + cartList.get(position).getQty());
        holder.price.setText("Rs " + cartList.get(position).getPrice() + "/-");
        holder.title.setText(cartList.get(position).getName());


        Glide
                .with(holder.itemView.getContext())
                .load(cartList.get(position).getImg())
                .centerCrop()
                .placeholder(R.drawable.ic_outline_delete_forever_24)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, qty, price;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
        }
    }
}
