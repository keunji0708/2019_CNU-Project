package com.example.tablet;

import android.media.session.MediaSession;

import java.io.Serializable;

public class ChatData implements Serializable{ //데이터 넣고 빼고 넣고 빼고 할려고! (데이터 전송객체)
    private String msg;
    private String nickname;
    public MediaSession.Token token;

    public String getMsg() {  return msg;     }

    public void setMsg(String msg) {  this.msg = msg; }

    public String getNickname() {  return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

}