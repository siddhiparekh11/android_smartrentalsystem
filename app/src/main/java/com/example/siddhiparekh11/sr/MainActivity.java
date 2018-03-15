package com.example.siddhiparekh11.sr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    String memid,username="";
    private EditText et_Name,et_Pass;
    String wservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wservice= getIntent().getStringExtra("Service").toString();
        et_Name = (EditText)findViewById(R.id.name);
        et_Pass = (EditText)findViewById(R.id.password);
    }

    public void onclickLogin(View view)
    {
        //Login sucess
            Loginuser();
    }

    public void onclickSignUp(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        intent.putExtra("Service",wservice);
        startActivity(intent);

    }

    private void LoginErrorMessage()
    {
        android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(this);
        dlgAlert.setMessage("wrong password or username");
        dlgAlert.setTitle("Error Message...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.show();
    }

    public void Loginuser()
    {
        String name,password;
        boolean res=false;

        name=et_Name.getText().toString();
        password=et_Pass.getText().toString();
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("UserName",name );
        jsonParams.put("PWD",password);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/members/login",
                new JSONObject(jsonParams),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("resonse","success");


                        try {

                             memid=response.getString("MemberID");

                            Log.d("memberid",memid);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                            intent.putExtra("service",wservice);
                            intent.putExtra("memberid",memid);
                            startActivity(intent);
                            }

                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("resonse","Failed");
                        LoginErrorMessage();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "My useragent");
                return headers;
            }
        };
        queue.add(myRequest);

    }

}
