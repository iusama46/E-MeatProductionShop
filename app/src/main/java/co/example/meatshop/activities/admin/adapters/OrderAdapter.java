package co.example.meatshop.activities.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.models.Order;

/**
 * Created by Ussama Iftikhar on 27-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_order_item, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.date.setText("Delivery Date: " + orderList.get(position).getDeliveryDate());
        holder.amount.setText("Rs." + orderList.get(position).getAmount() + "/-");
        holder.id.setText("Order# " + orderList.get(position).getOrderId());
        holder.name.setText("Customer Name: " + orderList.get(position).getCustName());
        holder.note.setText("Note: " + orderList.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, amount, date, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.customer_name);
            id = itemView.findViewById(R.id.orderno);
            amount = itemView.findViewById(R.id.payment);
            date = itemView.findViewById(R.id.deliverydate);
            note = itemView.findViewById(R.id.note);

        }
    }
}
