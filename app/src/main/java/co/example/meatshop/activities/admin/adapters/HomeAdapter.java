package co.example.meatshop.activities.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.example.meatshop.R;
import co.example.meatshop.activities.admin.models.Home;

/**
 * Created by Ussama Iftikhar on 15-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<Home> homeList;
    private HomeAdapter.onItemClickListener mItemClickListener;

    public HomeAdapter(List<Home> homeList) {
        this.homeList = homeList;
    }

    public void setOnItemClickListener(HomeAdapter.onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(homeList.get(position).getImageId());
        holder.title.setText(homeList.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public interface onItemClickListener {
        void onItemClickListener(View view, int position, Home myData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClickListener(view, getAdapterPosition(), homeList.get(getAdapterPosition()));
                    }
                }
            });
        }


    }
}
