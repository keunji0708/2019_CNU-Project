package com.example.tablet;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tablet.View.NoticeInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private ArrayList<NoticeInfo> mDataset;
    private Activity activity;

    static class NoticeViewHolder extends  RecyclerView.ViewHolder{
        CardView cardView;
        NoticeViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    NoticeAdapter(Activity activity, ArrayList<NoticeInfo> myDataset){
        this.mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        final NoticeViewHolder noticeViewHolder = new NoticeViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });

        return noticeViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final NoticeViewHolder holder, int position){
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataset.get(position).getTitle());

        TextView contentsTextView = cardView.findViewById(R.id.contentsTextView);
        contentsTextView.setText(mDataset.get(position).getContents());

        //long now = System.currentTimeMillis();
        //Date date = new Date(now);
        SimpleDateFormat tempdate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        tempdate.setTimeZone(timeZone);

        TextView createdAtTextView = cardView.findViewById(R.id.createdAtTextView);
        createdAtTextView.setText( // 날짜 형식 변환 월(Month) 대문자!!
                tempdate.format(mDataset.get(position).getCreatedAt()));

        Log.e("로그: ", "데이터: "+mDataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
