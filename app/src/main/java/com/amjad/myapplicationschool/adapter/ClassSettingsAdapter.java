package com.amjad.myapplicationschool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
        switch (type) {
            case 0://Number
                //check_number_used(getSnapshots().getSnapshot(position).getId());
                FirebaseFirestore.getInstance().collection("ClassRoom").whereEqualTo("numberId", getSnapshots().getSnapshot(position).getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            if (task.getResult().getDocuments().size() > 0)
                                holder.buttonDelete.setVisibility(View.GONE);
                            else
                                holder.buttonDelete.setVisibility(View.VISIBLE);
                    }
                });
                fillText(model.getNumber(), holder.number);
                fillText(model.getNumberEn(), holder.number_en);
                holder.section.setVisibility(View.GONE);
                holder.section_en.setVisibility(View.GONE);
                break;
            case 1://Section
                FirebaseFirestore.getInstance().collection("ClassRoom").whereEqualTo("sectionId", getSnapshots().getSnapshot(position).getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            if (task.getResult().getDocuments().size() > 0)
                                holder.buttonDelete.setVisibility(View.GONE);
                            else
                                holder.buttonDelete.setVisibility(View.VISIBLE);
                    }
                });
                fillText(model.getSection(), holder.section);
                fillText(model.getSectionEn(), holder.section_en);
                holder.number.setVisibility(View.GONE);
                holder.number_en.setVisibility(View.GONE);
                break;
            case 2://Class Name
                FirebaseFirestore.getInstance()
                        .collection("ClassRoom")
                        .document(model.getNumberId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //fillText(task.getResult().toObject(ClassModel.class).getNumber(), holder.number);
                        //fillText(task.getResult().toObject(ClassModel.class).getNumberEn(), holder.number_en);
                        holder.number.setText(task.getResult().toObject(ClassModel.class).getNumber());
                        holder.number_en.setText(task.getResult().toObject(ClassModel.class).getNumberEn());
                    }
                });
                FirebaseFirestore.getInstance()
                        .collection("ClassRoom")
                        .document(model.getSectionId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        // fillText(task.getResult().toObject(ClassModel.class).getSection(), holder.section);
                        //fillText(task.getResult().toObject(ClassModel.class).getSectionEn(), holder.section_en);
                        holder.section.setText(task.getResult().toObject(ClassModel.class).getSection());
                        holder.section_en.setText(task.getResult().toObject(ClassModel.class).getSectionEn());
                    }
                });
                break;
        }
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
        ImageButton buttonDelete, buttonEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.textViewNumber);
            number_en = itemView.findViewById(R.id.textViewNumberEn);
            section = itemView.findViewById(R.id.textViewSection);
            section_en = itemView.findViewById(R.id.textViewSectionEn);
            classModelItem = itemView.findViewById(R.id.classModelItem);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteClass);
            buttonEdit = itemView.findViewById(R.id.buttonEditClass);
            classModelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), 1);
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), 0);
                }
            });
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(getAdapterPosition(), 1);
                }
            });
        }
    }

    public void onClickItem(int position, int type) {
        if (position != RecyclerView.NO_POSITION && listener != null) {
            DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
            listener.onItemClick(documentSnapshot, position, type);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position, int type);
    }

    public void onItemSetOnClickListener(ClassSettingsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
