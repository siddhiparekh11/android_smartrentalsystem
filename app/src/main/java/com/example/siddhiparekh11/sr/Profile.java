package com.example.siddhiparekh11.sr;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;



public class Profile extends Fragment {
    String memid;
    ListView l;
    ArrayList<String> subscribers=new ArrayList<String>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Subscriptions");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_subscribe,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        memid = "6";
        l=getActivity().findViewById(R.id.lstsubscribe);
    }

    @Override
    public void onResume() {
        super.onResume();


        loadSubscribers();
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, subscribers);

        l.setAdapter(mArrayAdapter);



    }

    public void loadSubscribers()
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final ArrayList<String> tmp=new ArrayList<>();
        String url="http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/subscription/search?id=" +memid;
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("resonse", "success");
                        Log.d("lengtharr",String.valueOf(response.length()));


                        try {



                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                Log.d("object",String.valueOf(o.getString("ServiceID")));
                               addsub(String.valueOf(o.getString("ServiceID")));
                              // Log.d("sub",tmp.get(i));

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("resonse", error.toString());

                    }
                });

        queue.add(myRequest);
       // Log.d("length",String.valueOf(subscribers.size()));


    }
    public void addsub(String s)
    {
        subscribers.add(s);
    }

}
