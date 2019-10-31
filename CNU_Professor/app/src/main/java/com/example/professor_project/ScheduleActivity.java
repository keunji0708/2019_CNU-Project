package com.example.professor_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.professor_project.View.ScheduleInfo;
import com.example.professor_project.View.documentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "ScheduleActivity";
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<ScheduleInfo> scheduleList = new ArrayList<>();
    private ArrayList<documentInfo> documentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        firebaseFirestore = FirebaseFirestore.getInstance();
        onActivityCreated();

        final ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(onClickListener);

        if(!monday[0].equals("")){
            monday[0].setOnClickListener(onClickListener);
        }
        if(!monday[1].equals("")){
            Log.e("monday", monday[0].getText().toString());
            monday[1].setOnClickListener(onClickListener);
        }
        if(!monday[2].equals("")){
            monday[2].setOnClickListener(onClickListener);
        }
        if(!monday[3].equals("")){
            monday[3].setOnClickListener(onClickListener);
        }
        if(!monday[4].equals("")){
            monday[4].setOnClickListener(onClickListener);
        }
        if(!monday[5].equals("")){
            monday[5].setOnClickListener(onClickListener);
        }
        if(!monday[6].equals("")){
            monday[6].setOnClickListener(onClickListener);
        }
        if(!monday[7].equals("")){
            monday[7].setOnClickListener(onClickListener);
        }
        if(!monday[8].equals("")){
            monday[8].setOnClickListener(onClickListener);
        }
        if(!monday[9].equals("")){
            monday[9].setOnClickListener(onClickListener);
        }
        if(!tuesday[0].equals("")){
            tuesday[0].setOnClickListener(onClickListener);
        }
        if(!tuesday[1].equals("")){
            tuesday[1].setOnClickListener(onClickListener);
        }
        if(!tuesday[2].equals("")){
            tuesday[2].setOnClickListener(onClickListener);
        }
        if(!tuesday[3].equals("")){
            tuesday[3].setOnClickListener(onClickListener);
        }
        if(!tuesday[4].equals("")){
            tuesday[4].setOnClickListener(onClickListener);
        }
        if(!tuesday[5].equals("")){
            tuesday[5].setOnClickListener(onClickListener);
        }
        if(!tuesday[6].equals("")){
            tuesday[6].setOnClickListener(onClickListener);
        }
        if(!tuesday[7].equals("")){
            tuesday[7].setOnClickListener(onClickListener);
        }
        if(!tuesday[8].equals("")){
            tuesday[8].setOnClickListener(onClickListener);
        }
        if(!tuesday[9].equals("")){
            tuesday[9].setOnClickListener(onClickListener);
        }
        if(!wednesday[0].equals("")){
            wednesday[0].setOnClickListener(onClickListener);
        }
        if(!wednesday[1].equals("")){
            wednesday[1].setOnClickListener(onClickListener);
        }
        if(!wednesday[2].equals("")){
            wednesday[2].setOnClickListener(onClickListener);
        }
        if(!wednesday[3].equals("")){
            wednesday[3].setOnClickListener(onClickListener);
        }
        if(!wednesday[4].equals("")){
            wednesday[4].setOnClickListener(onClickListener);
        }
        if(!wednesday[5].equals("")){
            wednesday[5].setOnClickListener(onClickListener);
        }
        if(!wednesday[6].equals("")){
            wednesday[6].setOnClickListener(onClickListener);
        }
        if(!wednesday[7].equals("")){
            wednesday[7].setOnClickListener(onClickListener);
        }
        if(!wednesday[8].equals("")){
            wednesday[8].setOnClickListener(onClickListener);
        }
        if(!wednesday[9].equals("")){
            wednesday[9].setOnClickListener(onClickListener);
        }
        if(!thursday[0].equals("")){
            thursday[0].setOnClickListener(onClickListener);
        }
        if(!thursday[1].equals("")){
            thursday[1].setOnClickListener(onClickListener);
        }
        if(!thursday[2].equals("")){
            thursday[2].setOnClickListener(onClickListener);
        }
        if(!thursday[3].equals("")){
            thursday[3].setOnClickListener(onClickListener);
        }
        if(!thursday[4].equals("")){
            thursday[4].setOnClickListener(onClickListener);
        }
        if(!thursday[5].equals("")){
            thursday[5].setOnClickListener(onClickListener);
        }
        if(!thursday[6].equals("")){
            thursday[6].setOnClickListener(onClickListener);
        }
        if(!thursday[7].equals("")){
            thursday[7].setOnClickListener(onClickListener);
        }
        if(!thursday[8].equals("")){
            thursday[8].setOnClickListener(onClickListener);
        }
        if(!thursday[9].equals("")){
            thursday[9].setOnClickListener(onClickListener);
        }
        if(!friday[0].equals("")){
            friday[0].setOnClickListener(onClickListener);
        }
        if(!friday[1].equals("")){
            friday[1].setOnClickListener(onClickListener);
        }
        if(!friday[2].equals("")){
            friday[2].setOnClickListener(onClickListener);
        }
        if(!friday[3].equals("")){
            friday[3].setOnClickListener(onClickListener);
        }
        if(!friday[4].equals("")){
            friday[4].setOnClickListener(onClickListener);
        }
        if(!friday[5].equals("")){
            friday[5].setOnClickListener(onClickListener);
        }
        if(!friday[6].equals("")){
            friday[6].setOnClickListener(onClickListener);
        }
        if(!friday[7].equals("")){
            friday[7].setOnClickListener(onClickListener);
        }
        if(!friday[8].equals("")){
            friday[8].setOnClickListener(onClickListener);
        }
        if(!friday[9].equals("")){
            friday[9].setOnClickListener(onClickListener);
        }
    }

    private TextView monday[] = new TextView[10];
    private TextView tuesday[] = new TextView[10];
    private TextView wednesday[] = new TextView[10];
    private TextView thursday[] = new TextView[10];
    private TextView friday[] = new TextView[10];


    public void onActivityCreated(){
        monday[0] = (TextView) findViewById(R.id.monday0);
        monday[1] = (TextView) findViewById(R.id.monday1);
        monday[2] = (TextView) findViewById(R.id.monday2);
        monday[3] = (TextView) findViewById(R.id.monday3);
        monday[4] = (TextView) findViewById(R.id.monday4);
        monday[5] = (TextView) findViewById(R.id.monday5);
        monday[6] = (TextView) findViewById(R.id.monday6);
        monday[7] = (TextView) findViewById(R.id.monday7);
        monday[8] = (TextView) findViewById(R.id.monday8);
        monday[9] = (TextView) findViewById(R.id.monday9);

        tuesday[0] = (TextView) findViewById(R.id.tuesday0);
        tuesday[1] = (TextView) findViewById(R.id.tuesday1);
        tuesday[2] = (TextView) findViewById(R.id.tuesday2);
        tuesday[3] = (TextView) findViewById(R.id.tuesday3);
        tuesday[4] = (TextView) findViewById(R.id.tuesday4);
        tuesday[5] = (TextView) findViewById(R.id.tuesday5);
        tuesday[6] = (TextView) findViewById(R.id.tuesday6);
        tuesday[7] = (TextView) findViewById(R.id.tuesday7);
        tuesday[8] = (TextView) findViewById(R.id.tuesday8);
        tuesday[9] = (TextView) findViewById(R.id.tuesday9);

        wednesday[0] = (TextView) findViewById(R.id.wednesday0);
        wednesday[1] = (TextView) findViewById(R.id.wednesday1);
        wednesday[2] = (TextView) findViewById(R.id.wednesday2);
        wednesday[3] = (TextView) findViewById(R.id.wednesday3);
        wednesday[4] = (TextView) findViewById(R.id.wednesday4);
        wednesday[5] = (TextView) findViewById(R.id.wednesday5);
        wednesday[6] = (TextView) findViewById(R.id.wednesday6);
        wednesday[7] = (TextView) findViewById(R.id.wednesday7);
        wednesday[8] = (TextView) findViewById(R.id.wednesday8);
        wednesday[9] = (TextView) findViewById(R.id.wednesday9);

        thursday[0] = (TextView) findViewById(R.id.thursday0);
        thursday[1] = (TextView) findViewById(R.id.thursday1);
        thursday[2] = (TextView) findViewById(R.id.thursday2);
        thursday[3] = (TextView) findViewById(R.id.thursday3);
        thursday[4] = (TextView) findViewById(R.id.thursday4);
        thursday[5] = (TextView) findViewById(R.id.thursday5);
        thursday[6] = (TextView) findViewById(R.id.thursday6);
        thursday[7] = (TextView) findViewById(R.id.thursday7);
        thursday[8] = (TextView) findViewById(R.id.thursday8);
        thursday[9] = (TextView) findViewById(R.id.thursday9);

        friday[0] = (TextView) findViewById(R.id.friday0);
        friday[1] = (TextView) findViewById(R.id.friday1);
        friday[2] = (TextView) findViewById(R.id.friday2);
        friday[3] = (TextView) findViewById(R.id.friday3);
        friday[4] = (TextView) findViewById(R.id.friday4);
        friday[5] = (TextView) findViewById(R.id.friday5);
        friday[6] = (TextView) findViewById(R.id.friday6);
        friday[7] = (TextView) findViewById(R.id.friday7);
        friday[8] = (TextView) findViewById(R.id.friday8);
        friday[9] = (TextView) findViewById(R.id.friday9);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView:
                    myStartActivity(WriteScheduleActivity.class);
                    break;

                case R.id.monday0:
                    for(int i = 0; i < documentList.size(); i++){
                        Log.e("monday", monday[0].getText().toString());
                        Log.e("setOnClickListener", "come in");
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("09")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 0);
                        }
                    }
                    break;
                case R.id.monday1:
                    for(int i = 0; i < documentList.size(); i++){
                        Log.e("monday", monday[1].getText().toString());
                        Log.e("setOnClickListener", "come in");
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("10")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 1);
                        }
                    }
                    break;
                case R.id.monday2:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("11")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 2);
                        }
                    }
                    break;
                case R.id.monday3:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("12")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 3);
                        }
                    }
                    break;
                case R.id.monday4:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("13")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 4);
                        }
                    }
                    break;
                case R.id.monday5:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("14")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 5);
                        }
                    }
                    break;
                case R.id.monday6:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("15")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 6);
                        }
                    }
                    break;
                case R.id.monday7:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("16")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 7);
                        }
                    }
                    break;
                case R.id.monday8:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("17")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 8);
                        }
                    }
                    break;
                case R.id.monday9:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("월요일") && documentList.get(i).getStartAt().split(":")[0].equals("18")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 9);
                        }
                    }
                    break;

                case R.id.tuesday0:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("09")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 0);
                        }
                    }
                    break;
                case R.id.tuesday1:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("10")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 1);
                        }
                    }
                    break;
                case R.id.tuesday2:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("11")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 2);
                        }
                    }
                    break;
                case R.id.tuesday3:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("12")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 3);
                        }
                    }
                    break;
                case R.id.tuesday4:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("13")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 4);
                        }
                    }
                    break;
                case R.id.tuesday5:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("14")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 5);
                        }
                    }
                    break;
                case R.id.tuesday6:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("15")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 6);
                        }
                    }
                    break;
                case R.id.tuesday7:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("16")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 7);
                        }
                    }
                    break;
                case R.id.tuesday8:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("17")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 8);
                        }
                    }
                    break;
                case R.id.tuesday9:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("화요일") && documentList.get(i).getStartAt().split(":")[0].equals("18")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 9);
                        }
                    }
                    break;

                case R.id.wednesday0:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("09")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 0);
                        }
                    }
                    break;
                case R.id.wednesday1:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("10")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 1);
                        }
                    }
                    break;
                case R.id.wednesday2:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("11")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 2);
                        }
                    }
                    break;
                case R.id.wednesday3:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("12")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 3);
                        }
                    }
                    break;
                case R.id.wednesday4:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("13")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 4);
                        }
                    }
                    break;
                case R.id.wednesday5:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("14")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 5);
                        }
                    }
                    break;
                case R.id.wednesday6:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("15")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 6);
                        }
                    }
                    break;
                case R.id.wednesday7:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("16")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 7);
                        }
                    }
                    break;
                case R.id.wednesday8:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("17")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 8);
                        }
                    }
                    break;
                case R.id.wednesday9:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("수요일") && documentList.get(i).getStartAt().split(":")[0].equals("18")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 9);
                        }
                    }
                    break;

                case R.id.thursday0:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("09")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 0);
                        }
                    }
                    break;
                case R.id.thursday1:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("10")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 1);
                        }
                    }
                    break;
                case R.id.thursday2:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("11")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 2);
                        }
                    }
                    break;
                case R.id.thursday3:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("12")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 3);
                        }
                    }
                    break;
                case R.id.thursday4:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("13")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 4);
                        }
                    }
                    break;
                case R.id.thursday5:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("14")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 5);
                        }
                    }
                    break;
                case R.id.thursday6:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("15")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 6);
                        }
                    }
                    break;
                case R.id.thursday7:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("16")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 7);
                        }
                    }
                    break;
                case R.id.thursday8:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("17")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 8);
                        }
                    }
                    break;
                case R.id.thursday9:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("목요일") && documentList.get(i).getStartAt().split(":")[0].equals("18")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 9);
                        }
                    }
                    break;

                case R.id.friday0:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("09")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 0);
                        }
                    }
                    break;
                case R.id.friday1:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("10")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 1);
                        }
                    }
                    break;
                case R.id.friday2:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("11")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 2);
                        }
                    }
                    break;
                case R.id.friday3:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("12")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 3);
                        }
                    }
                    break;
                case R.id.friday4:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("13")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 4);
                        }
                    }
                    break;
                case R.id.friday5:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("14")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 5);
                        }
                    }
                    break;
                case R.id.friday6:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("15")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 6);
                        }
                    }
                    break;
                case R.id.friday7:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("16")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 7);
                        }
                    }
                    break;
                case R.id.friday8:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("17")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 8);
                        }
                    }
                    break;
                case R.id.friday9:
                    for(int i = 0; i < documentList.size(); i++){
                        if(documentList.get(i).getDay().equals("금요일") && documentList.get(i).getStartAt().split(":")[0].equals("18")){
                            dialog(documentList.get(i).getId(), documentList.get(i).getEndAt(), i, 9);
                        }
                    }
                    break;
            }
        }
    };
    @Override
    protected void onResume(){
        super.onResume();
        collectTimeList();
    }


    private void writeTimeTable(){
        for(int i = 0; i < scheduleList.size(); i++){
            String start_array[] = scheduleList.get(i).getStartAt().split(":");
            String end_array[] = scheduleList.get(i).getEndAt().split(":");
            int first = Integer.parseInt(start_array[0]);
            int last = Integer.parseInt(end_array[0]);

            if(scheduleList.get(i).getDay().equals("월요일")){
                for(int j = first; j < last; j++){
                    monday[j - 9].setText(scheduleList.get(i).getState());
                    monday[j - 9].setBackground(getDrawable(R.drawable.cell_shape_add));
                }

            }
            else if(scheduleList.get(i).getDay().equals("화요일")){
                for(int j = first; j < last; j++){
                    tuesday[j - 9].setText(scheduleList.get(i).getState());
                    tuesday[j - 9].setBackground(getDrawable(R.drawable.cell_shape_add));
                }

            }
            else if(scheduleList.get(i).getDay().equals("수요일")){
                for(int j = first; j < last; j++){
                    wednesday[j - 9].setText(scheduleList.get(i).getState());
                    wednesday[j - 9].setBackground(getDrawable(R.drawable.cell_shape_add));
                }

            }
            else if(scheduleList.get(i).getDay().equals("목요일")){
                for(int j = first; j < last; j++){
                    thursday[j - 9].setText(scheduleList.get(i).getState());
                    thursday[j - 9].setBackground(getDrawable(R.drawable.cell_shape_add));
                }

            }
            else if(scheduleList.get(i).getDay().equals("금요일")){
                for(int j = first; j < last; j++){
                    friday[j - 9].setText(scheduleList.get(i).getState());
                    friday[j - 9].setBackground(getDrawable(R.drawable.cell_shape_add));
                }

            }
        }

    }

    private void collectTimeList(){
        CollectionReference collectionReference;
        collectionReference = firebaseFirestore.collection("schedules").document("userID").collection(user.getUid());
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            scheduleList.clear();
                            documentList.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                documentList.add(new documentInfo(
                                        document.getId(),
                                        document.getData().get("day").toString(),
                                        document.getData().get("startAt").toString(),
                                        document.getData().get("endAt").toString()));
                                scheduleList.add(new ScheduleInfo(
                                        document.getData().get("title").toString(),
                                        document.getData().get("state").toString(),
                                        document.getData().get("day").toString(),
                                        document.getData().get("publisher").toString(),
                                        document.getData().get("startAt").toString(),
                                        document.getData().get("endAt").toString()));
                            }
                            writeTimeTable();
                        }
                        else{
                            Log.d(TAG, "Error getting document ", task.getException());
                        }
                    }

                });

    }

    private void dialog(String id, String end, int i, int temp){
        final String items[] = {"변경", "삭제"};
        final String documentId = id;
        final int endAt = Integer.parseInt(end.split(":")[0]);
        final int index = i;
        final int period = temp;
        final int flag = 0;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("선택");
        alert.setCancelable(true);

        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    onModify(documentId, endAt, index, period, flag);
                }
                else{
                    onDelete(documentId, endAt, index, period);
                }
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void onModify(String id, int end, int i, int temp, int flag) {
        final int index = i;
        final int endAt = end;
        final int period = temp;
        flag = 1;
        Log.e("로그", "수정" + id);
        //onDelete(id, endAt, index, period, flag);
        myStartActivity(WriteScheduleActivity.class, id, flag);
    }

    public void onDelete(String id, int end, int i, int temp) {
        final int index = i;
        final int endAt = end;
        final int period = temp;
        Log.e("로그", "삭제" + id);
        firebaseFirestore.collection("schedules").document("userID").collection(user.getUid()).document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                        startToast("시간표를 삭제하였습니다.");

                        if(documentList.get(index).getDay().equals("월요일")){
                            for(int j = period; j < endAt - 9; j++){
                                int resID = getResources().getIdentifier("monday" + j, "id", getPackageName());
                                Log.e("delete", Integer.toString(resID));
                                monday[j].setText("");
                                monday[j].setBackground(getDrawable(R.drawable.cell_shape));
                                monday[j] = (TextView) findViewById(resID);
                            }
                        }
                        else if(documentList.get(index).getDay().equals("화요일")){
                            for(int j = period; j < endAt - 9; j++){
                                tuesday[j].setText("");
                                tuesday[j].setBackground(getDrawable(R.drawable.cell_shape));
                            }
                        }
                        else if(documentList.get(index).getDay().equals("수요일")){
                            for(int j = period; j < endAt - 9; j++){
                                wednesday[j].setText("");
                                wednesday[j].setBackground(getDrawable(R.drawable.cell_shape));
                            }
                        }
                        else if(documentList.get(index).getDay().equals("목요일")){
                            for(int j = period; j < endAt - 9; j++){
                                thursday[j].setText("");
                                thursday[j].setBackground(getDrawable(R.drawable.cell_shape));
                            }
                        }
                        else if(documentList.get(index).getDay().equals("금요일")){
                            for(int j = period; j < endAt - 9; j++){
                                friday[j].setText("");
                                friday[j].setBackground(getDrawable(R.drawable.cell_shape));
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                        startToast("시간표 삭제에 실패하였습니다.");
                    }
                });

    }

    private void startToast(String msg) { // 알림 창
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        //22:18 part5 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void myStartActivity(Class c, String id, int flag) {
        Intent intent = new Intent(this, c);
        intent.putExtra("id", id);
        intent.putExtra("flag", flag);
        startActivity(intent);
    }
}
