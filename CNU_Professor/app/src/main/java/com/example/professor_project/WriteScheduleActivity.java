package com.example.professor_project;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.professor_project.View.ScheduleInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WriteScheduleActivity extends BasicActivity {
    private static final String TAG = "WriteScheduleActivity";
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<ScheduleInfo> scheduleList = new ArrayList<>();
    private ArrayList<ScheduleInfo> list = new ArrayList<>();
   // private Date start, end, nowDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_schedule);
        firebaseFirestore = FirebaseFirestore.getInstance();

        findViewById(R.id.scheduleSaveButton).setOnClickListener(onClickListener);
        findViewById(R.id.scheduleCancelButton).setOnClickListener(onClickListener);
    }

    private void uploadSchedule() {
        String scheduleTitle = ((EditText) findViewById(R.id.scheduleTitle)).getText().toString();
        String scheduleDay = SpinnerToString((Spinner) findViewById(R.id.scheduleDay));
        String scheduleState = SpinnerToString((Spinner) findViewById(R.id.scheduleState));
        String scheduleStartHour = SpinnerToString((Spinner) findViewById(R.id.scheduleStartHour));
        String scheduleStartMinute = SpinnerToString((Spinner) findViewById(R.id.scheduleStartMinute));
        String scheduleEndHour = SpinnerToString((Spinner) findViewById(R.id.scheduleEndHour));
        String scheduleEndMinute = SpinnerToString((Spinner) findViewById(R.id.scheduleEndMinute));


        String startDate = scheduleStartHour + ":" + scheduleStartMinute + ":00";
        String endDate = scheduleEndHour + ":" + scheduleEndMinute + ":00";

        if (scheduleState.length() > 0 && scheduleDay.length() > 0 &&
                scheduleStartHour.length() > 0 && scheduleEndHour.length() > 0) {
            //loaderLayout.setVisibility(View.VISIBLE);
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            //수정에 필요한 id
            String id = getIntent().getStringExtra("id");
            int flag = getIntent().getIntExtra("flag", 0);
            DocumentReference dr;

            if (id == null) {
                dr = firebaseFirestore.collection("schedules").document("userID")
                        .collection(user.getUid()).document(); // 스케줄 밑에 유저아이디 별로 들어감
            } else {
                dr = firebaseFirestore.collection("schedules").document("userID")
                        .collection(user.getUid()).document(id);
            }

            final DocumentReference documentReference = dr;
            //Log.e("로그:", "로그 id:" + id);
            //Log.e("로그:", "로그 documentReference.getId(): " + documentReference.get());

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Document data : " + document.getData());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with", task.getException());
                    }
                }
            });

            //scheduleUpdate();

            int start = Integer.parseInt(scheduleStartHour + scheduleStartMinute);
            int end = Integer.parseInt(scheduleEndHour + scheduleEndMinute);
            int count = 0;
            int size = 0;
            Log.e("크기 : ", Integer.toString(scheduleList.size()));

            for(int i = 0; i < scheduleList.size(); i++){
                String start_array[] = scheduleList.get(i).getStartAt().split(":");
                String end_array[] = scheduleList.get(i).getEndAt().split(":");
                int first = Integer.parseInt(start_array[0] + start_array[1]);
                int last = Integer.parseInt(end_array[0] + end_array[1]);
                if(scheduleList.get(i).getDay().equals(scheduleDay)){
                    size++;
                    if(last <= start){
                        count++;
                    }
                    else if(first >= end){
                        count++;
                    }
                }
            }

            if(start - end >= 0){
                startToast("시간을 제대로 입력해주세요.");
            }
            else if(count != size && flag == 0){
                startToast("시간 중복");
            }
            else {
                list.add(new ScheduleInfo(scheduleTitle, scheduleState, scheduleDay, user.getUid(), startDate, endDate));
                ScheduleInfo scheduleInfo = new ScheduleInfo(scheduleTitle, scheduleState, scheduleDay, user.getUid(),
                        startDate, endDate);
                storeUpload(documentReference, scheduleInfo, flag);
            }
        }
        else{
                startToast("내용을 입력해주세요.");
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        scheduleUpdate();
    }

    private void scheduleUpdate(){
        CollectionReference collectionReference;
        collectionReference = firebaseFirestore.collection("schedules").document("userID").collection(user.getUid());
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    scheduleList.clear();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d(TAG, document.getId() + "=>" + document.getData());
                        //if(collectionReference.get("day")){
                            scheduleList.add(new ScheduleInfo(
                                    document.getData().get("title").toString(),
                                    document.getData().get("state").toString(),
                                    document.getData().get("day").toString(),
                                    document.getData().get("publisher").toString(),
                                    document.getData().get("startAt").toString(),
                                    document.getData().get("endAt").toString()));
                        //}
                    }

                }
                else{
                    Log.d(TAG, "Error getting document ", task.getException());
                }
            }

        });
    }

    private void storeUpload(DocumentReference documentReference, ScheduleInfo scheduleInfo, int flag) {
        final int f = flag;
        documentReference.set(scheduleInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(f == 0){
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            startToast("스케줄 등록에 성공하였습니다.");
                            //loaderLayout.setVisibility(View.GONE);
                        }
                        else{
                            startToast("스케줄 변경에 성공하였습니다.");
                        }
                        finish();
                        myStartActivity(ScheduleActivity.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        //loaderLayout.setVisibility(View.GONE);
                        startToast("스케줄 등록에 실패하였습니다.");
                    }
                });
    }

    private void startToast(String msg) { // 알림 창
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.scheduleSaveButton:
                    uploadSchedule();
                    break;

                case R.id.scheduleCancelButton:
                    finish();
                    break;

            }
        }
    };

    private String SpinnerToString(Spinner spinner){ // spinner에서 선택한 string 값 가져오기
        String mString = spinner.getSelectedItem().toString();
        return mString;
    }

    private Date StringToDate(Date mdate, String dateString){ // string 값을 date로 변환
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul"); // 우리나라 시간에 맞춤
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(timeZone);
        try {
            mdate = dateFormat.parse(dateString);
            Log.e("로그:", "스케줄 시간:" + mdate.toString());
        }catch(ParseException e){
            Log.e("Error", ""+e);
        }
        return mdate;
    }

    @Override public void onBackPressed() {
        //뒤로가기 안먹이게
    }
    
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
