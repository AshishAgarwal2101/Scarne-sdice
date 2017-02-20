package com.click.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView tv;
    private Button b1, b2, b3;
    private int uScore,uTurnScore,cScore,cTurnScore;
    String compStatus="Computer holds";
    final Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);
        tv = (TextView)findViewById(R.id.tv);
        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        b3 = (Button)findViewById(R.id.b3);

        uScore=uTurnScore=cScore=cTurnScore=0;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int num = r.nextInt(6)+1;
                String userStatus="";
                iv.setImageResource(getResources().getIdentifier("dice"+num, "drawable", getPackageName()));
                if(num!=1){
                    uTurnScore+=num;
                }
                else{
                    uTurnScore=0;
                    tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore);
                    computerTurn();
                }
                if(uScore+uTurnScore>=100){
                    userStatus="You win!!";
                    uScore=uTurnScore=cScore=cTurnScore=0;
                    iv.setImageResource(R.drawable.dice1);
                }
                tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+userStatus);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uScore+=uTurnScore;
                uTurnScore=0;
                tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore);
                computerTurn();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uScore=uTurnScore=cScore=cTurnScore=0;
                iv.setImageResource(R.drawable.dice1);
                tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore);
            }
        });
    }
    void computerTurn(){

        while(cTurnScore<20){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    final int num = random.nextInt(6)+1;
                    if(num!=1){
                        cTurnScore+=num;
                        compStatus = "Computer rolls";
                    }
                    else{
                        cTurnScore=0;
                        compStatus="Computer rolled 1";
                        iv.setImageResource(R.drawable.dice1);
                        tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
                        return;
                    }
                    if(cScore+cTurnScore>=100) {
                        compStatus = "Computer Wins!!";
                        tv.setText("Your score: " + uScore + " computer score: " + cScore + " your turn score: " + uTurnScore + "\n" + compStatus);
                        uScore = uTurnScore = cScore = cTurnScore = 0;
                        iv.setImageResource(R.drawable.dice1);
                        return;
                    }
                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
                    compStatus="Computer holds";
                    iv.setImageResource(getResources().getIdentifier("dice"+num, "drawable", getPackageName()));

                }
            },1000);
            /*final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Random random = new Random();
                    final int num = random.nextInt(6)+1;
                    if(num!=1){
                        cTurnScore+=num;
                        compStatus = "Computer rolls";
                    }
                    else{
                        cTurnScore=0;
                        compStatus="Computer rolled 1";
                        iv.setImageResource(R.drawable.dice1);
                        tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
                        return;
                    }
                    if(cScore+cTurnScore>=100){
                        compStatus="Computer Wins!!";
                        tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
                        uScore=uTurnScore=cScore=cTurnScore=0;
                        iv.setImageResource(R.drawable.dice1);
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b1.setEnabled(false);
                            b2.setEnabled(false);
                            tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
                            compStatus="Computer holds";
                            iv.setImageResource(getResources().getIdentifier("dice"+num, "drawable", getPackageName()));
                        }
                    });
                    timer.cancel();
                }

            },1000,2000);*/
            //timer.schedule(timerTask, 1000, 2000);
        }
        cScore+=cTurnScore;
        cTurnScore=0;
        tv.setText("Your score: "+uScore+" computer score: "+cScore+" your turn score: "+uTurnScore+"\n"+compStatus);
        b1.setEnabled(true);
        b3.setEnabled(true);
    }
}
