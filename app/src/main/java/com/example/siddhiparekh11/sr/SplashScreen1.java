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

public class SplashScreen1 extends Activity {

        String ans1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
    }
    public void onSplash1Next(View v)
    {
        Intent i=new Intent(getApplicationContext(),SplashScreen2.class);
        i.putExtra("answer1",ans1);
        startActivity(i);

    }
    public void onRadioButtonClick(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();

        RadioButton r;
        switch(v.getId()) {
            case R.id.spr1:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.spr1);
                    ans1=r.getText().toString();
                    break;
                }
            case R.id.spr2:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.spr2);
                    ans1=r.getText().toString();
                    break;
                }
            case R.id.spr3:
                if (checked) {
                    r = (RadioButton) findViewById(R.id.spr3);
                    ans1=r.getText().toString();
                    break;
                }


        }
    }
}
