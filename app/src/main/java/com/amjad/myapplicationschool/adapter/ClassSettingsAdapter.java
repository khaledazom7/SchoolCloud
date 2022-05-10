package com.amjad.myapplicationschool.adapter;

import android.annotation.SuppressLint;
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
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClassSettingsAdapter extends FirestoreRecyclerAdapter<ClassModel, ClassSettingsAdapter.ViewHolder> {

    private ClassSettingsAdapter.OnItemClickListener listener;

    public ClassSettingsAdapter(@NonNull FirestoreRecyclerOptions<ClassModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ClassSettingsAdapter.ViewHolder holder, int position, @NonNull ClassModel model) {
        holder.number.setText(model.getNumber());
        holder.number.setText(model.getNumberEn());
        holder.number.setText(model.getSection());
        holder.number.setText(model.getSectionEn());
    }

    @NonNull
    @Override
    public ClassSettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ClassSettingsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  number, number_en, section, section_en;
        ConstraintLayout classModelItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.textViewNumber);
            number_en = itemView.findViewById(R.id.textViewNumberEn);
            section = itemView.findViewById(R.id.textViewSection);
            section_en = itemView.findViewById(R.id.textViewSectionEn);
            classModelItem = itemView.findViewById(R.id.classModelItem);
            classModelItem.setOnClickListener(new View.OnClickListener() {
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

    public void onItemSetOnClickListener(ClassSettingsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
