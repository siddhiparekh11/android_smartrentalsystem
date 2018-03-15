package com.example.siddhiparekh11.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by siddhiparekh11 on 12/2/17.
 */

public class SplashScreen3 extends Activity {

    String ans3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash3);
    }


    public void onSplash3Next(View v){

        Intent i=new Intent(getApplicationContext(),SplashScreen4.class);
        i.putExtra("answer1",getIntent().getStringExtra("answer1").toString());
        i.putExtra("answer2",getIntent().getStringExtra("answer2").toString());
        i.putExtra("answer3",ans3);
        startActivity(i);

    }
    public void onRadioButtonClick(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();

        RadioButton r;
        switch(v.getId()) {
            case R.id.sp3r1:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp3r1);
                    ans3=r.getText().toString();
                    break;
                }
            case R.id.sp3r2:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp3r2);
                    ans3=r.getText().toString();
                    break;
                }
            case R.id.sp3r3:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp3r3);
                    ans3=r.getText().toString();
                    break;
                }


        }
    }

}
