package com.example.tablet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tablet.View.NoticeInfo;
import com.example.tablet.View.StateInfo;
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

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private NoticeAdapter myAdapter;
    private StateAdapter stateAdapter;

    private long time = 0;
    private String name;
    private String name_class;

    private RecyclerView recyclerViewNotice, recyclerViewState;
    private ArrayList<NoticeInfo> noticeList;
    private ArrayList<StateInfo>  stateList;

    private TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
    private String day, getTime;
    private int nowH, startH, endH;

    MainThread backgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final TextView roomTestView = findViewById(R.id.roomNumber);//교수실
        final TextView profNameTextView = findViewById(R.id.profName);//교수 이름

        if (firebaseUser == null) {
            myStartActivity(LoginActivity.class);
        } else {//로그인에 성공한 경우
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference docRef = firebaseFirestore.collection("users")
                    .document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    roomTestView.setText(task.getResult().getData().get("room").toString());
                    profNameTextView.setText(task.getResult().getData().get("name").toString());

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

        findViewById(R.id.chatButton).setOnClickListener(onClickListener);
        findViewById(R.id.noticeListButton).setOnClickListener(onClickListener);
        findViewById(R.id.logout_b).setOnClickListener(onClickListener);

        startRecycler();
        showState();
        showNotice();
    }

    private final MyHandler  mHandler = new MyHandler(this);

    // 핸들러 객체 만들기
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    // Handler 에서 호출하는 함수
    public void handleMessage(Message msg) {
        showState();
        showNotice();
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundThread = new MainThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();
        //Toast.makeText(this, "onStart()", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        boolean retry = true;
        backgroundThread.setRunning(false);

        while (retry) {
            try {
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        //Toast.makeText(this, "onStop()", Toast.LENGTH_LONG).show();
    }

    public class MainThread extends Thread {
        boolean running = false;

        void setRunning(boolean b) {
            running = b;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { // 버튼 클릭
            switch (view.getId()) {
                case R.id.chatButton:
                    myStartActivity(ChatLogin.class);
                    break;

                case R.id.noticeListButton:
                    myStartActivity(NoticeMainActivity.class);
                    break;

                case R.id.logout_b:
                    Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;

            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        startRecycler();
        showState();
        showNotice();
    }


    private void showState() {
        getTime = getDateTime();
        day = getDateDay();
        nowH = getHour(getTime);

        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("schedules")
                    .document("userID").collection(firebaseUser.getUid());
            collectionReference
                    .whereEqualTo("day", day)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                stateList.clear(); // 안에 있는 데이터를 비워줌
                                stateList.add(0, new StateInfo("재실"));

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.e(TAG, document.getId() + " => " + document.getData());
                                    startH = getHour(document.getData().get("startAt").toString());
                                    endH = getHour(document.getData().get("endAt").toString());
                                    if (nowH >= startH && nowH < endH) {
                                        stateList.set(0, new StateInfo(document.getData().get("state").toString()));
                                    }

                                }
                                stateAdapter.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Error getting documents:", task.getException());
                            }
                        }
                    });
        }
    }

    private void showNotice() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("notices")
                    .document("userID").collection(firebaseUser.getUid());
            collectionReference
                    .orderBy("createdAt", Query.Direction.DESCENDING).limit(1)//하나의 데이터만 가져옴
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                noticeList.clear(); // 안에 있는 데이터를 비워줌
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    // 현재 로그인한 아이디가 쓴 공지사항만 불러옴
                                    //if (firebaseUser.getUid().equals(document.getData().get("publisher").toString())) {
                                    Log.e(TAG, "가장 최신 글 -> " + document.getData().get("title").toString());

                                    noticeList.add(new NoticeInfo(
                                            document.getData().get("title").toString(),
                                            document.getData().get("contents").toString(), //(ArrayList<String) document.getData().get("contents")
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));
                                    // }
                                    //Log.e("로그: ", "데이터: " + document.getData().get("title").toString());
                                }
                                myAdapter.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Error getting documents:", task.getException());
                            }
                        }
                    });

        }
    }

    private void startRecycler(){
        noticeList = new ArrayList<>();
        myAdapter = new NoticeAdapter(MainActivity.this, noticeList);

        recyclerViewNotice = findViewById(R.id.recyclerViewNotice);//공지사항 화면
        recyclerViewNotice.setHasFixedSize(true);
        recyclerViewNotice.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewNotice.setAdapter(myAdapter);

        stateList = new ArrayList<>();
        stateAdapter = new StateAdapter(MainActivity.this, stateList);

        recyclerViewState = findViewById(R.id.recyclerViewState);//공지사항 화면
        recyclerViewState.setHasFixedSize(true);
        recyclerViewState.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewState.setAdapter(stateAdapter);

    }

    public String getDateDay() {
        final String[] week = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 9);
        Log.d(TAG,"달력 시간 : "+calendar.get(Calendar.HOUR_OF_DAY)); // 현재보다 9시간 늦음

        String today = week[calendar.get(Calendar.DAY_OF_WEEK)-1];
        Log.d(TAG, "오늘 요일(day) -> " + day);

        return today;
    }

    public String getDateTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        sdf.setTimeZone(seoul);

        String time = sdf.format(date);
        Log.d(TAG, "현재 시각(Hour) -> " + time);

        return time;
    }

    public int getHour(String time) {
        String temp = time.substring(0, 2);
        int hour = Integer.parseInt(temp);

        return hour;
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time >= 2000){
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다." ,Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time <2000){
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


}

