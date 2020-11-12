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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {

    private Context mContext;
    private List<Event> allEvents_list;
    private RecyclerViewClickListener listener;
    private ArrayList<modelClass> posters;

    public RecyclerViewAdapter(Context mContext,ArrayList<modelClass> posters, List<Event> allEvents_list,RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.allEvents_list = allEvents_list;
        this.listener=listener;
        this.posters=posters;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.card_view_item_layout,parent,false);

        return new myViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //holder.poster.setImageBitmap(posters.get(position).getImage());
        int no=Integer.parseInt(allEvents_list.get(position).getNo());
        holder.poster.setImageBitmap(posters.get(no-1).getImage());

        holder.event_title.setText(allEvents_list.get(position).getNo()+". "+allEvents_list.get(position).getName());
        holder.org_name.setText(allEvents_list.get(position).getOrganisation());
        holder.genre.setText(allEvents_list.get(position).getType());
        holder.date.setText(allEvents_list.get(position).getStart_date());
        holder.time.setText(allEvents_list.get(position).getStart_time());
        //holder.poster.setImageBitmap(allEvents_list.get(position).getPoster());

    }

    @Override
    public int getItemCount() {
        return allEvents_list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView event_title,org_name,genre,date,time;
        ImageView poster;
        RecyclerViewClickListener recyclerViewClickListener;
        //ImageView poster;
        public myViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            event_title = itemView.findViewById(R.id.event_name_textView);
            org_name = itemView.findViewById(R.id.org_name_textView);
            genre = itemView.findViewById(R.id.genre_textView);
            date = itemView.findViewById(R.id.date_textView);
            time = itemView.findViewById(R.id.time_textView);
            poster=itemView.findViewById(R.id.poster_image_view);
            this.recyclerViewClickListener=recyclerViewClickListener;
            //poster = itemView.findViewById(R.id.poster_image_view);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(getAdapterPosition());

        }
    }
}
