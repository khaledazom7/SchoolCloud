package com.amjad.myapplicationschool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.model.ClassName;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<ClassName> {
    private OnItemClickListener listener;
    private Context context;
    private ArrayList<ClassName> spinnerArrayList;

    public SpinnerAdapter(Context context, ArrayList<ClassName> spinnerArrayList) {
        super(context, 0, spinnerArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_name_item, parent, false);
        }
        TextView title = convertView.findViewById(R.id.class_name);
        ConstraintLayout spinner_item = convertView.findViewById(R.id.spinner_item);
        ClassName currentItem = getItem(position);
        if (currentItem != null) {
            title.setText(currentItem.getTitle());
        }
        /*spinner_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, currentItem);
            }
        });*/
        return convertView;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, ClassName className);
    }

    public void onItemSetOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
