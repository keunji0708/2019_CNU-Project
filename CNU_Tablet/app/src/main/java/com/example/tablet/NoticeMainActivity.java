package com.example.tablet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tablet.View.NoticeInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class NoticeMainActivity extends AppCompatActivity {
    private static final String TAG = "NoticeMainActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private NoticeAdapter mainAdapter;
    private ArrayList<NoticeInfo> noticeList;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            myStartActivity(LoginActivity.class);
        } else {//로그인에 성공한 경우
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference docRef = firebaseFirestore.collection("users")
                    .document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

        }

        util = new Util(this);
        noticeList = new ArrayList<>();
        mainAdapter = new NoticeAdapter(NoticeMainActivity.this, noticeList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeMainActivity.this));
        recyclerView.setAdapter(mainAdapter);
    }


    @Override
    protected void onResume(){
        super.onResume();
        noticeUpdate();
    }

    private void noticeUpdate() {//컬렉션의 여러문서 가져오기(데이터 가져오기)
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("notices")
                    .document("userID").collection(firebaseUser.getUid());;
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                noticeList.clear(); // 안에 있는 데이터를 비워줌
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    // 현재 로그인한 아이디가 쓴 공지사항만 불러옴
                                    if(firebaseUser.getUid().equals(document.getData().get("publisher").toString())) {
                                        noticeList.add(new NoticeInfo(
                                                document.getData().get("title").toString(),
                                                document.getData().get("contents").toString(), //(ArrayList<String) document.getData().get("contents")
                                                document.getData().get("publisher").toString(),
                                                new Date(document.getDate("createdAt").getTime()),
                                                document.getId()));
                                    }
                                    Log.e("로그: ", "데이터: " + document.getData().get("title").toString());
                                }
                                mainAdapter.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Error getting documents:", task.getException());
                            }
                        }
                    });
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        //22:18 part5 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
