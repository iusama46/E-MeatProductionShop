package co.example.meatshop.activities.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.models.Payment;
import co.example.meatshop.activities.admin.models.Product;

/**
 * Created by Ussama Iftikhar on 25-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    List<Payment> productList;

    public PaymentAdapter(List<Payment> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_payemnt_item, parent, false);
        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {

        holder.method.setText("Payment method: "+productList.get(position).getPaymentMethod());
        holder.amount.setText("Total amount: Rs." +productList.get(position).getAmount()+"/-");
        holder.status.setText("Status: "+productList.get(position).getStatus());
        holder.id.setText("ID: "+productList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, amount, status, method;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            method = itemView.findViewById(R.id.method);
            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
