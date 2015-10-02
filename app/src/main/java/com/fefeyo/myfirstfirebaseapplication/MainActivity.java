package com.fefeyo.myfirstfirebaseapplication;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //　Firebase初期化
        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://firstfirefefe.firebaseio.com/");
    }

    public void insert(View v){
        /**
         * データツリーの作成サンプル
         */
//        final Firebase user_info = mFirebase.child("users").child(new Random().nextInt(9999999)+"");
//        user_info.child("name").setValue("じょるの");
//        user_info.child("chara").setValue("えりち");
//        user_info.child("comment").setValue("ハラショー");

        //　メルアドとパスワードを登録して認証する、トークンを得ることができるので次回からトークン再利用可能
        mFirebase.authWithPassword("imanity.fefe@gmail.com", "metukiringa", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                if(null != authData){
                    getSharedPreferences("auth", MODE_PRIVATE).edit().putString("token", authData.getToken());
                    Log.i("完了", "完了");
                }else{
                    Toast.makeText(MainActivity.this, "認証失敗", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this, "認証失敗", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void read(View v){
        //　欲しいデータツリーの所にポインタ（Firebase）を持ってきてこのリスナーをセットすると、データの習得が可能
//        mFirebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
        //　新規ユーザー登録
        mFirebase.createUser(
                "imanity.fefe@gmail.com",
                "metukiringa",
                new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "ユーザー登録完了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                }
        );
    }


}