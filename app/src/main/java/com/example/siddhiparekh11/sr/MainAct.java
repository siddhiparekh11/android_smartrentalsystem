package com.example.siddhiparekh11.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by siddhiparekh11 on 12/2/17.
 */

public class MainAct extends Activity {

    TextView t;
    String wservice;
    String s1="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_question);
         t=(TextView)findViewById(R.id.txt1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent b=getIntent();
             s1=(b.getStringExtra("answer1").toString()) + " " ;
             s1= s1  + (getIntent().getStringExtra("answer2").toString()) + " " ;
             s1=s1+ (getIntent().getStringExtra("answer3").toString())+ " ";
             s1=s1+ (getIntent().getStringExtra("answer4").toString());
       // t.setText(s1);
        Log.d("myTag", (String) t.getText());


    }

    public void onclickNext(View view)
    {

         wservice=Servicedetails(s1);

         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
         intent.putExtra("Service",wservice);
         startActivity(intent);

    }

    public String Servicedetails(String select)
    {
        String result="";
        //if(select.equals("spr1 sp2r2 sp3r3 sp4r1"))
            result="0";
        //else if(select.equals("spr1 sp2r1 sp3r1 sp4r1"))
          //  result="1";
        return result;
    }

}
