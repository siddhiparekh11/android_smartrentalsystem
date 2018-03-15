package com.example.siddhiparekh11.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;



public class PaymentMain extends Activity implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;
    TextView Total;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment);
        double value1 = getIntent().getExtras().getDouble("total");
        int value=(int) value1;
        Log.d("Value",Integer.toString(value));
        bp = new BillingProcessor(this, null, this);
        Total=(TextView)findViewById(R.id.totalamt);
        Total.setText(""+value);

        final Button button = findViewById(R.id.btn_pay);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                bp.purchase(PaymentMain.this,"android.test.purchased");
            }
        });
    }

    // IBillingHandler implementation

    @Override
    public void onBillingInitialized() {
    /*
    * Called when BillingProcessor was initialized and it's ready to purchase
    */
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
    /*
    * Called when requested PRODUCT ID was successfully purchased
    */

        Toast.makeText(this,"Payment Successful", Toast.LENGTH_LONG);

        bp.consumePurchase("android.test.purchased");
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
    /*
    * Called when some error occurred. See Constants class for more details
    *
    * Note - this includes handling the case where the user canceled the buy dialog:
    * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
    */
    }

    @Override
    public void onPurchaseHistoryRestored() {
    /*
    * Called when purchase history was restored and the list of all owned PRODUCT ID's
    * was loaded from Google Play
    */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }



}
