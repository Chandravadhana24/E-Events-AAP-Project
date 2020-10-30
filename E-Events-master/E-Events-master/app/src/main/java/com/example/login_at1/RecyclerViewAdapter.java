package com.example.login_at1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {

    private Context mContext;
    private List<Event> allEvents_list;

    public RecyclerViewAdapter(Context mContext, List<Event> allEvents_list) {
        this.mContext = mContext;
        this.allEvents_list = allEvents_list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.card_view_item_layout,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.event_title.setText(allEvents_list.get(position).getName());
        holder.org_name.setText(allEvents_list.get(position).getOrganisation());
        holder.genre.setText(allEvents_list.get(position).getType());
        holder.date.setText(allEvents_list.get(position).getStart_date().toString());
        //holder.poster.setImageBitmap(allEvents_list.get(position).getPoster());

    }

    @Override
    public int getItemCount() {
        return allEvents_list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView event_title,org_name,genre,date;
        //ImageView poster;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            event_title = itemView.findViewById(R.id.event_name_textView);
            org_name = itemView.findViewById(R.id.org_name_textView);
            genre = itemView.findViewById(R.id.genre_textView);
            date = itemView.findViewById(R.id.date_textView);
            //poster = itemView.findViewById(R.id.poster_image_view);
        }
    }
}
