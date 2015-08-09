package com.babyspace.mamshare.app.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.service.LocalService;
import com.babyspace.mamshare.basement.BaseActivity;
import com.michael.library.debug.L;
import com.michael.library.widget.materialedittext.MaterialEditText;
import com.michael.library.widget.materialedittext.validation.RegexpValidator;

public class TestActivity extends BaseActivity {

    private final static String TAG = "LOCAL_SERVICE_TEST";
    private ServiceConnection sc;
    private boolean isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        sc=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalService.LocalBinder mBinder = (LocalService.LocalBinder)service;
                L.d(TAG, "30 + 5 = " + mBinder.add(30, 5));
                L.d(TAG, mBinder.getService().toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                L.d(TAG, "onServiceDisconnected "+name.toString());

            }
        };
        initEnableBt();
        initSingleLineEllipsisEt();
        initSetErrorEt();
        initValidationEt();
    }

    private void initEnableBt() {
        final EditText basicEt = (EditText) findViewById(R.id.basicEt);
        final Button enableBt = (Button) findViewById(R.id.enableBt);
        enableBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicEt.setEnabled(!basicEt.isEnabled());
                enableBt.setText(basicEt.isEnabled() ? "DISABLE" : "ENABLE");
            }
        });
    }

    private void initSingleLineEllipsisEt() {
        EditText singleLineEllipsisEt = (EditText) findViewById(R.id.singleLineEllipsisEt);
        singleLineEllipsisEt.setSelection(singleLineEllipsisEt.getText().length());
    }

    private void initSetErrorEt() {
        final EditText bottomTextEt = (EditText) findViewById(R.id.bottomTextEt);
        final Button setErrorBt = (Button) findViewById(R.id.setErrorBt);
        setErrorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO service
                bindService(new Intent(TestActivity.this, LocalService.class), sc, Context.BIND_AUTO_CREATE);
                isBind = true;


                bottomTextEt.setError("1-line Error!");
            }
        });
        final Button setError2Bt = (Button) findViewById(R.id.setError2Bt);
        setError2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO service
                if(isBind){
                    unbindService(sc);
                    isBind = false;
                }



                bottomTextEt.setError("2-line\nError!");
            }
        });
        final Button setError3Bt = (Button) findViewById(R.id.setError3Bt);
        setError3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTextEt.setError("So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors!");
            }
        });
    }

    private void initValidationEt() {
        final MaterialEditText validationEt = (MaterialEditText) findViewById(R.id.validationEt);
        validationEt.addValidator(new RegexpValidator("Only Integer Valid!", "\\d+"));
        final Button validateBt = (Button) findViewById(R.id.validateBt);
        validateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                validationEt.validate();
            }
        });
    }


}
