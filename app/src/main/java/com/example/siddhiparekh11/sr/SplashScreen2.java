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


public class SplashScreen2 extends Activity {

    String ans2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }
    public void onSplash2Next(View v)
    {
        Intent i=new Intent(getApplicationContext(),SplashScreen3.class);
        i.putExtra("answer1",getIntent().getStringExtra("answer1").toString());
        i.putExtra("answer2",ans2);
        startActivity(i);

    }
    public void onRadioButtonClick(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();

        RadioButton r;
        switch(v.getId()) {
            case R.id.sp2r1:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp2r1);
                    ans2=r.getText().toString();
                    break;
                }
            case R.id.sp2r2:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp2r2);
                    ans2=r.getText().toString();
                    break;
                }
            case R.id.sp2r3:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.sp2r3);
                    ans2=r.getText().toString();
                    break;
                }


        }
    }
}
