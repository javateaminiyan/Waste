package com.example.admin.solidwaste.paymenttransfergateway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.solidwaste.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();


    @BindView(R.id.tvAmount)
    TextView tvAmout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String amount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);


        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent i = getIntent();
        amount = i.getStringExtra("amount");


        if (amount != null) {
            tvAmout.setText(" INR " + amount);
        } else {
            tvAmout.setText(" INR 0");

        }


        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Prematix Corporation");
            options.put("description", "SolidWaste Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "data:image/png;base64,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");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9894591650");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
