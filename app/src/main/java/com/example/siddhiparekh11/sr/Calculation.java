package com.example.siddhiparekh11.sr;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;
import android.widget.Button;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.util.Random;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;



public class Calculation extends Fragment {

    private TextView tvTot;
    private TextView tvDur;
    private TextView tvPrice;
    LinearLayout checkBoxLayout;
    ScrollView sv;
    String[] serviceids;
    String[] serviceNames;
    ArrayList<String> equipmentNames = new ArrayList<>();
    ArrayList<String> equipmentids = new ArrayList<>();
    ArrayList<String> equiIdsOld = new ArrayList<>();
    ArrayList<String> equipIdsNew = new ArrayList<>();
    ArrayList<String> equidur = new ArrayList<>();
    ArrayList<String> listIds = new ArrayList<>();
    HashMap<String, String> tmpNames = new HashMap<>();
    HashMap<String, String> equipmentPrice = new HashMap<>();
    HashMap<String, String> allequi = new HashMap<>();
    String[] memequipments;
    String wservice;
    String memid;
    Double tot = 0.0;
    Double pay = 0.0;
    int durdays;
    String startdate;
    String enddate;
    String rentser;
    String rentper;
    String renewal = "0";
    int pos;
    Button submit;
    JSONArray equipmentList=new JSONArray();
    JSONArray equioldlist=new JSONArray();
    JSONArray oldequilist;
    Boolean flag;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Subscribe");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_cal, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        checkBoxLayout = getActivity().findViewById(R.id.layoutinflate);
        submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(pre);
        // sv=new ScrollView(getActivity().getApplicationContext());
        //sv.addView(checkBoxLayout);

    }

    @Override
    public void onResume() {
        super.onResume();
        tvTot = (TextView) getView().findViewById(R.id.total);
        tvPrice = (TextView) getView().findViewById(R.id.sug_price);
        tvDur = (TextView) getView().findViewById(R.id.sug_duration);


        // tvTot.setText(getArguments().getString("service"));
        wservice = getArguments().getString("service");
        memid = getArguments().getString("memberid");

        loadallequipments();
        loadMemberEquipment();
        Log.d("memberequiplenght", String.valueOf(equiIdsOld.size()));
        loadServices();


    }

    public void loadallequipments() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/equipments/list",
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("resonse", "success");


                        try {


                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                allequi.put(o.getString("EquipmentID"), o.getString("EquipmentName"));


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

    }

    public void loadServices() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/service/list",
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("resonse", "success");


                        try {


                            serviceNames = new String[response.length()];
                            serviceids = new String[response.length()];
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                serviceNames[i] = o.getString("ServiceName");
                                serviceids[i] = o.getString("ServiceID");


                            }

                            loadSpecificService(serviceids[Integer.valueOf(wservice)]);
                            spinnerPopulate(serviceNames);


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

    }

    public void loadSpecificService(String serviceid) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/service/search?id=" + serviceid;
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("specificservice", "success");


                        try {

                            durdays = Integer.valueOf(response.getJSONObject(0).getString("Duration"));
                            rentser = response.getJSONObject(0).getString("Duration");

                            tvDur.setText(String.valueOf(durdays));

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

    }

    public void spinnerPopulate(String[] arr) {
        Spinner spinner = getActivity().findViewById(R.id.Spinner_duration);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(Integer.valueOf(wservice));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                populateEquipments(serviceids[position]);
                loadSpecificService(serviceids[position]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateEquipments(String serviceId) {
        loadEquipments(serviceId);

    }

    public void loadEquipments(String serviceId) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/equipments/search?serviceid=" + serviceId;
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET, url
                ,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("fromhere", "success");


                        try {


                            // equipmentNames=new String[response.length()];
                            // equipmentids=new String[response.length()];

                            if (equipmentids.size() > 0) {
                                equipmentids.clear();
                                equipmentNames.clear();
                                equipmentPrice.clear();
                            }

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                Log.d("testing...", o.getString("EquipmentName"));
                                equipmentNames.add(o.getString("EquipmentName"));
                                equipmentids.add(o.getString("EquipmentID"));
                                equipmentPrice.put(o.getString("EquipmentName"), o.getString("Price"));


                            }

                            if (((LinearLayout) checkBoxLayout).getChildCount() > 0) {
                                ((LinearLayout) checkBoxLayout).removeAllViews();

                            }
                            populatecheckbox();

                        } catch (JSONException e) {
                            Log.d("error", e.toString());
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

    }

    public void populatecheckbox() {


       tmpNames.clear();
        for (int j = 0; j < equiIdsOld.size(); j++) {
            if (allequi.containsKey(equiIdsOld.get(j))) {
                tmpNames.put(allequi.get(equiIdsOld.get(j)), equiIdsOld.get(j));
            }
        }
        for (int i = 0; i < equipmentids.size(); i++) {

            CheckBox c = new CheckBox(getActivity().getApplication());

            c.setOnCheckedChangeListener(checked);
            c.setText(equipmentNames.get(i));

            if (tmpNames.containsKey(equipmentNames.get(i))) {
                c.setEnabled(false);

            }
            checkBoxLayout.addView(c);


        }

    }

    private CompoundButton.OnCheckedChangeListener checked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (compoundButton.isChecked()) {

                String s = compoundButton.getText().toString();
                tot = tot + Double.valueOf(equipmentPrice.get(compoundButton.getText().toString()));
                equipIdsNew.add(equipmentids.get(equipmentNames.indexOf(compoundButton.getText().toString())));
            } else {
                tot = tot - Double.valueOf(equipmentPrice.get(compoundButton.getText().toString()));
                if (equipIdsNew.contains(equipmentids.get(equipmentNames.indexOf(compoundButton.getText().toString()))))
                    equipIdsNew.remove(equipmentids.get(equipmentNames.indexOf(compoundButton.getText().toString())));
            }
            tvPrice.setText(String.valueOf(tot));
            pay = tot + (tot * 0.2);
            tvTot.setText(String.valueOf(pay));


        }
    };

    public void loadMemberEquipment() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/members/equipments/search?memberid=" + memid;
        JsonArrayRequest myRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Memlength", String.valueOf(response.length()));


                        try {


                            for (int i = 0; i < response.length(); i++) {
                                JSONObject o = response.getJSONObject(i);
                                Log.d("memberidf", o.getString("EquipmentID"));
                                Log.d("dur", o.getString("RentalPeriod"));
                                Log.d("ksit", o.getString("ListID"));
                                equiIdsOld.add(o.getString("EquipmentID"));
                                equidur.add(o.getString("RentalPeriod"));
                                listIds.add(o.getString("ListID"));


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
    }

    public int generateRandom() {
        final int random = new Random().nextInt(1000) + 1;
        return random;
    }

    Button.OnClickListener pre = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
           /* subscribe();
            registernewequipments();
            updateoldequipments();
            if (flag) {
                for (int i = 0; i < listIds.size(); i++) {
                    deloldequipments(listIds.get(i));
                }

            }
            createnewmemequiplist();*/
            Intent i=new Intent(getActivity().getApplicationContext(),PaymentMain.class);
            Double iy=Double.valueOf(tvPrice.getText().toString());
            i.putExtra("total",iy);
            startActivity(i);
        }}

        ;


        public void subscribe() {


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                String currentDateandTime = sdf.format(new Date());
                startdate = currentDateandTime;
                Calendar c = Calendar.getInstance(); // Get Calendar Instance
                c.setTime(sdf.parse(startdate));

                c.add(Calendar.DATE, Integer.valueOf(rentser));  // add 45 days
                sdf = new SimpleDateFormat("MM/dd/yy");

                Date resultdate = new Date(c.getTimeInMillis());   // Get new time
                enddate = sdf.format(resultdate);



            JSONObject jsonParams = new JSONObject();
            jsonParams.put("SubscriptionID", generateRandom());
            jsonParams.put("ServiceID", Integer.valueOf(serviceids[pos]));
            jsonParams.put("MemberID", Integer.valueOf(memid));
            jsonParams.put("StartDate", startdate);
            jsonParams.put("EndDate", enddate);
            jsonParams.put("Renewals", 0);
            jsonParams.put("IsActive",1);

            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest myRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/subscription/create",
                    jsonParams,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("status", "success");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("resonse", error.toString());

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
            queue.add(myRequest);} catch (Exception e) {

            }


        }

        public void registernewequipments() {
            for (int i = 0; i < equipIdsNew.size(); i++) {
                JSONObject o = new JSONObject();
                try {
                    o.put("ListID", generateRandom());
                    o.put("MemberID", Integer.valueOf(memid));
                    o.put("EquipmentID", Integer.valueOf(equipIdsNew.get(i)));
                    o.put("IsOwned",0);
                    o.put("Subscriptions", "s1");
                    o.put("RentalPeriod", Integer.valueOf(rentser));
                    equipmentList.put(o);
                } catch (JSONException e) {

                }
            }
        }

        public void updateoldequipments() {
            flag = false;

            for (int i = 0; i < equipmentids.size(); i++) {
                if (tmpNames.containsKey(equipmentNames.get(i))) {
                    JSONObject o = new JSONObject();
                    flag = true;
                    try {
                        o.put("ListID", generateRandom());
                        o.put("MemberID", Integer.valueOf(memid));
                        o.put("EquipmentID", Integer.valueOf(equipmentids.get(i)));
                        o.put("IsOwned",0);
                        o.put("Subscriptions", "s1");
                        if ((Integer.valueOf(equidur.get(i))) < Integer.valueOf(rentser)) {

                            int remamt = ((Integer.valueOf(rentser)) - Integer.valueOf(equidur.get(i))) + (Integer.valueOf(equidur.get(i)));
                            o.put("RentalPeriod", remamt);
                        }
                        equipmentList.put(o);

                    } catch (JSONException e) {

                    }
                }
            }
        }

        public void deloldequipments(String lid) {
            try {
                String url = "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/members/equipments/delete?listid=" + lid + "&" + "memberid=" + memid;
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonArrayRequest myRequest = new JsonArrayRequest(
                        Request.Method.DELETE, url,
                        null,

                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("status", "success");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("resonse", error.toString());

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
            } catch (Exception e) {

            }

        }

        public void createnewmemequiplist() {
            try {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = "http://ec2-13-57-3-28.us-west-1.compute.amazonaws.com:9999/api/members/equipments/create?memberid=" + memid;
                JsonArrayRequest myRequest = new JsonArrayRequest(
                        Request.Method.POST, url,
                        equipmentList,

                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("status", "success");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("resonse", error.toString());

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
            } catch (Exception e) {

            }
        }
    }



