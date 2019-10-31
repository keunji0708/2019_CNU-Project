package com.example.tablet;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {
        //{"1","2"} 젤 처음에 필요한거 가져오는거,
        mDataset = myDataset;
        this.myNickName = myNickName;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, //어떤 레이아웃 쓸 지 정해
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //뷰홀더에서 데이터 세팅
        ChatData chat = mDataset.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg());

        if(chat.getNickname().equals("Professor")) { //닉네임 비교!해서 같으면 오른쪽 정렬 내꺼니까
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        else {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END); //닉네임 가져온것을 넣어줌
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END); //메시지 가져온거 넣어줭
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 :  mDataset.size();
    }

    public ChatData getChat(int position) { //데이터 반환하는거
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
        //온바인더홀더에서 뺑뺑이를 도는데 몇번째 데이터가 들어가는지 알아야되니까, 갱신용
    }

}