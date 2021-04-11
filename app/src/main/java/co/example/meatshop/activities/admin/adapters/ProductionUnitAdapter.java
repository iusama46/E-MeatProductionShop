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

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.AddProductionUnitActivity;
import co.example.meatshop.activities.admin.models.ProductionUnit;

/**
 * Created by Ussama Iftikhar on 27-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class ProductionUnitAdapter extends RecyclerView.Adapter<ProductionUnitAdapter.ViewHolder> {

    List<ProductionUnit> productionUnitList;
    DatabaseReference reference;

    public ProductionUnitAdapter(List<ProductionUnit> productionUnitList, DatabaseReference reference) {
        this.productionUnitList = productionUnitList;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ProductionUnitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_unit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionUnitAdapter.ViewHolder holder, int position) {
        holder.phone.setText("Phone# " + productionUnitList.get(position).getPhone());
        holder.location.setText("Location: " + productionUnitList.get(position).getLocation());
        holder.name.setText(productionUnitList.get(position).getName());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(productionUnitList.get(position).getId()).removeValue();
                Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AddProductionUnitActivity.class).putExtra("id", position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productionUnitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, phone;
        ImageView del, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            phone = itemView.findViewById(R.id.payment);
            del = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.modify);
        }
    }
}
