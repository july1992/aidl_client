package com.vily.aidl_clint;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vily.aidl_myaidl.VilyServiceAidl;
import com.vily.aidl_myaidl.bean.Person;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mTv_value;
    private Button mBtn_add;
    private EditText mEt_b;
    private EditText mEt_a;
    private Button mBtn_string;
    private Button mBtn_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }


    private void initView() {
        mEt_a = findViewById(R.id.et_a);
        mEt_b = findViewById(R.id.et_b);
        mBtn_add = findViewById(R.id.btn_add);
        mTv_value = findViewById(R.id.tv_value);

        mBtn_string = findViewById(R.id.btn_string);
        mBtn_person = findViewById(R.id.btn_person);

    }

    private void initData() {


        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.vily.aidl_service", "com.vily.aidl_service.VilyService");
        intent.setComponent(cn);
        intent.setPackage("com.vily.aidl_service");
        intent.setAction("com.vily.aidl_service.VilyService");
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);

    }

    private VilyServiceAidl mService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
               mService = null;
            Log.i(TAG, "onServiceDisconnected: ----绑定失败");
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: -----service connect success");
            mService = VilyServiceAidl.Stub.asInterface(service);

        }
    };


    private void initListener() {
        mBtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mService==null){
                    Log.i(TAG, "onClick: -----kong");
                    return;
                }
                try {
                    int sum = mService.addNumbers(StringUtis.toInt(mEt_a.getText().toString()), StringUtis.toInt(mEt_b.getText().toString()));
                    mTv_value.setText(sum+"");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mBtn_string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mService==null){
                    Log.i(TAG, "onClick: -----kong");
                    return;
                }
                try {
                    List<String> stringList = mService.getStringList();
                    mTv_value.setText(stringList.toString());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mService==null){
                    Log.i(TAG, "onClick: -----kong");
                    return;
                }
                try {
                    List<Person> personList = mService.getPersonList();
                    mTv_value.setText(personList.toString());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
