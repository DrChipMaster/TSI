package com.drchip.ihelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    ItemClicked activity;

    public interface ItemClicked  // Interface serve para passar eventos e assim entre classes, ou seja criar uma interface entre classes!
    {
        void onItemClicked(int index);
    }

    public  PostAdapter(Context context, ArrayList<Post> list)
    {
        posts=list;
        activity=(ItemClicked)context;
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivProfilePicture, ivPostImage, ivShare, ivLike, ivComment;
        TextView tvTitle, tvName, tvDate, tvDescription, tvLikes, tvComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.onItemClicked(posts.indexOf((Post) view.getTag()));  // como obter o index do item clicado!!

                }
            });
        }
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemns,parent,false);

        return new ViewHolder(v);         //muito importante!!!!para linkar com o ivPref por exemplo
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
//        holder.itemView.setTag(posts.get(position));   // quando algem segura o cenas guarda o index!!!!
//
//        holder.tvName.setText(posts.get(position).getName());
//        holder.tvSurname.setText(posts.get(position).getSurname());
//
//        if(posts.get(position).getPreference().equals("bus"))
//        {
//            holder.ivPref.setImageResource(R.drawable.bus);
//        }
//        else
//        {
//            holder.ivPref.setImageResource(R.drawable.plane);
//
//        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
