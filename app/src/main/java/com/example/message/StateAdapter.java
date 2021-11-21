package com.example.message;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<State> states;
    String messageLong = " ...";
    public static Drawable image_up = null;
    public OnItemClick onItemClicks;


    public StateAdapter(Context context,List<State> states,OnItemClick onItemClicks){
        this.states = states;
        this.inflater = LayoutInflater.from(context);
        this.onItemClicks = onItemClicks;
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        if((state.getLastMessage()).length() > 22) messageLong = state.getLastMessage().substring(0,22) + "...";
            else messageLong = state.getLastMessage();
        holder.imageView.setImageResource(state.getProfileResource());
        holder.titleView.setText(state.getTitle());
        holder.lastUserView.setText(state.getLastUser());
        holder.lastMessageView.setText(messageLong);
        holder.timeView.setText(state.getTime());
        holder.imageView.setOnClickListener(view -> {
            onItemClicks.OnItemClick(states.get(position));
        });
        holder.lastMessageView.setOnClickListener(view ->{
            onItemClicks.OnItemClick(states.get(position));
        });


    }

    public interface OnItemClick{
        void OnItemClick(State states);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        image_up = holder.imageView.getDrawable();
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView titleView, lastUserView, timeView, lastMessageView;
        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.profile_image);
            titleView = view.findViewById(R.id.title);
            lastUserView = view.findViewById(R.id.lastUser);
            timeView = view.findViewById(R.id.time1);
            lastMessageView = view.findViewById(R.id.lastMessage);
        }
    }
}
