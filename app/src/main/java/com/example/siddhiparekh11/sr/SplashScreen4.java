package com.example.siddhiparekh11.sr;

/**
 * Created by siddhiparekh11 on 12/2/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;

public class SplashScreen4 extends Activity {
    String ans4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash4);
    }
    public void onSplash4Next(View v)
    {
        Intent i=new Intent(getApplicationContext(),MainAct.class);
        i.putExtra("answer1",getIntent().getStringExtra("answer1").toString());
        i.putExtra("answer2",getIntent().getStringExtra("answer2").toString());
        i.putExtra("answer3",getIntent().getStringExtra("answer3").toString());
        i.putExtra("answer4",ans4);
        startActivity(i);
    }
    public void onRadioButtonClick(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();

        RadioButton r;
        switch(v.getId()) {
            case R.id.sp4r1:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp4r1);
                    ans4=r.getText().toString();
                    break;
                }
            case R.id.sp4r2:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp4r2);
                    ans4=r.getText().toString();
                    break;
                }
            case R.id.sp4r3:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp4r3);
                    ans4=r.getText().toString();
                    break;
                }


        }
    }
}
