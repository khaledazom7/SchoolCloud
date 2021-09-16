package com.amjad.myapplicationschool.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class UsersAdapter extends FirestoreRecyclerAdapter<User, UsersAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public UsersAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User model) {
        Picasso.get()
                .load(model.getUserImage())
                .into(holder.userImageView);
        holder.userNameTextView.setText(model.getName());
        holder.userEmailTextView.setText(model.getEmail());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImageView;
        TextView userNameTextView;
        TextView userEmailTextView;
        ConstraintLayout constraintLayoutUserItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.imageViewUser);
            userNameTextView = itemView.findViewById(R.id.textViewName);
            userEmailTextView = itemView.findViewById(R.id.textViewEmail);
            constraintLayoutUserItem = itemView.findViewById(R.id.constraintLayout_user_item_id);
            constraintLayoutUserItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null) {
                        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(getAdapterPosition());
                        listener.onItemClick(documentSnapshot, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void onItemSetOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
