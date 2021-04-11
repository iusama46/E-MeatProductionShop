package co.example.meatshop.activities.consumer.adapters;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.consumer.CategoryActivity;
import co.example.meatshop.activities.consumer.models.Promotion;

/**
 * Created by Ussama Iftikhar on 23-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    List<Promotion> promotionList;

    public PromotionAdapter(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.lay_promotion_item, parent, false);
        return new PromotionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(promotionList.get(position).getImg());
        holder.cat.setText("Category: " + promotionList.get(position).getCat());

        LocalDate tempDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tempDate = LocalDate.now().plusDays(promotionList.get(position).getDays());
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            holder.date.setText("Valid till " + myFormatObj.format(tempDate));
        } else {
            holder.date.setText("Valid till 31/12/2021");
        }


        holder.title.setText(promotionList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), CategoryActivity.class).putExtra("cat", promotionList.get(position).getIndex()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, cat, date;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            cat = itemView.findViewById(R.id.cat);
            date = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img);

        }
    }
}
