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
    private int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    public ClassSettingsAdapter(@NonNull FirestoreRecyclerOptions<ClassModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ClassSettingsAdapter.ViewHolder holder, int position, @NonNull ClassModel model) {
        switch (type){
            case 0://Number
                fillText(model.getNumber(), holder.number);
                fillText(model.getNumberEn(), holder.number_en);
                holder.section.setVisibility(View.GONE);
                holder.section_en.setVisibility(View.GONE);
                break;
            case 1://Section
                fillText(model.getSection(),holder.section );
                fillText(model.getSectionEn(),holder.section_en );
                holder.number.setVisibility(View.GONE);
                holder.number_en.setVisibility(View.GONE);
                break;
            case 2://Class Name

                break;
        }
        /*fillText(model.getNumber(), holder.number);
        fillText(model.getNumberEn(), holder.number_en);
        fillText(model.getSection(), holder.section);
        fillText(model.getSectionEn(), holder.section_en);*/
    }

    private void fillText(String value, TextView textView) {
        if (value.isEmpty())
            textView.setVisibility(View.GONE);
        else
            textView.setText(value);
    }


    @NonNull
    @Override
    public ClassSettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.class_item, parent, false);
        return new ClassSettingsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number, number_en, section, section_en;
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
