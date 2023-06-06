package com.example.perpusdesa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.perpusdesa.R;
import com.example.perpusdesa.model.Bookmark;
import com.example.perpusdesa.model.PepusModel;

import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.MyViewHolder> {

    private Context context;
    private List<Bookmark> bookmarkedList;
    private ItemClickListener clickListener;

    public BookmarkListAdapter(Context context, List<Bookmark> bookmarkedList, ItemClickListener clickListener) {
        this.context = context;
        this.bookmarkedList = bookmarkedList;
        this.clickListener = clickListener;
    }

    public void setBookmarkedList(List<Bookmark> bookmarkedList) {
        this.bookmarkedList = bookmarkedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bookmark bookmarkedBook = bookmarkedList.get(position);

        holder.tvTitle.setText(bookmarkedBook.getBookId());

        // Anda dapat menambahkan properti lain dari objek Bookmark sesuai kebutuhan

        // Menggunakan Glide untuk memuat gambar
        Glide.with(context)
                .load(bookmarkedBook.getImageUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onBookmarkClick(bookmarkedBook);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleView);
            imageView = itemView.findViewById(R.id.img_row);
        }
    }

    public interface ItemClickListener {
        void onBookmarkClick(Bookmark bookmarkedBook);
    }
}
