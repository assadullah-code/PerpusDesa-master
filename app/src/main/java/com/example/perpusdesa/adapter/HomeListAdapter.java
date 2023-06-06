package com.example.perpusdesa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.perpusdesa.activity.DetailHomeActivity;
import com.example.perpusdesa.R;
import com.example.perpusdesa.model.PepusModel;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private Context context;
    private List<PepusModel> perpusList;
    private ItemClickListener clickListener;
    private List<PepusModel> pepusModelList = new ArrayList<>();

    public void setFilteredList(List<PepusModel> filteredList){
        this.perpusList = filteredList;
        notifyDataSetChanged();
    }

    public HomeListAdapter(Context context, List<PepusModel> perpusList, ItemClickListener clickListener) {
        this.context = context;
        this.perpusList = perpusList;
        this.clickListener = clickListener;

    }

    public void setPerpusList(List<PepusModel> perpusList) {
        this.perpusList = perpusList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(this.perpusList.get(position).getTitle().toString());
        holder.tvId.setText(this.perpusList.get(position).getId().toString());
        holder.tvDes.setText(this.perpusList.get(position).getDesciption().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHomeActivity.class);
                intent.putExtra("image",perpusList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("pdf", perpusList.get(holder.getAdapterPosition()).getUrl());
                context.startActivity(intent);
                clickListener.onPerpusClick(perpusList.get(position));
            }

        });
        Glide.with(context)
                .load(this.perpusList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        if(this.perpusList != null) {
            return this.perpusList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView tvTitle, tvId, tvDes;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.titleView);
            tvId = (TextView)itemView.findViewById(R.id.id);
            tvDes = (TextView)itemView.findViewById(R.id.deskripsi);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);

        }
    }


    public interface ItemClickListener{
        public void onPerpusClick(PepusModel pepusModel);
    }
}
