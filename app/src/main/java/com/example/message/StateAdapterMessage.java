package com.example.message;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StateAdapterMessage extends RecyclerView.Adapter<StateAdapterMessage.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<StateMessage> states;
    int i = 0;
    Date dateNow;
    SimpleDateFormat formatForDateNow;


    public StateAdapterMessage(Context context, List<StateMessage> states){
        this.states = states;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StateAdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.list_item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapterMessage.ViewHolder holder, int position) {
        StateMessage state = states.get(position);
        dateNow = new Date();
        Log.d("sasa", String.valueOf(position));
        holder.nameView2.setText(state.getName());
        holder.nameView.setText(state.getName());
        holder.time.setText(state.getTime());
        holder.time2.setText(state.getTime());
        if(state.getNick().toString().equals(BaseLogic.nick)) {
            holder.nameView2.setVisibility(View.VISIBLE);
            holder.time2.setVisibility(View.VISIBLE);
            holder.nameView.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);
        }else {
            holder.nameView2.setVisibility(View.GONE);
            holder.time2.setVisibility(View.GONE);
            holder.nameView.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public int getItemCount() {
        return states.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView nameView2;
        final TextView time;
        final TextView time2;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.message_list);
            nameView2 = view.findViewById(R.id.message_list2);
            time = view.findViewById(R.id.time1);
            time2 = view.findViewById(R.id.time2);
        }
    }
}
