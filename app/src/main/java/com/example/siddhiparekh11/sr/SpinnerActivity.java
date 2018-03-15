package com.example.siddhiparekh11.sr;

import android.view.View;
import android.widget.AdapterView;



public class SpinnerActivity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
