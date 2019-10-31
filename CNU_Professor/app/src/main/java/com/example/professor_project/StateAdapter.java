package com.example.professor_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professor_project.View.StateInfo;

import java.util.ArrayList;


public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {
    private ArrayList<StateInfo> mDataset;
    private Activity activity;

    static class StateViewHolder extends  RecyclerView.ViewHolder{
        CardView cardView;
        StateViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    StateAdapter(Activity activity, ArrayList<StateInfo> myDataset){
        this.mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_state, parent, false);
        final StateViewHolder stateViewHolder = new StateViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });


        return stateViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final StateViewHolder holder, int position){
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.stateTextView);
        titleTextView.setText(mDataset.get(position).getState());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
