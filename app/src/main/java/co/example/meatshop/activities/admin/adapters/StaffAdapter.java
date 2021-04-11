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
import co.example.meatshop.activities.admin.AddStaffActivity;
import co.example.meatshop.activities.admin.models.Staff;

/**
 * Created by Ussama Iftikhar on 27-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    List<Staff> staffList;
    DatabaseReference reference;

    public StaffAdapter(List<Staff> staffList, DatabaseReference reference) {
        this.staffList = staffList;
        this.reference = reference;
    }

    @NonNull
    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_staff_iten, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.ViewHolder holder, int position) {

        holder.location.setText("Location: " + staffList.get(position).getLocation());
        holder.name.setText("Person Name: " + staffList.get(position).getName());
        holder.phone.setText("Role: " + staffList.get(position).getRole());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(staffList.get(position).getId()).removeValue();
                Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AddStaffActivity.class).putExtra("id", position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
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
