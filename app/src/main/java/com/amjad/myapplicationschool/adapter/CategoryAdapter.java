package com.amjad.myapplicationschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.model.Category;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private Context context;
    private ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category currentCategory = categoryArrayList.get(position);
        holder.image.setImageResource(currentCategory.getImage());
        holder.title.setText(currentCategory.getTitle());
        holder.subTitle.setText(currentCategory.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, subTitle;
        ConstraintLayout categoryItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_category);
            title = itemView.findViewById(R.id.textView_title);
            subTitle = itemView.findViewById(R.id.textView_sub_title);
            categoryItem = itemView.findViewById(R.id.category_item);
            categoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(categoryArrayList.get(getAdapterPosition()).getId());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int categoryID);
    }

    public void onItemSetOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
