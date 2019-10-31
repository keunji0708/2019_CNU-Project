package com.example.professor_project.Chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.professor_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String nick = "Professor";

    private FirebaseUser firebaseUser;

    private EditText EditText_chat;
    private Button Button_send;
    private Button Button_out;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button_send = findViewById(R.id.Button_send);
        Button_out = findViewById(R.id.Button_out);

        EditText_chat = findViewById(R.id.EditText_chat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = EditText_chat.getText().toString(); //msg내용

                if(msg != null) {//메시지 널체크! 빈칸은 서버로 보내지 않도록!
                    ChatData chat = new ChatData();
                    chat.setNickname(nick);
                    chat.setMsg(msg);
                    myRef.child(firebaseUser.getUid()).push().setValue(chat);
                    EditText_chat.setText(""); //빈칸으로 만들어주기
                }


            }
        });

        Button_out.setOnClickListener(new View.OnClickListener() { //나가기 버튼 누르면 다 지워지기
            @Override
            public void onClick(View v) {
                String msg = "상대방이 나갔습니다."; //msg내용
                ChatData chat = new ChatData();
                chat.setNickname("  ");
                chat.setMsg(msg);
                myRef.child(firebaseUser.getUid()).push().setValue(chat);
                myRef.child(firebaseUser.getUid()).removeValue();//다 지우기
                finish();//뒤로가기
            }
        });


        //리사이클러뷰 세팅
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어댑터세팅
        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nick);
        mRecyclerView.setAdapter(mAdapter);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //채팅데이터를 최초데이터를 넣어서


        //애드차이들에서 잘 받아와서 넣어주도록
        myRef.child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() { //실시간데이터베이스 아래에 잇는 애들이 모두 차일드
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                ChatData chat = dataSnapshot.getValue(ChatData.class); //자동으로 챗데이터에서 받아옴 (에러가 안나면!)
                ((ChatAdapter)mAdapter).addChat(chat);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {            }
        });

        updateToken();
    }

    private void updateToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String uid = FirebaseAuth.getInstance().getUid();

        if(uid != null){
            FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .update("token", token);
        }
    }
}