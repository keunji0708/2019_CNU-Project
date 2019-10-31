package com.example.professor_project;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends BasicActivity {
    private FirebaseAuth mAuth;
    //private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener(){
      @Override
      public void onClick(View view){
          switch (view.getId()){
              case R.id.signUpButton:
                  signUp();
                  break;

              case R.id.gotoLoginButton:
                  startLogInActivity();
                  break;

          }
      }
    };

    private void signUp(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.pwEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.pwCheckEditText)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if(password.equals(passwordCheck)){
                final RelativeLayout loaderLayout = findViewById(R.id.loaderLayout);
                loaderLayout.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loaderLayout.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입 성공");
                                } else {
                                    if(task.getException() != null) {
                                        // If sign in fails, display a message to the user.
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지 않습니다.");
            }
        } else{
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLogInActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
