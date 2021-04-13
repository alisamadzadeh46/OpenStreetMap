package com.example.map.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.map.R;
import com.example.map.model.Search;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.viewholder> {
    List<Search> list;
    Context context;
    Point point;


    public AdapterSearch(List<Search> list, Context context, Point point) {
        this.list = list;
        this.context = context;
        this.point = point;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemssearch, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final Search search = list.get(position);
        holder.title.setText(search.getDisplay_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                point.ChangePoint(Double.parseDouble(search.getLat()), Double.parseDouble(search.getLon()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Point {
        void ChangePoint(Double lat, Double lon);
    }


    static class viewholder extends RecyclerView.ViewHolder {
        TextView title;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
