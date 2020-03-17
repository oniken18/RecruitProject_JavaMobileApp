package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class WelcomeActivity extends AppCompatActivity {

    private volatile Boolean isStopSuitcaseThread;

    private Handler mainHandler = new Handler();
    RelativeLayout myLayout;

    ObjectAnimator animX, animTranslationX;
    Animation ScaleAnimation, FadeInAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SPLASH_TIMEOUT = 4000;
        TextView appName;
        View underLine;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        appName = findViewById(R.id.AppName);
        underLine = findViewById(R.id.UnderLine);
        myLayout = (RelativeLayout) findViewById(R.id.myLayout);

        ScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.under_line);
        ScaleAnimation.setFillAfter(true);
        underLine.startAnimation(ScaleAnimation);

        YoYo.with(Techniques.BounceInDown).duration(2000).repeat(0).playOn(appName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStopSuitcaseThread = false;
        suitcaseThread ST = new suitcaseThread();
        ST.start();

//        BallMoveX();
//        BallMoveDown();
//        BallMoveY();
//        BallMoveUp();
    }

//    private void BallMoveDown() {
//        ObjectAnimator animation = ObjectAnimator.ofFloat(ball, "translationY", 860);
//        animation.setStartDelay(4000);
//        animation.setDuration(500);
//        animation.setRepeatCount(0);
//        animation.start();
//    }
//
//    private void BallMoveY() {
//        ObjectAnimator animation = ObjectAnimator.ofFloat(ball, "translationY", 500);
//        animation.setStartDelay(4500);
//        animation.setDuration(627);
//        animation.setRepeatCount(5);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//        animation.start();
//    }
//
//    private void BallMoveUp() {
//        ObjectAnimator animation = ObjectAnimator.ofFloat(ball, "translationY", 0);
//        animation.setStartDelay(8262);
//        animation.setDuration(500);
//        animation.setRepeatCount(0);
//        animation.start();
//    }
//    private void BallMoveX() {
//        ObjectAnimator animation = ObjectAnimator.ofFloat(ball, "translationX", -955);
//        animation.setStartDelay(4000);
//        animation.setDuration(4762);
//        animation.setRepeatCount(0);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//        animation.start();
//    }

    class suitcaseThread extends Thread {

        suitcaseThread(){}

        @Override
        public void run() {
            for (int i = 1; i < 50; i++){
                if (isStopSuitcaseThread){
                    return;
                }
                createBag((i*80)+4000);
            }
        }

        private void createBag(final long startTime){
            final int leftPos;
            final long speed;
            final ImageView imageView;
            final RelativeLayout.LayoutParams layoutParams;

            imageView = new ImageView(getApplication());
            imageView.setImageResource(R.drawable.ic_bag);

            layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            Random r = new Random();

            speed =(long) r.nextInt((4000 - 800) + 1) + 800;
            leftPos= r.nextInt((1100 - 140) + 1) + 140;

            layoutParams.leftMargin = leftPos;
            layoutParams.topMargin = 480;

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    myLayout.addView(imageView,0,  layoutParams);

                    ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "translationY", 920);
                    animation.setStartDelay(startTime);
                    animation.setDuration(speed);
                    animation.setRepeatCount(0);
                    animation.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myLayout.removeView(imageView);
                        }
                    }, startTime + speed);
                }
            });
        }
    }
}
