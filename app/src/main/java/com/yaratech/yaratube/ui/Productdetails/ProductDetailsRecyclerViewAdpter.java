package com.yaratech.yaratube.ui.Productdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsRecyclerViewAdpter extends RecyclerView.Adapter<ProductDetailsRecyclerViewAdpter.ViewHolder> {

    private List<Comment> comments = new ArrayList<>();
    private Context context;

    public ProductDetailsRecyclerViewAdpter(Context context) {
        this.context = context;
    }

    public void setData(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }

        public void onBind(Comment comment) {
            username.setText(comment.getUser());
            this.comment.setText(comment.getCommentText());
        }
    }
}
