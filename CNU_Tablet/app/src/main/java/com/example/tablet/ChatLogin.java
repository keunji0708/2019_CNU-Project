package com.example.tablet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatLogin extends AppCompatActivity {

    private EditText et_user1;
    private EditText et_user2;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);

        et_user1 = (EditText)findViewById(R.id.et_user1);
        et_user2 = (EditText)findViewById(R.id.et_user2);
        btn_login = (Button)findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_id = et_user1.getText().toString();
                String str_name = et_user2.getText().toString();

                if("".equals(str_id)){
                    Toast.makeText(ChatLogin.this, "학번을 입력해주세요!",
                            Toast.LENGTH_SHORT).show();
                }
                else if("".equals(str_name)){
                    Toast.makeText(ChatLogin.this, "이름을 입력해주세요!",
                            Toast.LENGTH_SHORT).show();
                }

                else{
                    Intent intent = new Intent(ChatLogin.this, ChatActivity.class);
                    intent.putExtra("name", str_name);
                    intent.putExtra("id", str_id);
                    startActivity(intent);
                }
            }
        });
    }
}