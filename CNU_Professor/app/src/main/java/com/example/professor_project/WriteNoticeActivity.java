
package com.example.professor_project;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.professor_project.View.NoticeInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class WriteNoticeActivity extends BasicActivity {
    private static final String TAG = "WriteNoticeActivity";
    private FirebaseUser user;
    private RelativeLayout loaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);
        //setToolbarTitle("게시글 작성");

        loaderLayout = findViewById(R.id.loaderLayout);
        findViewById(R.id.uploadButton).setOnClickListener(onClickListener);
    }

    private void uploadNotice(){
        String title = ((EditText)findViewById(R.id.titleEditText)).getText().toString();
        String contents = ((EditText)findViewById(R.id.contentsEditText)).getText().toString();

        if (title.length() > 0 && contents.length() > 0) {
            loaderLayout.setVisibility(View.VISIBLE);
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            // SNS 16 공지사항 수정에 필요한 id
            String id = getIntent().getStringExtra("id");
            DocumentReference dr;
            if (id == null){
                dr = firebaseFirestore .collection("notices")
                        .document("userID").collection(user.getUid()).document();
            } else{
                dr = firebaseFirestore .collection("notices")
                        .document("userID").collection(user.getUid()).document(id);
            }

            final DocumentReference documentReference = dr;
            NoticeInfo noticeInfo = new NoticeInfo(title, contents, user.getUid(), new Date());
            storeUpload(documentReference, noticeInfo);

        }else {
            startToast(  "제목과 내용을 입력해주세요.");
        }
    }

    private void storeUpload(DocumentReference documentReference, NoticeInfo noticeInfo){
        documentReference.set(noticeInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        startToast("공지사항 등록에 성공하였습니다.");
                        loaderLayout.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        loaderLayout.setVisibility(View.GONE);
                        startToast("공지사항 등록에 실패하였습니다.");
                    }
                });
    }

    private void startToast(String msg){ // 알림 창
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.uploadButton:
                    uploadNotice();
                    break;

            }
        }
    };

    private void myStartActivity(Class c, int media, int requestCode) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }
}

