package com.example.professor_project;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.professor_project.Chat.ChatActivity;

import com.example.professor_project.View.StateInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends BasicActivity {
    private static final String TAG = "MainActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private StateAdapter stateAdapter;
    private ArrayList<StateInfo>  stateList;
    private RecyclerView recyclerViewState;

    private TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
    private int nowH, startH, endH;

    MainThread backgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final TextView roomTextView = findViewById(R.id.roomText);
        final TextView professorTextView = findViewById(R.id.professorText);

        if (firebaseUser == null) {
            myStartActivity(LoginActivity.class);
        } else { // 로그인에 성공한 경우
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
                                roomTextView.setText(task.getResult().getData().get("room").toString());
                                professorTextView.setText(task.getResult().getData().get("name").toString());
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                myStartActivity(MemberInitActivity.class);//회원정보 입력
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

        }

        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        findViewById(R.id.noticeWriteButton).setOnClickListener(onClickListener);
        findViewById(R.id.noticeListButton).setOnClickListener(onClickListener);
        findViewById(R.id.scheduleWriteButton).setOnClickListener(onClickListener);
        findViewById(R.id.ChatButton).setOnClickListener(onClickListener);

        startRecyclerView();
        showState();

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundThread = new MainThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();
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


    @Override
    protected void onResume() {
        super.onResume();
        startRecyclerView();
        showState();
    }

    private void showState() {
        String getTime = getDateTime();
        String day = getDateDay();
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
                                    Log.e(TAG, document.getId() + " => " + document.getData());

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


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { // 버튼 클릭
            switch (view.getId()) {
                case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;

                case R.id.noticeWriteButton:
                    myStartActivity(WriteNoticeActivity.class);
                    break;

                case R.id.noticeListButton:
                    myStartActivity(NoticeMainActivity.class);
                    break;

                case R.id.scheduleWriteButton:
                    myStartActivity(ScheduleActivity.class);
                    break;

                case R.id.ChatButton:
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void startRecyclerView(){
        stateList = new ArrayList<>();
        stateAdapter = new StateAdapter(MainActivity.this, stateList);

        recyclerViewState = findViewById(R.id.recyclerViewState);//공지사항 화면
        recyclerViewState.setHasFixedSize(true);
        recyclerViewState.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewState.setAdapter(stateAdapter);
    }

    public String getDateDay() {
        final String[] week = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
        Calendar calendar = Calendar.getInstance();  // 현재 날짜/시간 등의 각종 정보 얻기
        calendar.add(Calendar.HOUR, 9);
        Log.d(TAG,"달력 시간 : "+calendar.get(Calendar.HOUR_OF_DAY)); // 현재보다 9시간 늦음

        String day = week[calendar.get(Calendar.DAY_OF_WEEK)-1];
        Log.d(TAG, "오늘 요일(day) -> " + day);

        return day;
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

}
