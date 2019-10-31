package com.example.professor_project;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.professor_project.View.MemberInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInitActivity extends BasicActivity {
    private static final String TAG = "MemberInitActivity";
    private RelativeLayout loaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        loaderLayout = findViewById(R.id.loaderLayout);
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.checkButton:
                    profileUpdate();
                    break;
            }

        }
    };

    private void profileUpdate(){
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
        String phoneNum = ((EditText)findViewById(R.id.phoneNumEditText)).getText().toString();
        String major = ((EditText)findViewById(R.id.majorEditText)).getText().toString();
        String room = ((EditText)findViewById(R.id.roomEditText)).getText().toString();

        if (name.length() > 0 && phoneNum.length() > 9 && room.length() >= 3 && major.length() > 0) {
            loaderLayout.setVisibility(View.VISIBLE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(name, phoneNum, room, major);

            if(user != null){
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록에 성공하였습니다.");
                                loaderLayout.setVisibility(View.GONE);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록에 실패하였습니다.");
                                loaderLayout.setVisibility(View.GONE);
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

            }
        } else{
            startToast("회원정보를 정확히 입력해주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
